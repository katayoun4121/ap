package ap.projects.finalproject;

import java.util.List;

public class StudentBorrowReport {
    private String studentUsername;
    private List<BorrowRecord> borrowHistory;
    private int totalBorrows;
    private int activeBorrows;
    private int overdueBorrows;
    private int returnedBorrows;

    public StudentBorrowReport(String studentUsername, List<BorrowRecord> borrowHistory) {
        this.studentUsername = studentUsername;
        this.borrowHistory = borrowHistory;
        this.totalBorrows = borrowHistory.size();
        this.activeBorrows = (int) borrowHistory.stream()
                .filter(record -> !record.isReturned())
                .count();
        this.overdueBorrows = (int) borrowHistory.stream()
                .filter(BorrowRecord::isOverdue)
                .count();
        this.returnedBorrows = (int) borrowHistory.stream()
                .filter(BorrowRecord::isReturned)
                .count();
    }

    public String getStudentUsername() { return studentUsername; }
    public List<BorrowRecord> getBorrowHistory() { return borrowHistory; }
    public int getTotalBorrows() { return totalBorrows; }
    public int getActiveBorrows() { return activeBorrows; }
    public int getOverdueBorrows() { return overdueBorrows; }
    public int getReturnedBorrows() { return returnedBorrows; }

    public void displayReport() {
        System.out.println("\n=== Student Borrow Report ===");
        System.out.println("Student Username: " + studentUsername);
        System.out.println("Total Borrows: " + totalBorrows);
        System.out.println("Active Borrows: " + activeBorrows);
        System.out.println("Overdue Borrows: " + overdueBorrows);
        System.out.println("Returned Borrows: " + returnedBorrows);

        System.out.println("\n--- Borrow History ---");
        if (borrowHistory.isEmpty()) {
            System.out.println("No borrow history found.");
        } else {
            borrowHistory.forEach(System.out::println);
        }

        System.out.println("\n--- Statistics ---");
        double returnRate = totalBorrows > 0 ? (returnedBorrows * 100.0 / totalBorrows) : 0;
        System.out.printf("Return Rate: %.1f%%\n", returnRate);

        if (overdueBorrows > 0) {
            System.out.println("⚠️  This student has " + overdueBorrows + " overdue book(s)!");
        }
    }
}