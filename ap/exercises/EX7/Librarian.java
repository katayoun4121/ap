package ap.exercises.EX7;

import java.util.Scanner;

public class Librarian {
    public static void showLibrarianMenu(Scanner scanner, LibrarySystem system, User currentUser) {
        while (true) {
            System.out.println("\n--- librarian menu ---");
            System.out.println("1.log in");
            System.out.println("2.edit information.");
            System.out.println("3.add new book");
            System.out.println("4.exit");

            System.out.print("your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginLibrarian(scanner, system);
                    break;
                case 2:
                    editProfile(scanner, system, currentUser);
                    break;
                case 3:
                    addNewBook(scanner, system);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("invalid");
            }
        }
    }

    private static void loginLibrarian(Scanner scanner, LibrarySystem system) {
        System.out.print("username ");
        String username = scanner.nextLine();
        System.out.print("password ");
        String password = scanner.nextLine();

        User user = system.authenticateUser(username, password);
        if (user != null && user.getRole().equals("librarian")) {
            System.out.println("logged in");
            showLibrarianMenu(scanner, system, user);
        } else {
            System.out.println("password or username invalid");
        }
    }

    private static void editProfile(Scanner scanner, LibrarySystem system, User currentUser) {
        if (currentUser == null) {
            System.out.println("log in first");
            return;
        }

        System.out.print("new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("password edited");
    }

    private static void addNewBook(Scanner scanner, LibrarySystem system) {
        System.out.print("bookId: ");
        String id = scanner.nextLine();
        System.out.print("book title:");
        String title = scanner.nextLine();
        System.out.print("book author");
        String author = scanner.nextLine();

        Book newBook = new Book(id, title, author);
        system.addBook(newBook);
        System.out.println("book added.");
    }
}
