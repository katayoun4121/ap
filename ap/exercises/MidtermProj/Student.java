package ap.exercises.MidtermProj;
import java.time.LocalDate;
public class Student {
    private String fullName;
    private String username;
    private String studentId;
    private String major;
    private String password;
    public Student(String fullName,String username,String password,String studentId,String major){
        this.fullName= fullName;
        this.username= username;
        this.password= password;
        this.studentId= studentId;
        this.major=major;
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

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullInfo(){
        return "Name:"+getFullName()+"\n"+"studentId:"+studentId+"\n"+"std major:"+major;
    }

    @Override
    public String toString() {
        return getFullName()+"("+studentId+")";
    }
}
