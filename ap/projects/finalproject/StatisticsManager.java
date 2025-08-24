package ap.projects.finalproject;

import java.util.List;

public class StatisticsManager {
    private StudentManager studentManager;
    private BookManager bookManager;
    private BorrowManager borrowManager;
    private EmployeeManager employeeManager;

    public StatisticsManager(StudentManager studentManager, BookManager bookManager, BorrowManager borrowManager) {
        this.studentManager = studentManager;
        this.bookManager = bookManager;
        this.borrowManager = borrowManager;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void displayStatistics() {
        System.out.println("\n=== Library Statistics ===");

        int totalStudents = studentManager.getStudentCount();
        System.out.println("Total Students: " + totalStudents);

        if (employeeManager != null) {
            int totalEmployees = employeeManager.getEmployeeCount();
            System.out.println("Total Employees: " + totalEmployees);
        }

        int totalBooks = bookManager.getTotalBooksCount();
        System.out.println("Total Books: " + totalBooks);

        int totalBorrows = borrowManager.getTotalBorrowsCount();
        System.out.println("Total Borrow Records: " + totalBorrows);

        int activeBorrows = borrowManager.getActiveBorrowsCount();
        System.out.println("Currently Borrowed Books: " + activeBorrows);

        int availableBooks = bookManager.getAvailableBooksCount();
        System.out.println("Available Books: " + availableBooks);

        int overdueBorrows = borrowManager.getOverdueBorrowsCount();
        System.out.println("Overdue Borrows: " + overdueBorrows);

        double availabilityRate = totalBooks > 0 ? (availableBooks * 100.0 / totalBooks) : 0;
        System.out.printf("Availability Rate: %.1f%%\n", availabilityRate);
    }
}