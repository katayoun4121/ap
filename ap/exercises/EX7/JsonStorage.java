package ap.exercises.EX7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage implements DataStorage {
    private static final String BOOKS_FILE = "books.json";
    private static final String USERS_FILE = "users.json";
    private static final String STUDENTS_FILE = "students.json";
    private static final String BORROW_RECORDS_FILE = "borrow_records.json";
    private final Gson gson = new Gson();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void saveBooks(List<Book> books) {
        try (FileWriter writer = new FileWriter(BOOKS_FILE)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> loadBooks() {
        try (FileReader reader = new FileReader(BOOKS_FILE)) {
            Type bookListType = new TypeToken<ArrayList<Book>>(){}.getType();
            return gson.fromJson(reader, bookListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveStudents(List<Student> students) {
        try (FileWriter writer = new FileWriter(STUDENTS_FILE)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> loadStudents() {
        try (FileReader reader = new FileReader(STUDENTS_FILE)) {
            Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveBorrowRecords(List<BorrowRecord> records) {
        try (FileWriter writer = new FileWriter(BORROW_RECORDS_FILE)) {
            gson.toJson(records, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BorrowRecord> loadBorrowRecords() {
        try (FileReader reader = new FileReader(BORROW_RECORDS_FILE)) {
            Type recordListType = new TypeToken<ArrayList<BorrowRecord>>(){}.getType();
            return gson.fromJson(reader, recordListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
