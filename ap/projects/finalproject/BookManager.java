package ap.projects.finalproject;

import java.io.*;
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
        books.add(new Book("Java Programming", "Brad Pitt", 2020, "1111"));
        books.add(new Book("Data Structures", "Jack Johnson", 2019, "2222"));
        books.add(new Book("Algorithms", "Robert smith", 2018, "3333"));
        books.add(new Book("Database Systems", " Garcia Morone ", 2021, "4444"));
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

        books.forEach(book -> {
            System.out.println(book);
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