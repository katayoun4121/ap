package ap.exercises.EX7;

import java.util.List;
import java.util.Scanner;

public class Admin {
    public static void showAdminMenu(Scanner scanner, LibrarySystem system, User currentUser) {
        while (true) {
            System.out.println("\n--- admin menu ---");
            System.out.println("1. add librarian");
            System.out.println("2. show list of borrowed books");
            System.out.println("3. show list of top 10 books");
            System.out.println("4. exit");

            System.out.print("your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewLibrarian(scanner, system);
                    break;
                case 2:
                    viewAllBorrowedBooks(system);
                    break;
                case 3:
                    viewTop10Books(system);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("invalid");
            }
        }
    }

    private static void addNewLibrarian(Scanner scanner, LibrarySystem system) {
        System.out.print("username ");
        String username = scanner.nextLine();
        System.out.print("password");
        String password = scanner.nextLine();

        String librarianId = "LIB-" + System.currentTimeMillis();
        system.addUser(new User(librarianId, username, password, "librarian"));
        System.out.println("librarian added.");
    }

    private static void viewAllBorrowedBooks(LibrarySystem system) {
        List<BorrowRecord> records = system.getAllBorrowedBooks();
        if (records.isEmpty()) {
            System.out.println("no book is borrowed");
        } else {
            System.out.println("list of borrowed books:");
            for (BorrowRecord record : records) {
                Book book = system.getBookById(record.getBookId());
                User user = system.getUserById(record.getUserId());
                System.out.println(book.getTitle() + " by " + user.getUsername() +
                        " at date: " + record.getBorrowDate());
            }
        }
    }

    private static void viewTop10Books(LibrarySystem system) {
        List<Book> topBooks = system.getTop10BorrowedBooks();
        if (topBooks.isEmpty()) {
            System.out.println("no book is borrowed.");
        } else {
            System.out.println("top 10 books:");
            for (int i = 0; i < topBooks.size() && i < 10; i++) {
                Book book = topBooks.get(i);
                System.out.println((i+1) + ". " + book.getTitle() + " by " + book.getAuthor() +
                        " number of borrowed books: " + book.getBorrowCount());
            }
        }
    }
}
