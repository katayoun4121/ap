package ap.projects.finalproject;

import java.util.Scanner;

public class MenuHandler {
    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Student currentUser;
    private Employee currentEmployee;

    public MenuHandler(LibrarySystem librarySystem) {
        this.scanner = new Scanner(System.in);
        this.librarySystem = librarySystem;
        this.currentUser = null;
        this.currentEmployee = null;
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== University Library Management System ===");
            System.out.println("1. Student Login");
            System.out.println("2. Employee Login");
            System.out.println("3. View Statistics");
            System.out.println("4. Search Books by Title");
            System.out.println("5. View Available Books");
            System.out.println("6. Exit");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    handleStudentLogin();
                    break;
                case 2:
                    handleEmployeeLogin();
                    break;
                case 3:
                    librarySystem.displayStatistics();
                    break;
                case 4:
                    librarySystem.searchBooksByTitle();
                    break;
                case 5:
                    librarySystem.displayAvailableBooks();
                    break;
                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
            System.out.println("___________________________");
        }
    }

    private void handleStudentLogin() {
        System.out.println("\n--- Student Login ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = librarySystem.authenticateStudent(username, password);

        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            displayLoggedInStudentMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleEmployeeLogin() {
        System.out.println("\n--- Employee Login ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentEmployee = librarySystem.authenticateEmployee(username, password);

        if (currentEmployee != null) {
            System.out.println("Login successful! Welcome, " + currentEmployee.getName());
            librarySystem.setCurrentEmployee(currentEmployee);
            displayLoggedInEmployeeMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void displayLoggedInStudentMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Student Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Edit My Information");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. View My Borrow History");
            System.out.println("6. View Available Books");
            System.out.println("7. Search Books by Title");
            System.out.println("8. View Statistics");
            System.out.println("9. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 9);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(currentUser);
                    break;
                case 2:
                    librarySystem.editStudentInformation(currentUser);
                    break;
                case 3:
                    librarySystem.borrowBook(currentUser);
                    break;
                case 4:
                    librarySystem.returnBook(currentUser);
                    break;
                case 5:
                    librarySystem.viewMyBorrows(currentUser);
                    break;
                case 6:
                    librarySystem.displayAvailableBooks();
                    break;
                case 7:
                    librarySystem.searchBooksByTitle();
                    break;
                case 8:
                    librarySystem.displayStatistics();
                    break;
                case 9:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void displayLoggedInEmployeeMenu() {
        while (currentEmployee != null) {
            System.out.println("\n=== Employee Dashboard ===");
            System.out.println("1. View Statistics");
            System.out.println("2. Search Books by Title");
            System.out.println("3. View Available Books");
            System.out.println("4. View All Books");
            System.out.println("5. Change Password");

            if (currentEmployee.isManager()) {
                System.out.println("6. Register New Employee");
                System.out.println("7. View All Employees");
                System.out.println("8. View All Students");
                System.out.println("9. Logout");
                System.out.print("Please enter your choice: ");

                int choice = getIntInput(1, 9);
                handleManagerChoice(choice);
            } else {
                System.out.println("6. Logout");
                System.out.print("Please enter your choice: ");

                int choice = getIntInput(1, 6);
                handleStaffChoice(choice);
            }
        }
    }

    private void handleManagerChoice(int choice) {
        switch (choice) {
            case 1:
                librarySystem.displayStatistics();
                break;
            case 2:
                librarySystem.searchBooksByTitle();
                break;
            case 3:
                librarySystem.displayAvailableBooks();
                break;
            case 4:
                librarySystem.getBookManager().displayAllBooks();
                break;
            case 5:
                handleChangePassword();
                break;
            case 6:
                handleEmployeeRegistration();
                break;
            case 7:
                librarySystem.displayAllEmployees();
                break;
            case 8:
                librarySystem.getStudentManager().displayStudents();
                break;
            case 9:
                currentEmployee = null;
                librarySystem.setCurrentEmployee(null);
                System.out.println("Logged out successfully.");
                return;
            default:
                System.out.println("Invalid option! Please try again.");
        }
    }

    private void handleStaffChoice(int choice) {
        switch (choice) {
            case 1:
                librarySystem.displayStatistics();
                break;
            case 2:
                librarySystem.searchBooksByTitle();
                break;
            case 3:
                librarySystem.displayAvailableBooks();
                break;
            case 4:
                librarySystem.getBookManager().displayAllBooks();
                break;
            case 5:
                handleChangePassword();
                break;
            case 6:
                currentEmployee = null;
                librarySystem.setCurrentEmployee(null);
                System.out.println("Logged out successfully.");
                return;
            default:
                System.out.println("Invalid option! Please try again.");
        }
    }

    private void handleChangePassword() {
        System.out.println("\n--- Change Password ---");

        System.out.print("Current password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("New password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match.");
            return;
        }

        if (librarySystem.changeEmployeePassword(currentEmployee.getUsername(), currentPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password. Please check your current password.");
        }
    }

    private void handleEmployeeRegistration() {
        System.out.println("\n--- New Employee Registration ---");

        System.out.print("Employee name: ");
        String name = scanner.nextLine();

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Role (manager/staff): ");
        String role = scanner.nextLine();

        if (!role.equalsIgnoreCase("manager") && !role.equalsIgnoreCase("staff")) {
            System.out.println("Invalid role. Please enter 'manager' or 'staff'.");
            return;
        }

        librarySystem.registerEmployee(name, employeeId, username, password, role.toLowerCase());
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}