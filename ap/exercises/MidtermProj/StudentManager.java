package ap.exercises.MidtermProj;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    private List<Student> students;
    public StudentManager(){
        this.students=new ArrayList<>();
    }
    public void showStudentMenu(Scanner scan){
        System.out.println("\n---student menu---");
        System.out.println("1.check in");
        System.out.println("2.log in");
        System.out.println("3.search book");
        System.out.println("your choice:");
        int choice= scan.nextInt();
        scan.nextLine();
        switch (choice){
            case 1:
                registerStudent(scan);
                break;
            case 2:
                loginStudent(scan);
                break;
            case 3:
                searchBooks(scan);
                break;
        }
    }
    private void registerStudent(Scanner scan){
        System.out.println("\n---register std---");
        System.out.println("username");
        String username= scan.nextLine();
        if (isUsernameExists(username)){
            System.out.println("username has been used.");
            return;
        }
        System.out.println("password");
        String password= scan.nextLine();
        System.out.println("fullname");
        String fullname= scan.nextLine();
        System.out.println("studentId");
        String studentId= scan.nextLine();
        System.out.println("major");
        String major= scan.nextLine();
        Student newstd=new Student(username,password,fullname,studentId,major);
        students.add(newstd);
        System.out.println("std registered");
    }
    private void loginStudent(Scanner scan){
        System.out.println("\n---std log in---");
        System.out.println("username");
        String username= scan.nextLine();
        System.out.println("password");
        String password= scan.nextLine();
        for (Student student:students){
            if (student.getUsername().equals(username)&&student.getPassword().equals(password)){
                System.out.println("logged in");
                return;
            }
        }
        System.out.println("username or password is wrong.");
    }
    private  void searchBooks(Scanner scan){
        System.out.println("\n---search books---");
        System.out.println("title");
        String title= scan.nextLine();
        System.out.println("search for:"+title);
    }
    private  boolean isUsernameExists(String username){
        for (Student student :students){
            if (student.getUsername().equals(username)){
                return  true;
            }
        }
        return false;
    }
    public List<Student> getStudents(){
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
