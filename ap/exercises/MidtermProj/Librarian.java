package ap.exercises.MidtermProj;

import java.util.Scanner;

public class Librarian {
    private String FullName;
    private String employeeId;
    private String password;
    private String username;
    public Librarian(String fullName,String username, String employeeId){
        this.FullName=fullName;
        this.username=username;
        this.employeeId=employeeId;
        this.password=password;
    }

    public String getFullName() {
        return FullName;
    }

    public String getUsername() {
        return username;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public void editPersonalInfo(Scanner scan){
        System.out.println("new name: ");
        this.FullName=scan.nextLine();
        System.out.println("new employee id: ");
        this.employeeId=scan.nextLine();
        System.out.println("new password: ");
        this.password= scan.nextLine();
        System.out.println("information edited successfully");
    }
    @Override
    public String toString(){
        return "Librarian{"+"FullName="+FullName+'\''+"employeeId="+employeeId+'}';
    }
}
