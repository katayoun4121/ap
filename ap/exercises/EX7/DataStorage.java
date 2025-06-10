package ap.exercises.EX7;

import java.util.List;

public interface DataStorage {
    void saveBooks(List<Book> books);
    List<Book> loadBooks();

    void saveUsers(List<User> users);
    List<User> loadUsers();

    void saveStudents(List<Student> students);
    List<Student> loadStudents();

    void saveBorrowRecords(List<BorrowRecord> records);
    List<BorrowRecord> loadBorrowRecords();
}
