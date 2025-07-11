package ap.exercises.projects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public final class DirectoryTools {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private DirectoryTools() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static boolean directoryExists(String directoryPath) {
        if (directoryPath == null || directoryPath.trim().isEmpty()) {
            return false;
        }

        Path path = Paths.get(directoryPath);
        return Files.exists(path) && Files.isDirectory(path);
    }

    public static boolean createDirectory(String directoryPath) {
        if (directoryPath == null || directoryPath.trim().isEmpty()) {
            return false;
        }

        try {
            Path path = Paths.get(directoryPath);
            if (!directoryExists(directoryPath)) {
                Files.createDirectories(path);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Directory creation failed: " + e.getMessage());
            return false;
        }
    }

    public static String createDirectoryWithTimeStamp(String directroyPath){
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String finalPath = directroyPath + "" + timestamp;
        createDirectory(directroyPath+""+timestamp);
        return finalPath;
    }

    /**
     * Gets all regular files (non-directories) in the specified directory
     * @param directoryPath Path to the directory to scan
     * @return List of File objects for each file in the directory
     * @throws IllegalArgumentException if the path is not a directory
     */
    public static List<File> getFilesInDirectory(String directoryPath) {
        File dir = new File(directoryPath);

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + directoryPath);
        }

        List<File> files = new ArrayList<>();
        File[] dirContents = dir.listFiles();

        if (dirContents != null) {
            for (File file : dirContents) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
        }

        return files;
    }


    public static List<String> getFilesAbsolutePathInDirectory(String directoryPath) {
        return DirectoryTools.getFilesInDirectory(directoryPath).stream()
                .map(s -> s.getAbsolutePath())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String testDir = "./test/directory";

        System.out.println("Directory exists (before): " + directoryExists(testDir));
        System.out.println("Creation result: " + createDirectory(testDir));
        System.out.println("Directory exists (after): " + directoryExists(testDir));
    }
}
