package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
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
        books.add(new Book("Java Programming", "John Smith", 2020, "978-0134685991"));
        books.add(new Book("Data Structures", "Alice Johnson", 2019, "978-0262033848"));
        books.add(new Book("Algorithms", "Robert Brown", 2018, "978-0321573513"));
        books.add(new Book("Database Systems", "Maria Garcia", 2021, "978-0133970777"));
        books.add(new Book("Web Development", "David Wilson", 2022, "978-1492057092"));
        saveBooks();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public List<Book> searchBooks(String title, String author, Integer year) {
        return books.stream()
                .filter(book -> book.matchesSearch(title, author, year))
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

        books.forEach(System.out::println);
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

        availableBooks.forEach(System.out::println);
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