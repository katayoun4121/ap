package ap.projects.finalproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private StudentManager studentManager;
    private BookManager bookManager;
    private BorrowManager borrowManager;
    private EmployeeManager employeeManager;
    private StatisticsManager statisticsManager;
    private MenuHandler menuHandler;
    private Employee currentEmployee;

    public LibrarySystem() {
        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.borrowManager = new BorrowManager();
        this.employeeManager = new EmployeeManager();
        this.statisticsManager = new StatisticsManager(studentManager, bookManager, borrowManager);
        this.statisticsManager.setEmployeeManager(employeeManager);
        this.menuHandler = new MenuHandler(this);
        this.currentEmployee = null;
    }

    // اضافه کردن متدهای Getter
    public StudentManager getStudentManager() { return studentManager; }
    public BookManager getBookManager() { return bookManager; }
    public BorrowManager getBorrowManager() { return borrowManager; }
    public EmployeeManager getEmployeeManager() { return employeeManager; }
    public StatisticsManager getStatisticsManager() { return statisticsManager; }

    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }

    public int getEmployeeCount() {
        return this.employeeManager.getEmployeeCount();
    }

    public void displayStudentCount() {
        int count = getStudentCount();
        System.out.println("\n--- Registered Students Count ---");
        System.out.println("Total registered students: " + count);
    }

    public void displayStatistics() {
        statisticsManager.displayStatistics();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
    }

    public Student authenticateStudent(String username, String password) {
        return studentManager.authenticateStudent(username, password);
    }

    public Employee authenticateEmployee(String username, String password) {
        return employeeManager.authenticateEmployee(username, password);
    }

    public void registerEmployee(String name, String employeeId, String username, String password, String role) {
        if (currentEmployee != null && currentEmployee.isManager()) {
            employeeManager.registerEmployee(name, employeeId, username, password, role);
        } else {
            System.out.println("Only managers can register new employees.");
        }
    }

    public void displayAllEmployees() {
        if (currentEmployee != null && currentEmployee.isManager()) {
            employeeManager.displayAllEmployees();
        } else {
            System.out.println("Only managers can view employee list.");
        }
    }

    public void editStudentInformation(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Edit Student Information ---");

        System.out.print("New name (current: " + student.getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            System.out.println("Name change functionality not fully implemented.");
        }

        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            System.out.println("Password change functionality not fully implemented.");
        }

        System.out.println("Information updated successfully.");
    }

    public void borrowBook(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Borrow a Book ---");

        bookManager.displayAvailableBooks();

        System.out.print("Enter ISBN of the book you want to borrow: ");
        String isbn = scanner.nextLine();

        Book book = bookManager.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Invalid ISBN.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is not available.");
            return;
        }

        System.out.print("Enter number of days to borrow (1-30): ");
        int borrowDays = scanner.nextInt();
        scanner.nextLine();

        if (borrowDays < 1 || borrowDays > 30) {
            System.out.println("Invalid number of days. Must be between 1 and 30.");
            return;
        }

        if (bookManager.borrowBook(isbn)) {
            borrowManager.borrowBook(student.getUsername(), isbn, borrowDays);

            LocalDate dueDate = LocalDate.now().plusDays(borrowDays);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.println("Book borrowed successfully!");
            System.out.println("Due date: " + dueDate.format(formatter));
        } else {
            System.out.println("Failed to borrow book.");
        }
    }

    public void returnBook(Student student) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Return a Book ---");

        List<BorrowRecord> activeBorrows = borrowManager.getActiveBorrows(student.getUsername());
        if (activeBorrows.isEmpty()) {
            System.out.println("You have no active borrows.");
            return;
        }

        System.out.println("\nYour Active Borrows:");
        activeBorrows.forEach(System.out::println);

        System.out.print("Enter ISBN of the book you want to return: ");
        String isbn = scanner.nextLine();

        if (borrowManager.returnBook(student.getUsername(), isbn)) {
            bookManager.returnBook(isbn);
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Invalid ISBN or you don't have this book borrowed.");
        }
    }

    public void displayAvailableBooks() {
        bookManager.displayAvailableBooks();
    }

    public void viewMyBorrows(Student student) {
        System.out.println("\n--- My Borrow History ---");
        List<BorrowRecord> borrows = borrowManager.getStudentBorrows(student.getUsername());

        if (borrows.isEmpty()) {
            System.out.println("You have no borrow history.");
            return;
        }

        borrows.forEach(System.out::println);
    }

    public void searchBooksByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Search Books by Title ---");

        System.out.print("Enter book title to search: ");
        String title = scanner.nextLine();

        if (title.isEmpty()) {
            System.out.println("Please enter a title to search.");
            return;
        }

        List<Book> results = bookManager.searchBooksByTitle(title);
        bookManager.displaySearchResults(results);
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        system.start();
    }

    public Employee getCurrentEmployee() { return currentEmployee; }
    public void setCurrentEmployee(Employee employee) { this.currentEmployee = employee; }
}