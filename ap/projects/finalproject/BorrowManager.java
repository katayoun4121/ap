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

    public boolean markBookAsPickedUp(String studentUsername, String bookIsbn) {
        BorrowRecord record = findReadyForPickupRecord(studentUsername, bookIsbn);
        if (record != null) {
            record.markAsPickedUp();
            saveRecords();
            return true;
        }
        return false;
    }

    public BorrowRecord findReadyForPickupRecord(String studentUsername, String bookIsbn) {
        return borrowRecords.stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername) &&
                        record.getBookIsbn().equals(bookIsbn) &&
                        record.isReadyForPickup())
                .findFirst()
                .orElse(null);
    }

    public List<BorrowRecord> getReadyForPickupRecords(String studentUsername) {
        return borrowRecords.stream()
                .filter(record -> record.getStudentUsername().equals(studentUsername) &&
                        record.isReadyForPickup())
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getAllReadyForPickupRecords() {
        return borrowRecords.stream()
                .filter(BorrowRecord::isReadyForPickup)
                .collect(Collectors.toList());
    }

    public void displayReadyForPickupBooks() {
        List<BorrowRecord> readyRecords = getAllReadyForPickupRecords();

        System.out.println("\n--- Books Ready for Pickup ---");
        if (readyRecords.isEmpty()) {
            System.out.println("No books ready for pickup.");
            return;
        }

        readyRecords.forEach(System.out::println);
    }

    public void displayStudentReadyForPickupBooks(String studentUsername) {
        List<BorrowRecord> readyRecords = getReadyForPickupRecords(studentUsername);

        System.out.println("\n--- Your Books Ready for Pickup ---");
        if (readyRecords.isEmpty()) {
            System.out.println("You have no books ready for pickup.");
            return;
        }

        readyRecords.forEach(System.out::println);
    }

    public StudentBorrowReport generateStudentReport(String studentUsername) {
        List<BorrowRecord> studentBorrows = getStudentBorrows(studentUsername);
        return new StudentBorrowReport(studentUsername, studentBorrows);
    }

    public List<String> getStudentsWithOverdueBorrows() {
        return borrowRecords.stream()
                .filter(BorrowRecord::isOverdue)
                .map(BorrowRecord::getStudentUsername)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getStudentsWithActiveBorrows() {
        return borrowRecords.stream()
                .filter(record -> !record.isReturned())
                .map(BorrowRecord::getStudentUsername)
                .distinct()
                .collect(Collectors.toList());
    }

    public void displayBorrowStatistics() {
        int totalBorrows = getTotalBorrowsCount();
        int activeBorrows = getActiveBorrowsCount();
        int overdueBorrows = getOverdueBorrowsCount();
        int returnedBorrows = getReturnedBorrowsCount();
        int readyForPickup = getReadyForPickupCount();

        System.out.println("\n=== Library Borrow Statistics ===");
        System.out.println("Total Borrow Records: " + totalBorrows);
        System.out.println("Ready for Pickup: " + readyForPickup);
        System.out.println("Active Borrows: " + activeBorrows);
        System.out.println("Overdue Borrows: " + overdueBorrows);
        System.out.println("Returned Borrows: " + returnedBorrows);

        double returnRate = totalBorrows > 0 ? (returnedBorrows * 100.0 / totalBorrows) : 0;
        System.out.printf("Overall Return Rate: %.1f%%\n", returnRate);

        List<String> studentsWithOverdue = getStudentsWithOverdueBorrows();
        if (!studentsWithOverdue.isEmpty()) {
            System.out.println("\nStudents with overdue books: " + studentsWithOverdue.size());
        }
    }

    public int getReadyForPickupCount() {
        return (int) borrowRecords.stream()
                .filter(BorrowRecord::isReadyForPickup)
                .count();
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
        if (record != null && record.isPickedUp()) {
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