
package ap.exercises.projects;

import ap.exercises.projects.Conf;
import ap.exercises.projects.HtmlParser;
import ap.exercises.projects.DirectoryTools;
import ap.exercises.projects.FileTools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlAnalyzer {
    private static List<String> fileList = DirectoryTools.getFilesAbsolutePathInDirectory(Conf.SAVE_DIRECTORY);

    public static List<String> getAllUrls() {
        return fileList.stream()
                .map(FileTools::getTextFileLines)
                .filter(s -> s != null)
                .flatMap(List::stream)
                .map(HtmlParser::getFirstUrl)
                .filter(s -> s != null && s.length() > 1)
                .collect(Collectors.toList());
    }

    public static List<String> getTopUrls(int k){
        Map<String, Long> urlCount = getAllUrls().stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return urlCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static void printTopCountUrls(int k){
        ObjectCounter urlCounter = new ObjectCounter(); // بدون <String>
        getAllUrls().forEach(urlCounter::add);
        for (Map.Entry<String, Integer> urlCountEntry : urlCounter.getTop(k)) {
            System.out.println(urlCountEntry.getKey() + " -> " + urlCountEntry.getValue());
        }
    }

    public static void main(String[] args) {
        HtmlAnalyzer.printTopCountUrls(10);
        System.out.println("____________________");
        HtmlAnalyzer.getTopUrls(10).forEach(System.out::println);
    }
}