package ap.exercises.EX7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    private String studentId;
    private String name;
    private List<String> borrowedBooks;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(String bookId) {
        borrowedBooks.add(bookId);
    }

    public void returnBook(String bookId) {
        borrowedBooks.remove(bookId);
    }

    public static void showStudentMenu(Scanner scanner, LibrarySystem system, User currentUser) {
        while (true) {
            System.out.println("\n--- std menu ---");
            System.out.println("1.Registering std.");
            System.out.println("2.log in");
            System.out.println("3.search book");
            System.out.println("4.show list of borrowed books");
            System.out.println("5.exit");

            System.out.print("your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerStudent(scanner, system);
                    break;
                case 2:
                    loginStudent(scanner, system);
                    break;
                case 3:
                    searchBook(scanner, system);
                    break;
                case 4:
                    viewBorrowedBooks(scanner, system, currentUser);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("invalid choice.");
            }
        }
    }

    private static void registerStudent(Scanner scanner, LibrarySystem system) {
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        System.out.print("full name ");
        String name = scanner.nextLine();

        String studentId = "STU-" + System.currentTimeMillis();
        system.addUser(new User(studentId, username, password, "student"));
        system.addStudent(new Student(studentId, name));
        System.out.println("registered successfully");
    }

    private static void loginStudent(Scanner scanner, LibrarySystem system) {
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password ");
        String password = scanner.nextLine();

        User user = system.authenticateUser(username, password);
        if (user != null && user.getRole().equals("student")) {
            System.out.println("logged in");
            showStudentMenu(scanner, system, user);
        } else {
            System.out.println("password or username invalid");
        }
    }

    private static void searchBook(Scanner scanner, LibrarySystem system) {
        System.out.print("enter title or author of the book. ");
        String query = scanner.nextLine();
        List<Book> results = system.searchBooks(query);

        if (results.isEmpty()) {
            System.out.println("book not found");
        } else {
            System.out.println("more result");
            for (Book book : results) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() +
                        " situation: " + (book.isAvailable() ? "valid" : "borrowed"));
            }
        }
    }

    private static void viewBorrowedBooks(Scanner scanner, LibrarySystem system, User currentUser) {
        if (currentUser == null) {
            System.out.println("log in first");
            return;
        }

        List<BorrowRecord> records = system.getBorrowedBooksByUser(currentUser.getId());
        if (records.isEmpty()) {
            System.out.println("no book borrowed");
        } else {
            System.out.println("your borrowed books:");
            for (BorrowRecord record : records) {
                Book book = system.getBookById(record.getBookId());
                System.out.println(book.getTitle() + "borrow date: " + record.getBorrowDate());
            }
        }
    }
}
