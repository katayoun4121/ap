package ap.projects.finalproject;

import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private StudentManager studentManager;
    private BookManager bookManager;
    private MenuHandler menuHandler;

    public LibrarySystem() {
        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.menuHandler = new MenuHandler(this);
    }

    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
    }

    public Student authenticateStudent(String username, String password) {
        return studentManager.authenticateStudent(username, password);
    }

    public void editStudentInformation(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Edit Student Information ---");

        System.out.print("New name (current: " + student.getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            System.out.println("Name change functionality not fully implemented.");
        }

        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            System.out.println("Password change functionality not fully implemented.");
        }

        System.out.println("Information updated successfully.");
    }

    public void borrowBook(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Borrow a Book ---");

        bookManager.displayAvailableBooks();

        System.out.print("Enter ISBN of the book you want to borrow: ");
        String isbn = scanner.nextLine();

        if (bookManager.borrowBook(isbn)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book not available or invalid ISBN.");
        }
    }

    public void returnBook(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Return a Book ---");

        System.out.print("Enter ISBN of the book you want to return: ");
        String isbn = scanner.nextLine();

        if (bookManager.returnBook(isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Invalid ISBN or book was not borrowed.");
        }
    }

    public void displayAvailableBooks() {
        bookManager.displayAvailableBooks();
    }

    public void searchBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Search Books ---");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author:  ");
        String author = scanner.nextLine();

        System.out.print("Publication year:  ");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Book> results = bookManager.searchBooks(
                title.isEmpty() ? null : title,
                author.isEmpty() ? null : author,
                year == 0 ? null : year
        );

        System.out.println("\n--- Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        system.start();
    }
}