package ap.exercises.EX7;

import java.util.*;

public class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<Student> students;
    private List<BorrowRecord> borrowRecords;
    private DataStorage storage;

    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.students = new ArrayList<>();
        this.borrowRecords = new ArrayList<>();
        this.storage=StorageFactory.createStorage();
        LoadData();
    }
private  void LoadData(){
        this.books=storage.loadBooks();
        this.users=storage.loadUsers();
        this.students=storage.loadStudents();
        this.borrowRecords=storage.loadBorrowRecords();
}
public void saveData(){
        storage.saveBooks(books);
        storage.saveStudents(students);
        storage.saveUsers(users);
        storage.saveBorrowRecords(borrowRecords);
}
    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) ||
                    book.getAuthor().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> getTop10BorrowedBooks() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()));
        return sortedBooks;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getStudentId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void borrowBook(String userId, String bookId) {
        Book book = getBookById(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            book.incrementBorrowCount();

            String recordId = "BR-" + System.currentTimeMillis();
            borrowRecords.add(new BorrowRecord(recordId, bookId, userId, new Date()));

            Student student = getStudentById(userId);
            if (student != null) {
                student.borrowBook(bookId);
            }
        }
    }

    public void returnBook(String userId, String bookId) {
        Book book = getBookById(bookId);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);

            for (BorrowRecord record : borrowRecords) {
                if (record.getBookId().equals(bookId) && record.getUserId().equals(userId) &&
                        record.getReturnDate() == null) {
                    record.setReturnDate(new Date());
                    break;
                }
            }

            Student student = getStudentById(userId);
            if (student != null) {
                student.returnBook(bookId);
            }
        }
    }

    public List<BorrowRecord> getBorrowedBooksByUser(String userId) {
        List<BorrowRecord> userRecords = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (record.getUserId().equals(userId) && record.getReturnDate() == null) {
                userRecords.add(record);
            }
        }
        return userRecords;
    }

    public List<BorrowRecord> getAllBorrowedBooks() {
        List<BorrowRecord> activeRecords = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (record.getReturnDate() == null) {
                activeRecords.add(record);
            }
        }
        return activeRecords;
    }
}
