package ap.exercises.EX7;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SqliteStorage implements DataStorage {
    private static final String DB_FILE = "library.db";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Connection connection;

    public SqliteStorage() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id TEXT PRIMARY KEY, " +
                    "title TEXT, " +
                    "author TEXT, " +
                    "is_available INTEGER, " +
                    "borrow_count INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id TEXT PRIMARY KEY, " +
                    "username TEXT, " +
                    "password TEXT, " +
                    "role TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "student_id TEXT PRIMARY KEY, " +
                    "name TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS borrowed_books (" +
                    "student_id TEXT, " +
                    "book_id TEXT, " +
                    "PRIMARY KEY (student_id, book_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS borrow_records (" +
                    "id TEXT PRIMARY KEY, " +
                    "book_id TEXT, " +
                    "user_id TEXT, " +
                    "borrow_date TEXT, " +
                    "return_date TEXT)");
        }
    }

    @Override
    public void saveBooks(List<Book> books) {
        try {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM books");
            }

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO books(id, title, author, is_available, borrow_count) VALUES (?, ?, ?, ?, ?)")) {
                for (Book book : books) {
                    pstmt.setString(1, book.getId());
                    pstmt.setString(2, book.getTitle());
                    pstmt.setString(3, book.getAuthor());
                    pstmt.setInt(4, book.isAvailable() ? 1 : 0);
                    pstmt.setInt(5, book.getBorrowCount());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"));
                book.setAvailable(rs.getInt("is_available") == 1);
                for (int i = 0; i < rs.getInt("borrow_count"); i++) {
                    book.incrementBorrowCount();
                }
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void saveUsers(List<User> users) {
        try {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM users");
            }

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO users(id, username, password, role) VALUES (?, ?, ?, ?)")) {
                for (User user : users) {
                    pstmt.setString(1, user.getId());
                    pstmt.setString(2, user.getUsername());
                    pstmt.setString(3, user.getPassword());
                    pstmt.setString(4, user.getRole());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveStudents(List<Student> students) {
        try {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM students");
                stmt.execute("DELETE FROM borrowed_books");
            }

            try (PreparedStatement studentStmt = connection.prepareStatement(
                    "INSERT INTO students(student_id, name) VALUES (?, ?)");
                 PreparedStatement bookStmt = connection.prepareStatement(
                         "INSERT INTO borrowed_books(student_id, book_id) VALUES (?, ?)")) {

                for (Student student : students) {
                    studentStmt.setString(1, student.getStudentId());
                    studentStmt.setString(2, student.getName());
                    studentStmt.executeUpdate();

                    for (String bookId : student.getBorrowedBooks()) {
                        bookStmt.setString(1, student.getStudentId());
                        bookStmt.setString(2, bookId);
                        bookStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try {
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getString("student_id"),
                            rs.getString("name"));
                    students.add(student);
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT book_id FROM borrowed_books WHERE student_id = ?")) {
                for (Student student : students) {
                    pstmt.setString(1, student.getStudentId());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            student.borrowBook(rs.getString("book_id"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void saveBorrowRecords(List<BorrowRecord> records) {
        try {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM borrow_records");
            }

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO borrow_records(id, book_id, user_id, borrow_date, return_date) " +
                            "VALUES (?, ?, ?, ?, ?)")) {
                for (BorrowRecord record : records) {
                    pstmt.setString(1, record.getId());
                    pstmt.setString(2, record.getBookId());
                    pstmt.setString(3, record.getUserId());
                    pstmt.setString(4, dateFormat.format(record.getBorrowDate()));
                    pstmt.setString(5, record.getReturnDate() != null ?
                            dateFormat.format(record.getReturnDate()) : null);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BorrowRecord> loadBorrowRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM borrow_records")) {
            while (rs.next()) {
                try {
                    Date borrowDate = (Date) dateFormat.parse(rs.getString("borrow_date"));
                    Date returnDate = rs.getString("return_date") != null ?
                            (Date) dateFormat.parse(rs.getString("return_date")) : null;

                    BorrowRecord record = new BorrowRecord(
                            rs.getString("id"),
                            rs.getString("book_id"),
                            rs.getString("user_id"),
                            borrowDate);
                    record.setReturnDate(returnDate);
                    records.add(record);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}