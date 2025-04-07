package ap.exercises.ex3;

import java.io.*;
import java.util.ArrayList;

public class Main_EX3_LM_1_2_C {
    public static void saveArrayToFile(String fileName, String[] data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String item : data) {
                writer.write(item + "\n");
            }
            System.out.println("information saved successfully.");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static String[] readFileToArray(String fileName) {
        ArrayList<String> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }
        } catch (IOException e) {
            System.out.println("error");
        }

        return dataList.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] books = {"mathematics", "chemist", "science", "art"
        };

        String[] students = {"nilo", "kiana", "faeze"};
        saveArrayToFile("books.txt", books);
        saveArrayToFile("students.txt", students);

        String[] loadedBooks = readFileToArray("books.txt");
        String[] loadedStudents = readFileToArray("students.txt");
        System.out.println("---------------------");
        System.out.println("loaded books from file.");
        for (String book : loadedBooks) {
            System.out.println(book);
        }
        System.out.println("---------------------");
        System.out.println("loaded students from file");
        for (String student : loadedStudents) {
            System.out.println(student);
        }
    }
}
