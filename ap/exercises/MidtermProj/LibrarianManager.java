package ap.exercises.MidtermProj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarianManager {
    private List<Librarian> librarians;
    public LibrarianManager(){
        this.librarians=new ArrayList<>();
    }
    public void showLibrarianMenu(Scanner scan) {
        System.out.println("\n---librarian menu---");
        System.out.println("1.log in");
        System.out.println("2.edit personal info");
        System.out.println("3.adding books");
        System.out.println("your choice:");
        int choice = scan.nextInt();
        scan.nextLine();
        switch (choice) {
            case 1:
                loginLibrarian(scan);
                break;
            case 2:
                editLibrarianInfo(scan);
                break;
            case 3:
                addBook(scan);
                break;
        }
    }
    private void loginLibrarian(Scanner scan){
        System.out.println("\n---librarian log in---");
        System.out.println("username");
        String username= scan.nextLine();
        System.out.println("password");
        String password= scan.nextLine();
        for (Librarian librarian: librarians){
            if (librarian.getUsername().equals(username)&& librarian.getPassword().equals(password)){
                System.out.println("logged in");
                return;
            }
        }
        System.out.println("username or password is wrong.");
    }
    private  void editLibrarianInfo(Scanner scan){
        System.out.println("\n---edit librarian's info---");
        System.out.println("username");
        String username= scan.nextLine();
        Librarian librarian=findLibrarian(username);
        if (librarian==null){
            System.out.println("not found");
            return;
        }
        System.out.println(" new password");
        String password= scan.nextLine();
        System.out.println("new fullname:");
        String fullname= scan.nextLine();
        librarian.setPassword(password);
        librarian.setFullName(fullname);
        System.out.println("edited successfully.");
    }
    private void addBook(Scanner scan){
        System.out.println("\n---add book---");
        System.out.println("book title");
        String title= scan.nextLine();
        System.out.println("author:");
        String author= scan.nextLine();
        System.out.println("book"+title+"added");
    }
    private Librarian findLibrarian(String username){
        for (Librarian librarian: librarians){
            if (librarian.getUsername().equals(username)){
                return librarian;
            }
        }
        return null;
    }
    public List<Librarian> getLibrarians(){
        return  librarians;
    }

    public void setLibrarians(List<Librarian> librarians) {
        this.librarians = librarians;
    }
}
