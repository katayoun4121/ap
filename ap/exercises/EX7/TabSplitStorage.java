package ap.exercises.EX7;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabSplitStorage implements DataStorage {
    private static final String BOOKS_FILE = "books.tab";
    private static final String USERS_FILE = "users.tab";
    private static final String STUDENTS_FILE = "students.tab";
    private static final String BORROW_RECORDS_FILE = "borrow_records.tab";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.println(String.join("\t",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        String.valueOf(book.isAvailable()),
                        String.valueOf(book.getBorrowCount())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 5) {
                    Book book = new Book(parts[0], parts[1], parts[2]);
                    book.setAvailable(Boolean.parseBoolean(parts[3]));
                    for (int i = 0; i < Integer.parseInt(parts[4]); i++) {
                        book.incrementBorrowCount();
                    }
                    books.add(book);
                }
            }
        } catch (IOException e) {
        }
        return books;
    }

    @Override
    public void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(String.join("\t",
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
        }
        return users;
    }

    @Override
    public void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.println(String.join("\t",
                        student.getStudentId(),
                        student.getName(),
                        String.join(",", student.getBorrowedBooks())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    Student student = new Student(parts[0], parts[1]);
                    for (String bookId : parts[2].split(",")) {
                        if (!bookId.isEmpty()) {
                            student.borrowBook(bookId);
                        }
                    }
                    students.add(student);
                }
            }
        } catch (IOException e) {
        }
        return students;
    }

    @Override
    public void saveBorrowRecords(List<BorrowRecord> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BORROW_RECORDS_FILE))) {
            for (BorrowRecord record : records) {
                writer.println(String.join("\t",
                        record.getId(),
                        record.getBookId(),
                        record.getUserId(),
                        dateFormat.format(record.getBorrowDate()),
                        record.getReturnDate() != null ? dateFormat.format(record.getReturnDate()) : "null"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BorrowRecord> loadBorrowRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BORROW_RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 5) {
                    try {
                        Date borrowDate = dateFormat.parse(parts[3]);
                        Date returnDate = parts[4].equals("null") ? null : dateFormat.parse(parts[4]);
                        BorrowRecord record = new BorrowRecord(parts[0], parts[1], parts[2], borrowDate);
                        record.setReturnDate(returnDate);
                        records.add(record);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
        }
        return records;
    }
}
