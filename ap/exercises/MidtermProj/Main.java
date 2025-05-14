package ap.exercises.MidtermProj;

import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        LibraryManager libraryManager= new LibraryManager();
        Scanner scan= new Scanner(System.in);
        System.out.println("select the user type");
        System.out.println("1. student");
        System.out.println("2. librarian");
        System.out.println("3.library manager");
        System.out.println("your choice:");
        int userType= scan.nextInt();
        scan.nextLine();
        libraryManager.handleUserType(userType,scan);
    }
}