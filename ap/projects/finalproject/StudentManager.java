package ap.projects.finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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