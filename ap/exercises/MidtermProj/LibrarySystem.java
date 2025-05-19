package ap.exercises.MidtermProj;

import java.util.ArrayList;
import java.util.Scanner;

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.run();
    }
}
 class Library{
    private ArrayList<Student> students;
    private ArrayList<Librarian> librarians;
    private ArrayList<Book> books;
    private ArrayList<BorrowRecord> borrowRecords;
    private Scanner scan;
    private Admin admin;
    public Library(){
        students= new ArrayList<>();
        librarians=new ArrayList<>();
        books=new ArrayList<>();
        borrowRecords=new ArrayList<>();
        scan= new Scanner(System.in);
        admin= new Admin("2112","phd","1234","mr.amini");
        librarians.add(new Librarian("mrs.shahed","5656","0011"));
        students.add(new Student("sima amini","2323","4444","403463","cs"));
        books.add(new Book("death or life","11","j.k.roling",700,1995));
        books.add(new Book("big java","007","jhon smith",1150,2003));
    }
    public void run(){
        while (true){
            System.out.println("---library system---\n");
            System.out.println("1.std check in");
            System.out.println("2.librarian check in.");
            System.out.println("3.admin check in ");
            System.out.println("4.exit");
            System.out.println("your choice: ");
            int choice = scan.nextInt();
            scan.nextLine();
            switch(choice){
                case 1: studentLogin(); break;
                case 2: librarianLogin(); break;
                case 3: adminLogin(); break;
                case 4:
                    System.out.println("exit");
                    return;
                default:
                    System.out.println("invalid choice");
            }
        }
    }
    private void studentLogin(){
        System.out.println("username: ");
            String username=scan.nextLine();
        System.out.println("password: ");
        String password=scan.nextLine();
        Student student =findStudent(username,password);
        if (student!= null){
            studentMenu(student);
        }else {
            System.out.println("password or username invalid");
        }
    }
    private  void librarianLogin(){
        System.out.println("username: ");
        String username=scan.nextLine();
        System.out.println("password: ");
        String password=scan.nextLine();
        Librarian librarian = findLibrarian(username,password);
        if (librarian!= null){
            librarianMenu(librarian);
        }else {
            System.out.println("invalid");
        }
    }
    private void adminLogin(){
        System.out.println("username: ");
        String username=scan.nextLine();
        System.out.println("password: ");
        String password=scan.nextLine();
        if (admin.getUsername().equals(username)&&admin.getPassword().equals(password)){
            adminMenu();
        }else {
            System.out.println("invalid");
        }
    }
    private Student findStudent(String username,String password){
        for (Student student: students){
            if (student.getUsername().equals(username)&&student.getPassword().equals(password)){
                return  student;
            }
        }
        return null;
    }
     private Librarian findLibrarian(String username,String password){
         for (Librarian librarian: librarians){
             if (librarian.getUsername().equals(username)&&librarian.getPassword().equals(password)){
                 return  librarian;
             }
         }
         return null;
     }
     private void studentMenu(Student student){
        while(true){
            System.out.println("---std menu---\n");
            System.out.println("1.search book");
            System.out.println("2.ask for loaning book");
            System.out.println("3.loaned books");
            System.out.println("4.ask for returning books");
            System.out.println("5.exit");
            System.out.println("your choice:");
            int choice= scan.nextInt();
            scan.nextLine();
            switch(choice){
                case 1: searchBooks(); break;
                case 2: requestBorrowBook(student); break;
                case 3: viewBorrowedBooks(student); break;
                case 4: requestReturnBook(student); break;
                case 5: return;
                default:
                    System.out.println("invalid");
            }
        }
     }
     private  void librarianMenu(Librarian librarian){
        while (true){
            System.out.println("---librarian menu---\n");
            System.out.println("1.edit info");
            System.out.println("2.add book");
            System.out.println("3.approve book loan");
            System.out.println("4.confirm book return");
            System.out.println("5.exit");
            System.out.println("your choice:");
            int choice = scan.nextInt();
            scan.nextLine();
            switch(choice){
                case 1: librarian.editPersonalInfo(scan); break;
                case 2:addBook();break;
                case 3:approveBorrowRequests(librarian); break;
                case 4:approveReturnRequests(librarian); break;
                case 5:return;
            }
        }
     }

     private void adminMenu() {
         while (true) {
             System.out.println("---admin menu---\n");
             System.out.println("1.add librarian");
             System.out.println("2.show books which are returned late");
             System.out.println("3.exit");
             System.out.print("your choice");

             int choice = scan.nextInt();
             scan.nextLine();

             switch (choice) {
                 case 1: addLibrarian(); break;
                 case 2: viewOverdueBooks(); break;
                 case 3: return;
             }
         }
     }

     private void searchBooks() {
         System.out.print("enter the title of the book");
         String query = scan.nextLine().toLowerCase();

         System.out.println("search result");
         boolean found = false;
         for (Book book : books) {
             if (book.getTitle().toLowerCase().contains(query) ||
                     book.getAuthor().toLowerCase().contains(query)) {
                 System.out.println(book);
                 found = true;
             }
         }

         if (!found) {
             System.out.println("book not found");
         }
     }

     private void requestBorrowBook(Student student) {
         System.out.print("enter bookId");
         String bookId = scan.nextLine();

         Book book = findBookById(bookId);
         if (book == null) {
             System.out.println("book not found");
             return;
         }
         BorrowRecord record = new BorrowRecord(
                 "BR" + (borrowRecords.size() + 1),
                 student.getUsername(),
                 bookId,
                 false,
                 false,
                 null
         );
         borrowRecords.add(record);
         System.out.println("request is confirmed");
     }

     private void viewBorrowedBooks(Student student) {
         System.out.println("the borrowed book\n");
         boolean found = false;
         for (BorrowRecord record : borrowRecords) {
             if (record.getStdusername().equals(student.getUsername()) &&
                     record.isApproved() && !record.isReturned()) {
                 Book book = findBookById(record.getBookId());
                 System.out.println(book + "loan date: " + record.getBorrowDate());
                 found = true;
             }
         }

         if (!found) {
             System.out.println("there is no book");
         }
     }

     private void requestReturnBook(Student student) {
         System.out.println("returning books: ");
         ArrayList<BorrowRecord> studentRecords = new ArrayList<>();

         for (BorrowRecord record : borrowRecords) {
             if (record.getStdusername().equals(student.getUsername()) &&
                     record.isApproved() && !record.isReturned() && !record.isReturnRequest()) {
                 Book book = findBookById(record.getBookId());
                 System.out.println(record.getRecordId() + ": " + book.getTitle());
                 studentRecords.add(record);
             }
         }

         if (studentRecords.isEmpty()) {
             System.out.println("you have no book for returning");
             return;
         }

         System.out.print("enter record id");
         String recordId = scan.nextLine();

         for (BorrowRecord record : studentRecords) {
             if (record.getRecordId().equals(recordId)) {
                 record.setReturnRequest(true);
                 System.out.println("application has been confirmed");
                 return;
             }
         }

         System.out.println("invalid");
     }

     private void addBook() {
         System.out.print("book id");
         String id = scan.nextLine();
         System.out.print("title");
         String title = scan.nextLine();
         System.out.print("author");
         String author = scan.nextLine();
         scan.nextLine();

         books.add(new Book());
         System.out.println("book added");
     }

     private void approveBorrowRequests(Librarian librarian) {
         System.out.println("borrow requests: ");
         boolean found = false;

         for (BorrowRecord record : borrowRecords) {
             if (!record.isApproved() && !record.isReturned()) {
                 Student student = findStudentByUsername(record.getStdusername());
                 Book book =findBookById(record.getBookId());

                 System.out.println("record id" + record.getRecordId());
                 System.out.println("student :" + student.getFullName());
                 System.out.println("book: " + book.getTitle());
                 System.out.println("1.confirm\n2.pass\n3.pass others");
                 System.out.print("your choice");

                 int choice = scan.nextInt();
                 scan.nextLine();
                 if (choice == 1) {
                     record.setApproved(true);
                     record.setBorrowDate(java.time.LocalDate.now().toString());
                     record.setApprovedBy(librarian.getUsername());
                     System.out.println("loan request accepted");
                 } else if (choice == 2) {
                     borrowRecords.remove(record);
                     System.out.println("request passed");
                 } else if (choice == 3) {
                     return;
                 }

                 found = true;
                 break;
             }
         }

         if (!found) {
             System.out.println("no new request");
         }
     }

     private void approveReturnRequests(Librarian librarian) {
         System.out.println("returning book application: ");
         boolean found = false;

         for (BorrowRecord record : borrowRecords) {
             if (record.isReturnRequest() && !record.isReturned()) {
                 Student student = findStudentByUsername(record.getStdusername());
                 Book book = findBookById(record.getBookId());

                 System.out.println("record id" + record.getRecordId());
                 System.out.println("student" + student.getFullName());
                 System.out.println("book" + book.getTitle());
                 System.out.println("1.confirm\n2.return\n3.pass");
                 System.out.print("your choice");

                 int choice = scan.nextInt();
                 scan.nextLine();

                 if (choice == 1) {
                     record.setReturned(true);
                     record.setReturnApprovedBy(librarian.getUsername());
                     System.out.println("book returning accepted");
                 } else if (choice == 2) {
                     return;
                 }

                 found = true;
                 break;
             }
         }

         if (!found) {
             System.out.println("no new request");
         }
     }

     private void addLibrarian() {
         System.out.print("new username");
         String username = scan.nextLine();
         System.out.print("password ");
         String password = scan.nextLine();
         System.out.print("full name ");
         String fullName = scan.nextLine();

         librarians.add(new Librarian(username, password, fullName));
         System.out.println("librarian added");
     }

     private void viewOverdueBooks() {
         System.out.println("late books");
         boolean found = false;
         String today = java.time.LocalDate.now().toString();

         for (BorrowRecord record : borrowRecords) {
             if (record.isApproved() && !record.isReturned()) {
                 String borrowDate = record.getBorrowDate();
                 String dueDate = java.time.LocalDate.parse(borrowDate).plusDays(14).toString();

                 if (today.compareTo(dueDate) > 0) {
                     Student student = findStudentByUsername(record.getStdusername());
                     Book book = findBookById(record.getBookId());
                     System.out.println("student " + student.getFullName());
                     System.out.println("book " + book.getTitle());
                     System.out.println("borrow date:  " + borrowDate);
                     System.out.println("due date:  " + dueDate);
                     System.out.println("-----------");
                     found = true;
                 }
             }
         }

         if (!found) {
             System.out.println("no late book");
         }
     }
     private Book findBookById(String bookId) {
         for (Book book : books) {
             if (book.getId().equals(bookId)) {
                 return book;
             }
         }
         return null;
     }

     private Student findStudentByUsername(String username) {
         for (Student student : students) {
             if (student.getUsername().equals(username)) {
                 return student;
             }
         }
         return null;
     }

     private Librarian findLibrarianByUsername(String username) {
         for (Librarian librarian : librarians) {
             if (librarian.getUsername().equals(username)) {
                 return librarian;
             }
         }
         return null;
     }
 }
