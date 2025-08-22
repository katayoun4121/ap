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
        books.add(new Book("Java Programming", "Brad Pitt", 2020, "1111"));
        books.add(new Book("Data Structures", "Jack Johnson", 2019, "2222"));
        books.add(new Book("Algorithms", "Robert smith", 2018, "3333"));
        books.add(new Book("Database Systems", " Garcia Morone", 2021, "4444"));
        books.add(new Book("Algorythm","Mark Tin",2011,"5555"));
        saveBooks();
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
            System.out.println(book.getBookInfo());
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
                System.out.println(book.getBookInfo());
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