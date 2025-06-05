package ap.exercises.projects;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Optional;

public class HtmlFetcher {
    private static final HttpClient DEFAULT_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    public static Optional<String> fetchHtml(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = DEFAULT_CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                return Optional.empty();
            }

            return Optional.of(response.body());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String fetchHtmlFromUrl(String url) throws IOException, InterruptedException {
        return fetchHtmlFromUrl(url, 10);
    }
    public static String fetchHtmlFromUrl(String url, int timeoutSeconds) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            throw new IOException("error");
        }

        return response.body();
    }


    public static void fetchAndSaveHtml(String url, Path outputPath) throws IOException, InterruptedException {
        String htmlContent = fetchHtmlFromUrl(url);
        Files.createDirectories(outputPath.getParent());
        Files.writeString(
                outputPath,
                htmlContent,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }


    public static String fetchHtmlWithTimeout(String url, int timeoutSeconds) throws IOException, InterruptedException {
        return fetchHtmlFromUrl(url, timeoutSeconds);
    }
}