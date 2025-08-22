package ap.projects.finalproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowManager {
    private List<BorrowRecord> borrowRecords;
    private BorrowFileManager fileManager;

    public BorrowManager() {
        this.fileManager = new BorrowFileManager();
        this.borrowRecords = fileManager.loadBorrowRecords();
        if (this.borrowRecords == null) {
            this.borrowRecords = new ArrayList<>();
        }
    }

    public List<BorrowRecord> getAllBorrows() {
        return new ArrayList<>(borrowRecords);
    }

    public int getTotalBorrowsCount() {
        return borrowRecords.size();
    }

    public int getActiveBorrowsCount() {
        return (int) borrowRecords.stream()
                .filter(record -> !record.isReturned())
                .count();
    }

    public int getOverdueBorrowsCount() {
        return (int) borrowRecords.stream()
                .filter(BorrowRecord::isOverdue)
                .count();
    }

    public int getReturnedBorrowsCount() {
        return (int) borrowRecords.stream()
                .filter(BorrowRecord::isReturned)
                .count();
    }

    public boolean borrowBook(String studentUsername, String bookIsbn, int borrowDays) {
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(borrowDays);

        BorrowRecord record = new BorrowRecord(studentUsername, bookIsbn, borrowDate, dueDate);
        borrowRecords.add(record);
        saveRecords();
        return true;
    }

    public boolean returnBook(String studentUsername, String bookIsbn) {
        BorrowRecord record = findActiveBorrow(studentUsername, bookIsbn);
        if (record != null) {
            record.markAsReturned();
            saveRecords();
            return true;
        }
        return false;
    }

    public BorrowRecord findActiveBorrow(String studentUsername, String bookIsbn) {
        return borrowRecords.stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername) &&
                        record.getBookIsbn().equals(bookIsbn) &&
                        !record.isReturned())
                .findFirst()
                .orElse(null);
    }

    public List<BorrowRecord> getStudentBorrows(String studentUsername) {
        return borrowRecords.stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername))
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getActiveBorrows(String studentUsername) {
        return borrowRecords.stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername) &&
                        !record.isReturned())
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getActiveBorrows() {
        return borrowRecords.stream()
                .filter(record -> !record.isReturned())
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getOverdueBorrows() {
        return borrowRecords.stream()
                .filter(BorrowRecord::isOverdue)
                .collect(Collectors.toList());
    }

    private void saveRecords() {
        fileManager.saveBorrowRecords(borrowRecords);
    }
}