import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

public class DomainHtmlScraper {
    private final String mainDomain;
    private final Queue<String> urlQueue = new LinkedList<>();
    private final Set<String> visitedUrls = new HashSet<>();
    private final List<String> allImageUrls = new ArrayList<>();
    private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();
    private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    private final List<String> allImageUrls = Collections.synchronizedList(new ArrayList<>());
    private final Path baseSavePath;
    private final Path imagesPath;
    private final Path songsPath;
    private final ExecutorService executorService;
    private final int threadCount;
    private final CountDownLatch completionLatch;

    public DomainHtmlScraper(String domainAddress, String savePath) throws URISyntaxException, IOException {
        URI uri = new URI(domainAddress);
@@ -29,6 +32,10 @@ public DomainHtmlScraper(String domainAddress, String savePath) throws URISyntax
        this.imagesPath = Paths.get(savePath, "images");
        this.songsPath = Paths.get(savePath, "songs");

        this.threadCount = Conf.THREAD_COUNT > 0 ? Conf.THREAD_COUNT : 1;
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.completionLatch = new CountDownLatch(threadCount);
        Files.createDirectories(baseSavePath);
        Files.createDirectories(imagesPath);
        Files.createDirectories(songsPath);
@@ -38,44 +45,70 @@ public DomainHtmlScraper(String domainAddress, String savePath) throws URISyntax
    }

    public void start() throws IOException, InterruptedException {
        int counter = 0;
        if (threadCount > 1) {

        while (!urlQueue.isEmpty() && counter < 100) {
            String currentUrl = urlQueue.remove();
            counter++;
            for (int i = 0; i < threadCount; i++) {
                executorService.submit(new CrawlerTask());
            }
            completionLatch.await();
            executorService.shutdown();
        } else {
            int counter = 0;
            while (!urlQueue.isEmpty() && counter < 100) {
                processNextUrl();
                counter++;
            }
        }
        System.out.printf("Crawl complete. Processed %d pages.%n", visitedUrls.size());
    }

    private class CrawlerTask implements Runnable {
        @Override
        public void run() {
            try {
                while (!urlQueue.isEmpty()) {
                    processNextUrl();
                }
            } finally {
                completionLatch.countDown();
            }
        }
    }

                TimeUnit.SECONDS.sleep(Conf.DOWNLOAD_DELAY_SECONDS);
    private void processNextUrl() {
        String currentUrl = urlQueue.poll();
        if (currentUrl == null) {
            return;
        }

                System.out.printf("[%d] Processing: %s%n", counter, currentUrl);
        try {
            TimeUnit.SECONDS.sleep(Conf.DOWNLOAD_DELAY_SECONDS);

                Path savePath = determineSavePath(currentUrl);
                Files.createDirectories(savePath.getParent());
            System.out.printf("[Thread-%d] Processing: %s%n", Thread.currentThread().getId(), currentUrl);

                List<String> htmlLines = HtmlFetcher.fetchHtml(currentUrl);
                saveHtmlContent(htmlLines, savePath);
            Path savePath = determineSavePath(currentUrl);
            Files.createDirectories(savePath.getParent());

            List<String> htmlLines = HtmlFetcher.fetchHtml(currentUrl);
            saveHtmlContent(htmlLines, savePath);

                processResources(htmlLines, currentUrl);
            processResources(htmlLines, currentUrl);

                System.out.printf("Queue size: %d%n", urlQueue.size());
            } catch (Exception e) {
                System.err.printf("Error processing %s: %s%n", currentUrl, e.getMessage());
            }
            System.out.printf("Queue size: %d%n", urlQueue.size());
        } catch (Exception e) {
            System.err.printf("Error processing %s: %s%n", currentUrl, e.getMessage());
        }
        System.out.printf("Crawl complete. Processed %d pages.%n", counter);
    }
    private void processResources(List<String> htmlLines, String baseUrl) throws URISyntaxException, IOException {

    private void processResources(List<String> htmlLines, String baseUrl) throws URISyntaxException, IOException {
        List<String> imageUrls = HtmlParser.extractAllImageUrls(htmlLines);
        downloadResources(imageUrls, imagesPath, baseUrl);

        List<String> audioUrls = HtmlParser.extractAllAudioUrls(htmlLines);
        downloadResources(audioUrls, songsPath, baseUrl);

        for (String url : HtmlParser.extractAllUrls(htmlLines)) {
            if (!visitedUrls.contains(url)) {
                String absoluteUrl = toAbsoluteUrl(url, baseUrl);
@@ -94,26 +127,23 @@ private Path determineSavePath(String url) throws URISyntaxException {

        Path savePath = baseSavePath;

        if (!subdomain.isEmpty()) {
            savePath = savePath.resolve("_" + subdomain);
        }

        if (path == null || path.isEmpty() || path.equals("/")) {
            return savePath.resolve("index.html");
        }

        String filePath = path.startsWith("/") ? path.substring(1) : path;

        if (path.endsWith("/")) {
            return savePath.resolve(filePath).resolve("index.html");
        }

        return savePath.resolve(filePath);
    }
    private void downloadResources(List<String> urls, Path targetDir, String baseUrl)
            throws URISyntaxException, IOException {
        for (String url : urls) {
@@ -134,6 +164,7 @@ private void downloadResources(List<String> urls, Path targetDir, String baseUrl
            }
        }
    }
    private String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
@@ -148,7 +179,6 @@ private String getSubdomain(URI uri) {
    }

    private void saveHtmlContent(List<String> htmlLines, Path savePath) throws IOException {
        Files.createDirectories(savePath.getParent());

        try (PrintWriter out = new PrintWriter(savePath.toFile(), "UTF-8")) {
@@ -158,23 +188,6 @@ private void saveHtmlContent(List<String> htmlLines, Path savePath) throws IOExc
        }
    }

    private void processLinks(List<String> htmlLines, String currentUrl) throws URISyntaxException {
        List<String> imageUrls = HtmlParser.extractAllImageUrls(htmlLines);
        allImageUrls.addAll(imageUrls);
        for (String url : HtmlParser.extractAllUrls(htmlLines)) {
            if (!visitedUrls.contains(url)) {
                String absoluteUrl = toAbsoluteUrl(url, currentUrl);
                if (isSameDomain(absoluteUrl)) {
                    visitedUrls.add(absoluteUrl);
                    urlQueue.add(absoluteUrl);
                }
            }
        }
    }
    private String toAbsoluteUrl(String url, String baseUrl) throws URISyntaxException {
        if (url.startsWith("http")) {
            return url;
@@ -193,9 +206,4 @@ private boolean isSameDomain(String url) throws URISyntaxException {
        String host = uri.getHost();
        return host != null && (host.equals(mainDomain) || host.endsWith("." + mainDomain));
    }
    private void saveImageUrls() throws IOException {
        Path imageListPath = baseSavePath.resolve("image_urls.txt");
        Files.write(imageListPath, allImageUrls);
    }
    }
