package ap.exercises.projects;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DomainHtmlScraper {
    private String domainAddress;
    private Queue<String> queue;
    private Set<String> visitedUrls;
    private HtmlFileManager htmlFileManager;
    private FileWriter imageLinksWriter;

    public DomainHtmlScraper(String domainAddress, String savePath) throws IOException {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.visitedUrls = new HashSet<>();
        this.htmlFileManager = new HtmlFileManager(savePath);
        this.imageLinksWriter = new FileWriter("image_links.txt", true); // فایل ذخیره لینک تصاویر
    }

    public void start() throws IOException {
        queue.add(domainAddress);
        int counter = 0;

        while (!queue.isEmpty()) {
            String url = queue.remove();

            if (visitedUrls.contains(url)) {
                continue;
            }

            visitedUrls.add(url);
            counter++;

            try {
                System.out.println("[" + counter + "] Processing: " + url);


                String htmlLines = HtmlFetcher.fetchHtml(domainAddress);
                this.htmlFileManager.save(htmlLines);

                extractAndSaveImageLinks(htmlLines, url);
                List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
                for (String newUrl : urls) {
                    if (!visitedUrls.contains(newUrl) && newUrl.startsWith(domainAddress)) {
                        queue.add(newUrl);
                    }
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + url + "\t -> " + e.getMessage());
            }

            System.out.println("Queue size: " + queue.size());
        }

        imageLinksWriter.close();
        System.out.println("Operation complete. Total pages processed: " + counter);
    }

    private void extractAndSaveImageLinks(String htmlLines, String pageUrl) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        for (String line : htmlLines) {
            htmlContent.append(line).append("\n");
        }
        List<String> imageLinks = HtmlParser.getImageUrlsFromHtml(htmlContent.toString());

        for (String imgUrl : imageLinks) {

            String absoluteUrl = makeAbsoluteUrl(imgUrl, pageUrl);
            imageLinksWriter.write(absoluteUrl + "\n");
            System.out.println("Found image: " + absoluteUrl);
        }
        imageLinksWriter.flush();
    }

    private String makeAbsoluteUrl(String url, String baseUrl) {
        if (url.startsWith("http")) {
            return url;
        }
        if (url.startsWith("/")) {
            return domainAddress + url;
        }
        return baseUrl + "/" + url;
    }
}