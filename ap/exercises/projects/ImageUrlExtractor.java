package ap.exercises.projects;

import java.io.IOException;
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

    public static Set<String> extractUniqueImageLinks(String htmlContent) {
        return Stream.of(htmlContent.split("<img"))
                .skip(1)
                .map(part -> {
                    Matcher matcher = SRC_PATTERN.matcher(part);
                    return matcher.find() ? matcher.group(1).trim() : null;
                })
                .filter(src -> src != null && !src.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static Set<String> extractUniqueImageLinksFromFile(String filePath) throws IOException {
        String htmlContent = Files.lines(Paths.get(filePath))
                .collect(Collectors.joining("\n"));
        return extractUniqueImageLinks(htmlContent);
    }

    public static void saveUniqueImageLinksToFile(Set<String> imageLinks, String outputPath) throws IOException {
        Path path = Paths.get(outputPath);
        Files.createDirectories(path.getParent());

        Files.write(
                path,
                imageLinks.stream()
                        .map(link -> link + "\n")
                        .collect(Collectors.toList()),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    public static void extractAndSaveUniqueImageLinks(String inputFile, String outputFile) throws IOException {
        Set<String> uniqueImageLinks = extractUniqueImageLinksFromFile(inputFile);
        saveUniqueImageLinksToFile(uniqueImageLinks, outputFile);
        System.out.println("Extracted " + uniqueImageLinks.size() + " unique image links to: " + outputFile);
    }

    public static void main(String[] args) {
        try {
            String inputHtml = "input.html";
            String outputFile = "output/unique_image_links.txt";

            extractAndSaveUniqueImageLinks(inputHtml, outputFile);
            Files.lines(Paths.get(outputFile))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error");
        }
    }
}