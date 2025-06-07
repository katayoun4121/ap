package ap.exercises.projects;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageAndAudioDownloader {

    private static final Pattern IMG_PATTERN = Pattern.compile(
            "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SRC_PATTERN = Pattern.compile(
            "src\\s*=\\s*['\"]([^'\"]+)['\"]",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern AUDIO_PATTERN = Pattern.compile(
            "<audio[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SOURCE_PATTERN = Pattern.compile(
            "<source[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE
    );

    private static final Set<String> IMAGE_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp", ".svg"
    );

    private static final Set<String> AUDIO_EXTENSIONS = Set.of(
            ".mp3", ".wav", ".ogg", ".aac", ".flac", ".m4a"
    );

    private final String targetDomain;
    private final Path downloadBaseDir;
    private final int downloadDelaySeconds;
    private final int maxRetries;
    private final int retryDelaySeconds;
    private final String userAgent;
    private final int timeoutSeconds;

    public ImageAndAudioDownloader(Properties config) {
        this.targetDomain = normalizeDomain(config.getProperty("target_domain"));
        this.downloadBaseDir = Paths.get(config.getProperty("download_base_dir"));
        this.downloadDelaySeconds = Integer.parseInt(config.getProperty("download_delay_seconds", "5"));
        this.maxRetries = Integer.parseInt(config.getProperty("max_retries", "3"));
        this.retryDelaySeconds = Integer.parseInt(config.getProperty("retry_delay_seconds", "10"));
        this.userAgent = config.getProperty("user_agent");
        this.timeoutSeconds = Integer.parseInt(config.getProperty("timeout_seconds", "30"));
    }

    private String normalizeDomain(String domain) {
        try {
            URI uri = new URI(domain);
            String host = uri.getHost();
            return host != null ? host.toLowerCase() : domain.toLowerCase();
        } catch (URISyntaxException e) {
            return domain.toLowerCase();
        }
    }

    private boolean isSameDomain(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host == null) {
                return true;
            }
            return host.toLowerCase().endsWith(targetDomain);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1) {
            return "";
        }
        return filename.substring(lastDot).toLowerCase();
    }

    public Set<String> extractUniqueMediaLinks(String htmlContent) {
        Set<String> allLinks = new LinkedHashSet<>();

        allLinks.addAll(Stream.of(htmlContent.split("<img"))
                .skip(1)
                .map(part -> {
                    Matcher matcher = SRC_PATTERN.matcher(part);
                    return matcher.find() ? matcher.group(1).trim() : null;
                })
                .filter(src -> src != null && !src.isEmpty())
                .filter(this::isSameDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        allLinks.addAll(Stream.of(htmlContent.split("<audio"))
                .skip(1)
                .map(part -> {
                    Matcher matcher = SRC_PATTERN.matcher(part);
                    return matcher.find() ? matcher.group(1).trim() : null;
                })
                .filter(src -> src != null && !src.isEmpty())
                .filter(this::isSameDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        allLinks.addAll(Stream.of(htmlContent.split("<source"))
                .skip(1)
                .map(part -> {
                    Matcher matcher = SRC_PATTERN.matcher(part);
                    return matcher.find() ? matcher.group(1).trim() : null;
                })
                .filter(src -> src != null && !src.isEmpty())
                .filter(this::isSameDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        return allLinks;
    }

    public Set<String> extractUniqueMediaLinksFromFile(String filePath) throws IOException {
        String htmlContent = Files.readString(Paths.get(filePath));
        return extractUniqueMediaLinks(htmlContent);
    }

    public void downloadMediaFiles(Set<String> mediaLinks, String url) throws IOException {
        Path basePath = createMediaBasePath(url);
        Files.createDirectories(basePath);

        Path imagesDir = basePath.resolve("images");
        Path audioDir = basePath.resolve("audio");
        Files.createDirectories(imagesDir);
        Files.createDirectories(audioDir);

        int count = 0;
        for (String link : mediaLinks) {
            count++;
            System.out.printf("\n[%d/%d] Processing: %s%n", count, mediaLinks.size(), link);

            try {
                URI uri = new URI(link);
                String path = uri.getPath();
                if (path == null || path.isEmpty()) {
                    System.out.println("Skipping - No path in URL");
                    continue;
                }

                String filename = Paths.get(path).getFileName().toString();
                if (filename.isEmpty()) {
                    System.out.println("Skipping - No filename in URL");
                    continue;
                }

                String extension = getExtension(filename);
                Path targetDir;

                if (IMAGE_EXTENSIONS.contains(extension)) {
                    targetDir = imagesDir;
                } else if (AUDIO_EXTENSIONS.contains(extension)) {
                    targetDir = audioDir;
                } else {
                    System.out.println("Skipping - Unsupported file type: " + extension);
                    continue;
                }

                Path targetFile = targetDir.resolve(filename);

                int counter = 1;
                while (Files.exists(targetFile)) {
                    String newName = filename.substring(0, filename.lastIndexOf('.'))
                            + "_" + counter + extension;
                    targetFile = targetDir.resolve(newName);
                    counter++;
                }

                boolean success = false;
                Exception lastError = null;

                for (int attempt = 1; attempt <= maxRetries; attempt++) {
                    try {
                        if (attempt > 1) {
                            System.out.printf("Retry attempt %d/%d...%n", attempt, maxRetries);
                            TimeUnit.SECONDS.sleep(retryDelaySeconds);
                        }

                        URL fileUrl = new URL(link);
                        URLConnection connection = fileUrl.openConnection();
                        connection.setRequestProperty("User-Agent", userAgent);
                        connection.setConnectTimeout(timeoutSeconds * 1000);
                        connection.setReadTimeout(timeoutSeconds * 1000);

                        try (InputStream in = connection.getInputStream()) {
                            Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Downloaded successfully to: " + targetFile);
                            success = true;
                            break;
                        }
                    } catch (Exception e) {
                        lastError = e;
                        System.out.printf("Download attempt %d failed: %s%n", attempt, e.getMessage());
                    }
                }

                if (!success) {
                    System.err.printf("Failed to download after %d attempts: %s%n", maxRetries, link);
                    if (lastError != null) {
                        lastError.printStackTrace();
                    }
                }

                if (count < mediaLinks.size() && downloadDelaySeconds > 0) {
                    System.out.printf("Waiting %d seconds before next download...%n", downloadDelaySeconds);
                    TimeUnit.SECONDS.sleep(downloadDelaySeconds);
                }

            } catch (URISyntaxException e) {
                System.err.println("Invalid URL: " + link);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Download interrupted");
                break;
            }
        }
    }

    private Path createMediaBasePath(String url) throws IOException {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            Path outputDir = downloadBaseDir;

            if (host != null && !host.equals(targetDomain) && host.endsWith(targetDomain)) {
                String subdomain = host.substring(0, host.length() - targetDomain.length() - 1);
                outputDir = outputDir.resolve("_" + subdomain.replace(".", "_"));
            }
            if (path != null && !path.isEmpty()) {
                String[] pathParts = path.split("/");
                for (String part : pathParts) {
                    if (!part.isEmpty() && !part.contains(".")) {
                        outputDir = outputDir.resolve(part);
                    }
                }
            }

            return outputDir.resolve("media_files");
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL: " + url, e);
        }
    }

    public void saveUniqueMediaLinksToFile(Set<String> mediaLinks, String url) throws IOException {
        Path outputPath = createOutputPath(url);
        Files.createDirectories(outputPath.getParent());

        Files.write(
                outputPath,
                mediaLinks.stream()
                        .map(link -> link + "\n")
                        .collect(Collectors.toList()),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private Path createOutputPath(String url) throws IOException {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            Path outputDir = downloadBaseDir;

            if (host != null && !host.equals(targetDomain) && host.endsWith(targetDomain)) {
                String subdomain = host.substring(0, host.length() - targetDomain.length() - 1);
                outputDir = outputDir.resolve("_" + subdomain.replace(".", "_"));
            }
            if (path != null && !path.isEmpty()) {
                String[] pathParts = path.split("/");
                for (String part : pathParts) {
                    if (!part.isEmpty() && !part.contains(".")) {
                        outputDir = outputDir.resolve(part);
                    }
                }
            }

            Files.createDirectories(outputDir);

            String filename = "media_links.txt";
            if (path != null && !path.isEmpty()) {
                int lastSlash = path.lastIndexOf('/');
                if (lastSlash != -1 && lastSlash < path.length() - 1) {
                    String lastPart = path.substring(lastSlash + 1);
                    if (!lastPart.isEmpty()) {
                        filename = lastPart.replaceAll("\\.\\w+$", "") + "_media.txt";
                    }
                }
            }

            return outputDir.resolve(filename);
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL: " + url, e);
        }
    }

    public void extractDownloadAndSaveMediaLinks(String inputFile, String sourceUrl) throws IOException {
        Set<String> uniqueMediaLinks = extractUniqueMediaLinksFromFile(inputFile);
        saveUniqueMediaLinksToFile(uniqueMediaLinks, sourceUrl);
        downloadMediaFiles(uniqueMediaLinks, sourceUrl);
        System.out.println("Processed " + uniqueMediaLinks.size() + " unique media links from: " + sourceUrl);
    }

    public static void main(String[] args) {
        try {
            Properties config = new Properties();
            try (InputStream input = Files.newInputStream(Paths.get("config.properties"))) {
                config.load(input);
            }

            String inputHtml = "input.html";
            String sourceUrl = "https://mail.znu.ac.ir/login/test.html";

            ImageAndAudioDownloader downloader = new ImageAndAudioDownloader(config);
            downloader.extractDownloadAndSaveMediaLinks(inputHtml, sourceUrl);

            Path outputPath = downloader.createOutputPath(sourceUrl);
            Files.lines(outputPath).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("error");
            e.printStackTrace();
        }
    }
}