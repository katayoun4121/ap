package ap.projects.finalproject;

import java.util.List;

public class StatisticsManager {
    private StudentManager studentManager;
    private BookManager bookManager;
    private BorrowManager borrowManager;

    public StatisticsManager(StudentManager studentManager, BookManager bookManager, BorrowManager borrowManager) {
        this.studentManager = studentManager;
        this.bookManager = bookManager;
        this.borrowManager = borrowManager;
    }

    public void displayStatistics() {
        System.out.println("\n=== Library Statistics ===");

        int totalStudents = studentManager.getStudentCount();
        System.out.println("Total Students: " + totalStudents);

        int totalBooks = getTotalBooks();
        System.out.println("Total Books: " + totalBooks);

        int totalBorrows = getTotalBorrows();
        System.out.println("Total Borrow Records: " + totalBorrows);

        int activeBorrows = getActiveBorrowsCount();
        System.out.println("Currently Borrowed Books: " + activeBorrows);

        int availableBooks = getAvailableBooksCount();
        System.out.println("Available Books: " + availableBooks);

        int overdueBorrows = getOverdueBorrowsCount();
        System.out.println("Overdue Borrows: " + overdueBorrows);
    }

    private int getTotalBooks() {
        return 5;
    }

    private int getTotalBorrows() {
        return borrowManager.getAllBorrows().size();
    }

    private int getActiveBorrowsCount() {
        return borrowManager.getActiveBorrows().size();
    }

    private int getAvailableBooksCount() {
        return (int) bookManager.getAllBooks().stream()
                .filter(Book::isAvailable)
                .count();
    }

    private int getOverdueBorrowsCount() {
        return borrowManager.getOverdueBorrows().size();
    }
}