package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books;
    private BookFileManager fileManager;

    public BookManager() {
        this.fileManager = new BookFileManager();
        this.books = fileManager.loadBooks();
        if (this.books == null) {
            this.books = new ArrayList<>();
            initializeSampleBooks();
        }
    }

    private void initializeSampleBooks() {
        books.add(new Book("Java Programming", "Brad Pitt", 2020, "1111"));
        books.add(new Book("Data Structures", "Jack Johnson", 2019, "2222"));
        books.add(new Book("Algorithms", "Robert smith", 2018, "3333"));
        books.add(new Book("Database Systems", " Garcia Morone", 2021, "4444"));
        books.add(new Book("Algorythm", "Mark Tin", 2011, "5555"));
        saveBooks();
    }

    public List<Book> searchBooks(String title, String author, String isbn) {
        return books.stream()
                .filter(book ->
                        (title == null || title.isEmpty() || book.matchesTitleSearch(title)) &&
                                (author == null || author.isEmpty() || book.matchesAuthorSearch(author)) &&
                                (isbn == null || isbn.isEmpty() || book.matchesIsbnSearch(isbn))
                )
                .collect(Collectors.toList());
    }

    public boolean editBook(String isbn, String newTitle, String newAuthor, Integer newYear) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            if (newTitle != null && !newTitle.isEmpty()) {
                book.setTitle(newTitle);
            }
            if (newAuthor != null && !newAuthor.isEmpty()) {
                book.setAuthor(newAuthor);
            }
            if (newYear != null && newYear > 0) {
                book.setPublicationYear(newYear);
            }
            saveBooks();
            return true;
        }
        return false;
    }

    public void editBookFromInput(Scanner scanner, String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return;
        }

        System.out.println("\nCurrent book information:");
        System.out.println(book.getFullBookInfo());

        System.out.println("\nEnter new information (press Enter to keep current value):");

        System.out.print("New title (current: " + book.getTitle() + "): ");
        String newTitle = scanner.nextLine();

        System.out.print("New author (current: " + book.getAuthor() + "): ");
        String newAuthor = scanner.nextLine();

        System.out.print("New publication year (current: " + book.getPublicationYear() + "): ");
        String yearInput = scanner.nextLine();
        Integer newYear = null;

        if (!yearInput.isEmpty()) {
            try {
                newYear = Integer.parseInt(yearInput);
                if (newYear < 1000 || newYear > 2100) {
                    System.out.println("Invalid year. Must be between 1000 and 2100.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format.");
                return;
            }
        }

        if (editBook(isbn, newTitle, newAuthor, newYear)) {
            System.out.println("Book information updated successfully!");
            System.out.println("Updated information:");
            System.out.println(findBookByIsbn(isbn).getFullBookInfo());
        } else {
            System.out.println("Failed to update book information.");
        }
    }

    public void searchBooksFromInput(Scanner scanner) {
        System.out.println("\n--- Advanced Book Search ---");

        System.out.print("Title (press Enter to skip): ");
        String title = scanner.nextLine();

        System.out.print("Author (press Enter to skip): ");
        String author = scanner.nextLine();

        System.out.print("ISBN (press Enter to skip): ");
        String isbn = scanner.nextLine();

        List<Book> results = searchBooks(
                title.isEmpty() ? null : title,
                author.isEmpty() ? null : author,
                isbn.isEmpty() ? null : isbn
        );

        displaySearchResults(results);
    }

    public void addNewBook(Book book) {
        if (isIsbnTaken(book.getIsbn())) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        books.add(book);
        saveBooks();
        System.out.println("Book added successfully!");
    }

    private boolean isIsbnTaken(String isbn) {
        return books.stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    public Book createBookFromInput(Scanner scanner) {
        System.out.println("\n--- Add New Book ---");

        System.out.print("Book title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Publication year: ");
        int year = getIntInput(scanner, 1000, 2100);

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        if (isbn.isEmpty() || isbn.length() < 10) {
            System.out.println("Invalid ISBN. Please enter a valid ISBN (minimum 10 characters).");
            return null;
        }

        return new Book(title, author, year, isbn);
    }

    private int getIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public int getTotalBooksCount() {
        return books.size();
    }

    public int getAvailableBooksCount() {
        return (int) books.stream()
                .filter(Book::isAvailable)
                .count();
    }

    public int getBorrowedBooksCount() {
        return (int) books.stream()
                .filter(book -> !book.isAvailable())
                .count();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return new ArrayList<>();
        }

        return books.stream()
                .filter(book -> book.matchesTitleSearch(title))
                .collect(Collectors.toList());
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public void displayAllBooks() {
        System.out.println("\n--- All Books in Library ---");
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        books.forEach(book -> {
            System.out.println(book.getFullBookInfo());
        });
    }

    public void displayAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        List<Book> availableBooks = books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());

        if (availableBooks.isEmpty()) {
            System.out.println("No books available at the moment.");
            return;
        }

        availableBooks.forEach(book -> {
            System.out.println(book.getBookInfo());
        });
    }

    public void displaySearchResults(List<Book> results) {
        if (results.isEmpty()) {
            System.out.println("No books found matching your criteria.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(book -> {
                System.out.println(book.getFullBookInfo());
            });
            System.out.println("Found " + results.size() + " book(s)");
        }
    }

    public boolean borrowBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            saveBooks();
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            saveBooks();
            return true;
        }
        return false;
    }

    private void saveBooks() {
        fileManager.saveBooks(books);
    }
}