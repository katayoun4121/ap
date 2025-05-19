package ap.exercises.MidtermProj;

import java.util.Scanner;

public class Input {
    Scanner scan = new Scanner(System.in);
    public Book addingBooks(){
        System.out.println("the book name:");
        String title=scan.next();
        System.out.println("the author:");
        String author= scan.next();
        System.out.println("number of pages: ");
        int pageNum=scan.nextInt();
        System.out.println("publication year: ");
        int year= scan.nextInt();
        Book book = new Book(title,author,pageNum,year);
        return  book;
    }
    public Student signup(){
        System.out.println("please enter your information.");
        System.out.println("full name: ");
        String FullName=scan.next();
        System.out.println("username: ");
        String username=scan.next();
        System.out.println("password: ");
        String password= scan.next();
        System.out.println("major: ");
        String major= scan.next();
        System.out.println("studentId: ");
        String studentId= scan.next();
        Student student=new Student(FullName,studentId,major,password,username);
        return  student;
    }
    public String searchBooks(){
        System.out.println("please enter the name of the book: ");
        String title= scan.next();
        return title;
    }
    public Student logIn(){
        System.out.println("please enter your information.");
        System.out.println("fullName: ");
        String FullName= scan.next();
        System.out.println("username: ");
        String username=scan.next();
        System.out.println("password: ");
        String password= scan.next();
        System.out.println("major: ");
        String major= scan.next();
        System.out.println("studentId: ");
        String studentId= scan.next();
        Student student = new Student(FullName,username,password,studentId,major);
        return student;
    }
    public Librarian LibLogIn(){
        System.out.println("employee number: ");
        String employeeId= scan.next();
        System.out.println("full name: ");
        String FullName= scan.next();
        System.out.println("username: ");
        String username= scan.next();
        System.out.println("password: ");
        String password= scan.next();
        Librarian librarian = new Librarian(employeeId,FullName,username);
        return librarian;
    }
}
