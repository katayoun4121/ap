package ap.exercises.projects;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageUrlExtractor {

    private static final Pattern IMG_PATTERN = Pattern.compile(
            "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SRC_PATTERN = Pattern.compile(
            "src\\s*=\\s*['\"]([^'\"]+)['\"]",
            Pattern.CASE_INSENSITIVE
    );

    private final String targetDomain;
    private final Path downloadBaseDir;

    public ImageUrlExtractor(String targetDomain, String downloadBaseDir) {
        this.targetDomain = normalizeDomain(targetDomain);
        this.downloadBaseDir = Paths.get(downloadBaseDir);
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

    public Set<String> extractUniqueImageLinks(String htmlContent) {
        return Stream.of(htmlContent.split("<img"))
                .skip(1)
                .map(part -> {
                    Matcher matcher = SRC_PATTERN.matcher(part);
                    return matcher.find() ? matcher.group(1).trim() : null;
                })
                .filter(src -> src != null && !src.isEmpty())
                .filter(this::isSameDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<String> extractUniqueImageLinksFromFile(String filePath) throws IOException {
        String htmlContent = Files.readString(Paths.get(filePath));
        return extractUniqueImageLinks(htmlContent);
    }

    public void saveUniqueImageLinksToFile(Set<String> imageLinks, String url) throws IOException {
        Path outputPath = createOutputPath(url);
        Files.createDirectories(outputPath.getParent());

        Files.write(
                outputPath,
                imageLinks.stream()
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

            String filename = "image_links.txt";
            if (path != null && !path.isEmpty()) {
                int lastSlash = path.lastIndexOf('/');
                if (lastSlash != -1 && lastSlash < path.length() - 1) {
                    String lastPart = path.substring(lastSlash + 1);
                    if (!lastPart.isEmpty()) {
                        filename = lastPart.replaceAll("\\.\\w+$", "") + "_images.txt";
                    }
                }
            }

            return outputDir.resolve(filename);
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL: " + url, e);
        }
    }

    public void extractAndSaveUniqueImageLinks(String inputFile, String sourceUrl) throws IOException {
        Set<String> uniqueImageLinks = extractUniqueImageLinksFromFile(inputFile);
        saveUniqueImageLinksToFile(uniqueImageLinks, sourceUrl);
        System.out.println("Extracted " + uniqueImageLinks.size() + " unique image links from: " + sourceUrl);
    }

    public static void main(String[] args) {
        try {
            String targetDomain = "znu.ac.ir";
            String downloadBaseDir = "downloaded_pages";
            String inputHtml = "input.html";
            String sourceUrl = "https://mail.znu.ac.ir/login/test.html";

            ImageUrlExtractor extractor = new ImageUrlExtractor(targetDomain, downloadBaseDir);
            extractor.extractAndSaveUniqueImageLinks(inputHtml, sourceUrl);

            Path outputPath = extractor.createOutputPath(sourceUrl);
            Files.lines(outputPath).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}