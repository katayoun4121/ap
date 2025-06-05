package ap.exercises.projects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlParser {


    private static final Pattern HREF_PATTERN = Pattern.compile(
            "href\\s*=\\s*['\"]([^'\"]+)['\"]",
            Pattern.CASE_INSENSITIVE
    );


    private static final Pattern IMG_PATTERN = Pattern.compile(
            "<img\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE
    );


    private static final Pattern SRC_PATTERN = Pattern.compile(
            "src\\s*=\\s*['\"]([^'\"]+)['\"]",
            Pattern.CASE_INSENSITIVE
    );

    public static String getFirstUrl(String htmlLine) {
        Matcher matcher = HREF_PATTERN.matcher(htmlLine);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    private static List<String> getAllUrlsFromHtmlLinesStream(Stream<String> htmlLinesStream) throws IOException {
        return htmlLinesStream
                .map(HtmlParser::getFirstUrl)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public static List<String> getAllUrlsFromFile(String filePath) throws IOException {
        return getAllUrlsFromHtmlLinesStream(Files.lines(Path.of(filePath)));
    }

    public static List<String> getAllUrlsFromList(Optional<String> htmlLines) throws IOException {
        return getAllUrlsFromHtmlLinesStream(htmlLines.stream());
    }
    public static List<String> getImageUrlsFromHtml(String htmlContent) {
        List<String> imageUrls = new ArrayList<>();


        Matcher imgTagMatcher = IMG_PATTERN.matcher(htmlContent);
        while (imgTagMatcher.find()) {
            String srcValue = imgTagMatcher.group(1).trim();
            if (!srcValue.isEmpty()) {
                imageUrls.add(srcValue);
            }
        }
        if (imageUrls.isEmpty()) {
            Matcher srcAttrMatcher = SRC_PATTERN.matcher(htmlContent);
            while (srcAttrMatcher.find()) {
                String srcValue = srcAttrMatcher.group(1).trim();
                if (!srcValue.isEmpty()) {
                    imageUrls.add(srcValue);
                }
            }
        }

        return imageUrls;
    }


    public static List<String> getImageUrlsFromFile(String filePath) throws IOException {
        String htmlContent = Files.readString(Path.of(filePath));
        return getImageUrlsFromHtml(htmlContent);
    }

    public static List<String> getImageUrlsFromList(List<String> htmlLines) {
        String htmlContent = String.join("\n", htmlLines);
        return getImageUrlsFromHtml(htmlContent);
    }
}