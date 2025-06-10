package ap.exercises.EX7;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibrarySystem system = new LibrarySystem();
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("save data\n");
            system.saveData();
        }));

        initializeSampleData(system);

        while (true) {
            System.out.println("\n--- LIBRARY SYSTEM---");
            System.out.println("1.student");
            System.out.println("2. librarian");
            System.out.println("3. library admin");
            System.out.println("4. exit");

            System.out.print("choose your type: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1:
                    Student.showStudentMenu(scanner, system, null);
                    break;
                case 2:
                    Librarian.showLibrarianMenu(scanner, system, null);
                    break;
                case 3:
                    Admin.showAdminMenu(scanner, system, null);
                    break;
                case 4:
                    System.out.println("thanks for checking");
                    system.saveData();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("invalid");
            }
        }
    }

    private static void initializeSampleData(LibrarySystem system) {
        system.addBook(new Book("B001", "Java ", "John Doe"));
        system.addBook(new Book("B002", "Data ", "Jane Smith"));
        system.addBook(new Book("B003", "Algorithms", "Robert Johnson"));

        system.addUser(new User("ADM-001", "admin", "admin123", "admin"));
        system.addUser(new User("LIB-001", "librarian", "lib123", "librarian"));
        system.addUser(new User("STU-001", "student1", "stu123", "student"));

        system.addStudent(new Student("STU-001", "melina ahmadi"));
    }
}
