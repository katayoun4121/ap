package ap.exercises.ex3;

import java.io.FileWriter;
import java.io.IOException;

public class Main_EX3_LM_1_2_A {
    public static void main(String[] args) {
        String[] Books = {"Science", "Chemist", "Mathematics", "Art gallery"};
        String[] Students = {"Nilo Ahmadi", "Mina Safavi", "Ali Amiri"};
        saveToFile("Books.txt", Books);
        saveToFile("Students.txt", Students);
        System.out.println("information saved successfully.");
    }

    public static void saveToFile(String fileName, String[] data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String item : data) {
                writer.write(item + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error occured.");
        }
    }
}
