package ap.projects.finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManager {
    private List<Student> students;
    private StudentFileManager fileManager;

    public StudentManager() {
        this.fileManager = new StudentFileManager();
        this.students = fileManager.loadStudents();

        if (this.students == null) {
            this.students = new ArrayList<>();
        }
    }

    public StudentStatistics generateStudentStatistics(List<BorrowRecord> borrowRecords) {
        return new StudentStatistics(students, borrowRecords);
    }

    public List<String> getStudentsWithMostOverdue(int limit) {
        return students.stream()
                .filter(student -> student.isActive())
                .map(student -> {
                    long overdueCount = getOverdueBorrowCount(student.getUsername());
                    return new StudentOverdueInfo(student, overdueCount);
                })
                .filter(info -> info.getOverdueCount() > 0)
                .sorted((i1, i2) -> Long.compare(i2.getOverdueCount(), i1.getOverdueCount()))
                .limit(limit)
                .map(StudentOverdueInfo::toString)
                .collect(Collectors.toList());
    }

    private long getOverdueBorrowCount(String username) {
        return (long) (Math.random() * 10);
    }

    public void displayStudentsWithMostOverdue(int limit, List<BorrowRecord> borrowRecords) {
        List<String> topOverdueStudents = students.stream()
                .filter(Student::isActive)
                .map(student -> {
                    long overdueCount = borrowRecords.stream()
                            .filter(record -> record.getStudentUsername().equals(student.getUsername()) &&
                                    record.isOverdue())
                            .count();
                    return new StudentOverdueInfo(student, overdueCount);
                })
                .filter(info -> info.getOverdueCount() > 0)
                .sorted((i1, i2) -> Long.compare(i2.getOverdueCount(), i1.getOverdueCount()))
                .limit(limit)
                .map(StudentOverdueInfo::toString)
                .collect(Collectors.toList());

        System.out.println("\n=== Top " + limit + " Students with Most Overdue Books ===");
        if (topOverdueStudents.isEmpty()) {
            System.out.println("No students with overdue books.");
            return;
        }

        for (int i = 0; i < topOverdueStudents.size(); i++) {
            System.out.println((i + 1) + ". " + topOverdueStudents.get(i));
        }
    }

    private static class StudentOverdueInfo {
        private Student student;
        private long overdueCount;

        public StudentOverdueInfo(Student student, long overdueCount) {
            this.student = student;
            this.overdueCount = overdueCount;
        }

        public long getOverdueCount() {
            return overdueCount;
        }

        @Override
        public String toString() {
            return student.getName() + " (" + student.getUsername() + ") - " +
                    overdueCount + " overdue books";
        }
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        if (isUsernameTaken(username)) {
            System.out.println("This username already exists. Please choose a different username.");
            return;
        }

        Student newStudent = new Student(name, studentId, username, password);
        students.add(newStudent);
        fileManager.saveStudents(students);
        System.out.println("Student registration completed successfully.");
    }

    public Student authenticateStudent(String username, String password) {
        Student student = students.stream()
                .filter(s -> s.getUsername().equals(username) && s.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (student != null && !student.isActive()) {
            System.out.println("Your account is inactive. Please contact the library administrator.");
            return null;
        }

        return student;
    }

    public boolean toggleStudentStatus(String username) {
        Student student = findStudentByUsername(username);
        if (student != null) {
            student.setActive(!student.isActive());
            fileManager.saveStudents(students);
            return true;
        }
        return false;
    }

    public boolean deactivateStudent(String username) {
        Student student = findStudentByUsername(username);
        if (student != null) {
            student.setActive(false);
            fileManager.saveStudents(students);
            return true;
        }
        return false;
    }

    public boolean activateStudent(String username) {
        Student student = findStudentByUsername(username);
        if (student != null) {
            student.setActive(true);
            fileManager.saveStudents(students);
            return true;
        }
        return false;
    }

    public Student findStudentByUsername(String username) {
        return students.stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void displayInactiveStudents() {
        List<Student> inactiveStudents = students.stream()
                .filter(student -> !student.isActive())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println("\n--- Inactive Students ---");
        if (inactiveStudents.isEmpty()) {
            System.out.println("No inactive students.");
            return;
        }

        for (Student student : inactiveStudents) {
            System.out.println(student);
        }
    }

    public void displayActiveStudents() {
        List<Student> activeStudents = students.stream()
                .filter(Student::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println("\n--- Active Students ---");
        if (activeStudents.isEmpty()) {
            System.out.println("No active students.");
            return;
        }

        for (Student student : activeStudents) {
            System.out.println(student);
        }
    }

    public void displayStudents() {
        System.out.println("\n--- List of Registered Students ---");

        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private boolean isUsernameTaken(String username) {
        return students.stream().anyMatch(s -> s.getUsername().equals(username));
    }

    public int getStudentCount() {
        return students.size();
    }

    public int getActiveStudentCount() {
        return (int) students.stream()
                .filter(Student::isActive)
                .count();
    }

    public int getInactiveStudentCount() {
        return (int) students.stream()
                .filter(student -> !student.isActive())
                .count();
    }
}