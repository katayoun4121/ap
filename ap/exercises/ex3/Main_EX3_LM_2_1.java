package ap.exercises.ex3;
import java.io.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
class Bookloan{
    private String studentId;
    private String bookId;
    private String date;
    public Bookloan(String studentId, String bookId, String date){
        this.studentId=studentId;
        this.bookId=bookId;
        this.date=date;
    }


    public String toString(){
        return "student id:"+studentId+"book id:"+bookId+"date"+date;
    }
}

public class Main_EX3_LM_2_1 {
private static ArrayList<Bookloan> loans = new ArrayList<>();
private static final String fileName="loans.txt";

    public static void main(String[] args) {
        loadfromfile();
        Scanner scan=new Scanner(System.in);
        System.out.println("1.add book\n2.show loans\n3.save and quit");
        int option =scan.nextInt();
        scan.nextLine();
        if (option==1){
            System.out.println("student id:");
            String studentId=scan.nextLine();
            System.out.println("book id:");
            String bookId= scan.nextLine();
            System.out.println("date:");
            String date= scan.nextLine();
            loans.add(new Bookloan(studentId, bookId, date));
        } else if (option==2) {
            loans.forEach(System.out::println);
        }
        saveToFile();

    }

    private static void loadfromfile(){
        try(Scanner fileScanner= new Scanner(new File(fileName))){
            while (fileScanner.hasNextLine()){
                String[] data=fileScanner.nextLine().split(",");
                loans.add(new Bookloan(data[0], data[1], data[2]));
            }
        }
            catch (FileNotFoundException e){
                System.out.println("error found.");
            }
        }
        private static void saveToFile(){
            try(PrintWriter writer= new PrintWriter(new FileWriter(fileName))){
                for (Bookloan loan: loans){
                    writer.println(loan.toString());
                }
                System.out.println("data saved successfully.");
            }catch(IOException e){
                System.out.println("error.");
            }

    }
}
