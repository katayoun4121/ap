package ap.exercises.MidtermProj;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String fullName;
    private String username;
    private String studentId;
    private String major;
    private String password;
    private List<String> borrowedBooks;
    public Student(String fullName,String username,String password,String studentId,String major){
        this.fullName= fullName;
        this.username= username;
        this.password= password;
        this.studentId= studentId;
        this.major=major;
        this.borrowedBooks=new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void borrowBook(String bookId){
        borrowedBooks.add(bookId);
    }
    public void returnBook(String bookId){
        borrowedBooks.remove(bookId);
    }
}
