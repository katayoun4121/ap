package ap.projects.finalproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibrarySystem {
    private StudentManager studentManager;
    private BookManager bookManager;
    private BorrowManager borrowManager;
    private BorrowRequestManager borrowRequestManager;
    private EmployeeManager employeeManager;
    private StatisticsManager statisticsManager;
    private MenuHandler menuHandler;
    private Employee currentEmployee;

    public LibrarySystem() {
        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.borrowManager = new BorrowManager();
        this.borrowRequestManager = new BorrowRequestManager();
        this.employeeManager = new EmployeeManager();
        this.statisticsManager = new StatisticsManager(studentManager, bookManager, borrowManager);
        this.statisticsManager.setEmployeeManager(employeeManager);
        this.menuHandler = new MenuHandler(this);
        this.currentEmployee = null;
    }
    public void toggleStudentStatus() {
        if (currentEmployee == null) {
            System.out.println("Only employees can manage student status.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Manage Student Status ---");

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = studentManager.findStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (studentManager.toggleStudentStatus(username)) {
            String newStatus = studentManager.findStudentByUsername(username).isActive() ? "activated" : "deactivated";
            System.out.println("Student status updated successfully. Student is now " + newStatus + ".");
        } else {
            System.out.println("Failed to update student status.");
        }
    }
    public void deactivateStudent() {
        if (currentEmployee == null) {
            System.out.println("Only employees can deactivate students.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Deactivate Student ---");

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = studentManager.findStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (!student.isActive()) {
            System.out.println("Student is already inactive.");
            return;
        }

        if (studentManager.deactivateStudent(username)) {
            System.out.println("Student deactivated successfully.");
        } else {
            System.out.println("Failed to deactivate student.");
        }
    }
    public void activateStudent() {
        if (currentEmployee == null) {
            System.out.println("Only employees can activate students.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Activate Student ---");

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = studentManager.findStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }


        if (student.isActive()) {
            System.out.println("Student is already active.");
            return;
        }
        if (studentManager.activateStudent(username)) {
            System.out.println("Student activated successfully.");
        } else {
            System.out.println("Failed to activate student.");
        }
    }

    public void displayInactiveStudents() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view inactive students.");
            return;
        }

        studentManager.displayInactiveStudents();
    }
    public void displayActiveStudents() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view active students.");
            return;
        }

        studentManager.displayActiveStudents();
    }
    public void createBorrowRequest(Student student, String bookIsbn, LocalDate desiredDate) {
        if (!student.isActive()) {
            System.out.println("Your account is inactive. You cannot borrow books.");
            return;
        }

        Book book = bookManager.findBookByIsbn(bookIsbn);
        if (book == null) {
            System.out.println("Invalid ISBN.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is not available.");
            return;
        }

        borrowRequestManager.createBorrowRequest(student.getUsername(), bookIsbn, desiredDate);
    }
    public void pickupBook(Student student) {
        System.out.println("\n--- Pickup Book ---");
        borrowManager.displayStudentReadyForPickupBooks(student.getUsername());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN of the book you want to pickup: ");
        String isbn = scanner.nextLine();

        if (borrowManager.markBookAsPickedUp(student.getUsername(), isbn)) {
            System.out.println("Book picked up successfully! The due date starts from today.");

            BorrowRecord record = borrowManager.findActiveBorrow(student.getUsername(), isbn);
            if (record != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                System.out.println("Due date: " + record.getDueDate().format(formatter));
            }
        } else {
            System.out.println("Failed to pickup book. Book not found or not ready for pickup.");
        }
    }

    public void displayReadyForPickupBooks() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view this information.");
            return;
        }

        borrowManager.displayReadyForPickupBooks();
    }
    public void markBookAsPickedUpByEmployee() {
        if (currentEmployee == null) {
            System.out.println("Only employees can perform this action.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Mark Book as Picked Up ---");

        borrowManager.displayReadyForPickupBooks();

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        if (borrowManager.markBookAsPickedUp(username, isbn)) {
            System.out.println("Book marked as picked up successfully!");
        }else {
            System.out.println("Failed to mark book as picked up. Record not found or not ready for pickup.");
        }
    }


    public void viewStudentBorrowReport() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view student reports.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Student Borrow Report ===");

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = studentManager.authenticateStudent(username, "");
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        StudentBorrowReport report = borrowManager.generateStudentReport(username);
        report.displayReport();
    }

    public void viewBorrowStatistics() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view borrow statistics.");
            return;
        }

        borrowManager.displayBorrowStatistics();
    }

    public void viewStudentsWithOverdueBorrows() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view this information.");
            return;
        }

        List<String> students = borrowManager.getStudentsWithOverdueBorrows();

        System.out.println("\n=== Students with Overdue Books ===");
        if (students.isEmpty()) {
            System.out.println("No students have overdue books.");
            return;
        }

        System.out.println("Total students with overdue books: " + students.size());
        System.out.println("\nList of students:");
        students.forEach(student -> {
            List<BorrowRecord> overdueBooks = borrowManager.getStudentBorrows(student).stream()
                    .filter(BorrowRecord::isOverdue)
                    .collect(Collectors.toList());

            System.out.println("Student: " + student + " - Overdue books: " + overdueBooks.size());
            overdueBooks.forEach(record ->
                    System.out.println("  - " + record.getBookIsbn() + " (Due: " + record.getDueDate() + ")")
            );
        });
    }

    public StudentManager getStudentManager() { return studentManager; }
    public BookManager getBookManager() { return bookManager; }
    public BorrowManager getBorrowManager() { return borrowManager; }
    public BorrowRequestManager getBorrowRequestManager() { return borrowRequestManager; }
    public EmployeeManager getEmployeeManager() { return employeeManager; }
    public StatisticsManager getStatisticsManager() { return statisticsManager; }

    public void displayPendingRequests() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view pending requests.");
            return;
        }
        borrowRequestManager.displayPendingRequests();
    }

    public void displayPendingRequestsForToday() {
        if (currentEmployee == null) {
            System.out.println("Only employees can view pending requests.");
            return;
        }
        borrowRequestManager.displayPendingRequestsForToday();
    }

    public void approveBorrowRequest() {
        if (currentEmployee == null) {
            System.out.println("Only employees can approve requests.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        borrowRequestManager.displayPendingRequestsForToday();

        System.out.print("\nEnter student username to approve: ");
        String username = scanner.nextLine();

        System.out.print("Enter book ISBN to approve: ");
        String isbn = scanner.nextLine();

        if (borrowRequestManager.approveRequest(username, isbn)) {
            System.out.println("Borrow request approved successfully!");
            bookManager.borrowBook(isbn);
            borrowManager.borrowBook(username, isbn, 14);
            System.out.println("Book has been marked as borrowed. Student can now pick up the book.");
        } else {
            System.out.println("Failed to approve request. Request not found or already processed.");
        }
    }

    public void rejectBorrowRequest() {
        if (currentEmployee == null) {
            System.out.println("Only employees can reject requests.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        borrowRequestManager.displayPendingRequestsForToday();

        System.out.print("\nEnter student username to reject: ");
        String username = scanner.nextLine();

        System.out.print("Enter book ISBN to reject: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter rejection reason: ");
        String reason = scanner.nextLine();

        if (borrowRequestManager.rejectRequest(username, isbn, reason)) {
            System.out.println("Borrow request rejected successfully!");
        } else {
            System.out.println("Failed to reject request. Request not found or already processed.");
        }
    }

    public void viewMyBorrowRequests(Student student) {
        List<BorrowRequest> requests = borrowRequestManager.getStudentRequests(student.getUsername());

        System.out.println("\n--- My Borrow Requests ---");
        if (requests.isEmpty()) {
            System.out.println("You have no borrow requests.");
            return;
        }

        requests.forEach(System.out::println);
    }

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

    public void searchBooksAdvanced() {
        if (currentEmployee == null) {
            System.out.println("Only employees can use advanced search.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        bookManager.searchBooksFromInput(scanner);
    }

    public void editBook() {
        if (currentEmployee == null) {
            System.out.println("Only employees can edit books.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Edit Book Information ---");

        System.out.print("Enter ISBN of the book to edit: ");
        String isbn = scanner.nextLine();

        bookManager.editBookFromInput(scanner, isbn);
    }

    public void addNewBook() {
        if (currentEmployee == null) {
            System.out.println("Only employees can add books.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Book newBook = bookManager.createBookFromInput(scanner);

        if (newBook != null) {
            bookManager.addNewBook(newBook);
        }
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

    public boolean changeEmployeePassword(String username, String currentPassword, String newPassword) {
        return employeeManager.changePassword(username, currentPassword, newPassword);
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

        System.out.print("Enter desired borrow date (YYYY-MM-DD) or press Enter for today: ");
        String dateInput = scanner.nextLine();
        LocalDate desiredDate = LocalDate.now();

        if (!dateInput.isEmpty()) {
            try {
                desiredDate = LocalDate.parse(dateInput);
                if (desiredDate.isBefore(LocalDate.now())) {
                    System.out.println("Cannot request a date in the past.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                return;
            }
        }

        createBorrowRequest(student, isbn, desiredDate);
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