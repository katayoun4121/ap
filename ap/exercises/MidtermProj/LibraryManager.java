package ap.exercises.MidtermProj;
import ap.exercises.Book;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class LibraryManager {
    private List<Student> students;
    private List<Librarian> librarians;
    private List<Admin> admins;
    private List<Book> books;
    private List<BookLoan> bookLoans;
    private Scanner scanner;

    public LibraryManager() {
        this.students = File.loadStudents();
        this.librarians = File.loadLibrarians();
        this.admins = File.loadAdmins();
        this.books = File.loadBooks();
        this.bookLoans = File.loadBookLoans();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("---library system---\n");
            System.out.println("1.entering system.");
            System.out.println("2.std check in");
            System.out.println("3.exit");
            System.out.print("your choice ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    saveAllData();
                    System.exit(0);
                default:
                    System.out.println("invalid choice");
            }
        }
    }

    private void login() {
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password");
        String password = scanner.nextLine();


        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                showStudentMenu(student);
                return;
            }
        }


        for (Librarian librarian : librarians) {
            if (librarian.getUsername().equals(username) && librarian.getPassword().equals(password)) {
                showLibrarianMenu(librarian);
                return;
            }
        }


        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                showAdminMenu();
                return;
            }
        }

        System.out.println("password or username is invalid");
    }

    private void registerStudent() {
        System.out.println("---student registering---\n");
        System.out.print("username: ");
        String username = scanner.nextLine();
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                System.out.println("username has been used.");
                return;
            }
        }

        System.out.print("password");
        String password = scanner.nextLine();
        System.out.print("FullName:");
        String name = scanner.nextLine();
        System.out.print("studentId");
        String studentId = scanner.nextLine();
        System.out.print("major: ");
        String major = scanner.nextLine();

        Student newStudent = new Student(username, password,fullName, studentId, major);
        students.add(newStudent);
        File.saveStudents(students);
        System.out.println("registered successfully. ");
    }

    private void showStudentMenu(Student student) {
        while (true) {
            System.out.println("---std menu---\n");
            System.out.println("1.search book");
            System.out.println("2.loaning book");
            System.out.println("3.show loaned book");
            System.out.println("4.returning books");
            System.out.println("5.exit.");
            System.out.print("your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBooks();
                    break;
                case 2:
                    requestBookLoan(student);
                    break;
                case 3:
                    showBorrowedBooks(student);
                    break;
                case 4:
                    returnBook(student);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("invalid.");
            }
        }
    }

    private void showLibrarianMenu(Librarian librarian) {
        while (true) {
            System.out.println("---librarian menu---\n");
            System.out.println("1.add books.");
            System.out.println("2.confirming book loaning");
            System.out.println("3.confirming book rerturning.");
            System.out.println("4.exit.");
            System.out.print("your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    approveBookLoan(librarian);
                    break;
                case 3:
                    approveBookReturn();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("invalid");
            }
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("---admin menu--\n");
            System.out.println("1.add librarian");
            System.out.println("2.show loaned book");
            System.out.println("3.show librarians list");
            System.out.println("4.exit");
            System.out.print("your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addLibrarian();
                    break;
                case 2:
                    showOverdueBooks();
                    break;
                case 3:
                    showLibrarianStats();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("invalid");
            }
        }
    }

    private void saveAllData() {
        File.saveStudents(students);
        File.saveLibrarians(librarians);
        File.saveAdmins(admins);
        File.saveBooks(books);
        File.saveBookLoans(bookLoans);
    }

    private void searchBooks() {
        System.out.print("name of the considered book");
        String query = scanner.nextLine().toLowerCase();

        System.out.println("search conclusion: ");
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                    book.getAuthor().toLowerCase().contains(query)) {
                System.out.println(book.getId() + " - " + book.getTitle() +
                        " (" + book.getAuthor() + ") - " +
                        (book.isAvailable() ? "valid" : "borrowed"));
            }
        }
    }

    private void requestBookLoan(Student student) {
        System.out.print("book id: ");
        String bookId = scanner.nextLine();


        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("book not found.");
            return;
        }

        if (!book.isAvailable()) {System.out.println("book is borrowed.");
            return;
        }
        book.setAvailable(false);
        student.borrowBook(bookId);
        bookLoans.add(new BookLoan(bookId, student.getStudentId(), " ",
                "today", "1 month"));

        File.saveBooks(books);
        File.saveStudents(students);
        File.saveBookLoans(bookLoans);

        System.out.println("your application has been registered.");
    }

    private Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
        private void showBorrowedBooks(Student student) {
            System.out.println("your loaned book");
            boolean isBorrowed = false;

            for (BookLoan loan : bookLoans) {
                if (loan.getStudentId().equals(student.getStudentId()) && !loan.isReturned()) {
                    Book book = findBookById(loan.getBookId());
                    if (book != null) {
                        System.out.println(book.getId() + " - " + book.getTitle() +
                                "loan date)" + loan.getLoanDate() + ")");
                        isBorrowed = true;
                    }
                }
            }

            if (!isBorrowed) {
                System.out.println("you donot have a book");
            }
        }

        private void returnBook(Student student) {
            System.out.print("the id of the book you want to give back");
            String bookId = scanner.nextLine();

            Book book = findBookById(bookId);
            if (book == null) {
                System.out.println("book not found");
                return;
            }

            for (BookLoan loan : bookLoans) {
                if (loan.getBookId().equals(bookId) &&
                        loan.getStudentId().equals(student.getStudentId()) &&
                        !loan.isReturned()) {

                    loan.setReturned(true);
                    loan.setReturnDate(new SimpleDateFormat("1404-02-11").format(new Date()));
                    book.setAvailable(true);
                    student.returnBook(bookId);

                    File.saveBooks(books);
                    File.saveStudents(students);
                    File.saveBookLoans(bookLoans);

                    System.out.println("book returned");
                    return;
                }
            }

            System.out.println("this book isnot borrowed by you");
        }

        private void addBook() {
            System.out.println("add new book");
            System.out.println("book id:");
            String id = scanner.nextLine();
            System.out.print("name:");
            String title = scanner.nextLine();
            System.out.print("author");
            String author = scanner.nextLine();
            System.out.print("year: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            books.add(new Book(id, title, author, isBorrowed, year));
            File.saveBooks(books);
            System.out.println("book added");
        }

        private void approveBookLoan(Librarian librarian) {
            System.out.println("list of loan applications");
            List<BookLoan> pendingLoans = new ArrayList<>();

            for (BookLoan loan : bookLoans) {
                if (loan.getLibrarianId().isEmpty() && !loan.isReturned()) {
                    Book book = findBookById(loan.getBookId());
                    Student student = findStudentById(loan.getStudentId());

                    if (book != null && student != null) {
                        System.out.println("book id:" + loan.getBookId() +
                                " | book: " + book.getTitle() +
                                " | student " + student.getFullName());
                        pendingLoans.add(loan);
                    }
                }
            }

            if (pendingLoans.isEmpty()) {
                System.out.println("no request");
                return;
            }

            System.out.print("book id is confirmed ");
            String bookId = scanner.nextLine();
            for (BookLoan loan : pendingLoans) {
                if (loan.getBookId().equals(bookId)) {
                    loan.setEmployeeId(librarian.getEmployeeId());
                    Book book = findBookById(bookId);
                    if (book != null) {
                        book.setAvailable(false);
                        book.increaseBorrowCount();
                    }

                    File.saveBooks(books);
                    File.saveBookLoans(bookLoans);

                    System.out.println("book loan confirmed");
                    return;
                }
            }

            System.out.println("invalid bookid");
        }

    private Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private void approveBookReturn() {
        System.out.println("list of borrowed books:\n");
        List<BookLoan> activeLoans = new ArrayList<>();

        for (BookLoan loan : bookLoans) {
            if (!loan.getLibrarianId().isEmpty() && !loan.isReturned()) {
                Book book = findBookById(loan.getBookId());
                Student student = findStudentById(loan.getStudentId());

                if (book != null && student != null) {
                    System.out.println("book id " + loan.getBookId() +
                            " | book: " + book.getTitle() +
                            " | student:  " + student.getFullName());
                    activeLoans.add(loan);
                }
            }
        }

        if (activeLoans.isEmpty()) {
            System.out.println("there is no book.");
            return;
        }

        System.out.print("book id for returning the book");
        String bookId = scanner.nextLine();

        for (BookLoan loan : activeLoans) {
            if (loan.getBookId().equals(bookId)) {
                loan.setReturned(true);
                loan.setReturnDate(new SimpleDateFormat("1405-12-11").format(new Date()));

                Book book = findBookById(bookId);
                if (book != null) {
                    book.setAvailable(true);
                }

                File.saveBooks(books);
                File.saveBookLoans(bookLoans);

                System.out.println("returning book is confirmed.");
                return;
            }
        }

        System.out.println("invalid book id");
    }

    private void addLibrarian() {
        System.out.println("adding new librarian");
        System.out.print("username");
        String username = scanner.nextLine();

        for (Librarian lib : librarians) {
            if (lib.getUsername().equals(username)) {
                System.out.println("username has been used");
                return;
            }
        }

        System.out.print("password");
        String password = scanner.nextLine();
        System.out.print("full name ");
        String name = scanner.nextLine();
        System.out.println("employee id: ");
        String empId = scanner.nextLine();

        librarians.add(new Librarian(username, password,FullName, empId));
        File.saveLibrarians(librarians);
        System.out.println("librarian added.");
    }

    private void showOverdueBooks() {
        System.out.println("books with temporarily loan");
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("1404-8-25");
        boolean found = false;

        for (BookLoan loan : bookLoans) {
            if (!loan.isReturned()) {
                try {
                    Date returnDate = sdf.parse(loan.getReturnDate());
                    if (returnDate.before(today)) {
                        Book book = findBookById(loan.getBookId());
                        Student student = findStudentById(loan.getStudentId());

                        if (book != null && student != null) {
                            System.out.println("book:  " + book.getTitle() +
                                    " | student:" + student.getFullName() +
                                    " |return date:  " + loan.getReturnDate());
                            found = true;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error.");
                }
            }
        }

        if (!found) {
            System.out.println("book not found.");        }
    }

    private void showLibrarianStats() {
        System.out.println("librarians list");
        for (Librarian librarian : librarians) {
            int loanCount = 0;
            int returnCount = 0;

            for (BookLoan loan : bookLoans) {
                if (loan.getLibrarianId().equals(librarian.getEmployeeId())) {
                    if (loan.isReturned()) {
                        returnCount++;
                    } else {
                        loanCount++;
                    }
                }
            }

            System.out.println(librarian.getFullName() +
                    " | loans " + loanCount +
                    " | returnings: " + returnCount);
        }
    }
}

}