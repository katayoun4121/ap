package ap.exercises.ex3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Main_EX3_LM_1_2_B {
    public static void main(String[] args) {
        String[] books=readFromFile("books.txt");
        String[] students=readFromFile("students.txt");
        System.out.println("students:  ");
        for (String student: students){
            System.out.println(student);
        }
        System.out.println("books:  ");
        for (String book: books){
            System.out.println(book);
        }
    }
    public static String[] readFromFile(String fileName){
        ArrayList<String> datalist =new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(fileName))){
           String line;
           while ((line=reader.readLine())!=null){
               datalist.add(line);
           }
        } catch (IOException e) {
            System.out.println("Error");
        }
        return datalist.toArray(new String[0]);
    }
}
