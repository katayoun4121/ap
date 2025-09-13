package ap.projects.finalproject;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StudentStatistics implements Serializable {
    private static final long serialVersionUID = 6L;
    private int totalStudents;
    private int activeStudents;
    private int inactiveStudents;
    private int studentsWithBorrows;
    private int studentsWithOverdue;
    private double averageBorrowsPerStudent;

    public StudentStatistics(List<Student> students, List<BorrowRecord> borrowRecords) {
        calculateStatistics(students, borrowRecords);
    }

    private void calculateStatistics(List<Student> students, List<BorrowRecord> borrowRecords) {
        this.totalStudents = students.size();
        this.activeStudents = (int) students.stream().filter(Student::isActive).count();
        this.inactiveStudents = totalStudents - activeStudents;

        this.studentsWithBorrows = (int) students.stream()
                .filter(student -> hasBorrows(student.getUsername(), borrowRecords))
                .count();

        this.studentsWithOverdue = (int) students.stream()
                .filter(student -> hasOverdueBorrows(student.getUsername(), borrowRecords))
                .count();

        int totalBorrows = borrowRecords.size();
        this.averageBorrowsPerStudent = studentsWithBorrows > 0 ?
                (double) totalBorrows / studentsWithBorrows : 0;
    }

    private boolean hasBorrows(String username, List<BorrowRecord> borrowRecords) {
        return borrowRecords.stream()
                .anyMatch(record -> record.getStudentUsername().equals(username));
    }

    private boolean hasOverdueBorrows(String username, List<BorrowRecord> borrowRecords) {
        return borrowRecords.stream()
                .anyMatch(record -> record.getStudentUsername().equals(username) &&
                        record.isOverdue());
    }

    public int getTotalStudents() { return totalStudents; }
    public int getActiveStudents() { return activeStudents; }
    public int getInactiveStudents() { return inactiveStudents; }
    public int getStudentsWithBorrows() { return studentsWithBorrows; }
    public int getStudentsWithOverdue() { return studentsWithOverdue; }
    public double getAverageBorrowsPerStudent() { return averageBorrowsPerStudent; }

    public void displayStatistics() {
        System.out.println("\n=== Student Statistics ===");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Active Students: " + activeStudents);
        System.out.println("Inactive Students: " + inactiveStudents);
        System.out.println("Students with Borrows: " + studentsWithBorrows);
        System.out.println("Students with Overdue Books: " + studentsWithOverdue);
        System.out.printf("Average Borrows per Student: %.2f\n", averageBorrowsPerStudent);

        if (totalStudents > 0) {
            double activePercentage = (activeStudents * 100.0) / totalStudents;
            double borrowPercentage = (studentsWithBorrows * 100.0) / totalStudents;
            double overduePercentage = (studentsWithOverdue * 100.0) / totalStudents;

            System.out.printf("Active Students: %.1f%%\n", activePercentage);
            System.out.printf("Students with Borrows: %.1f%%\n", borrowPercentage);
            System.out.printf("Students with Overdue: %.1f%%\n", overduePercentage);
        }
    }
}