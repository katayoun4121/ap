package ap.exercises.MidtermProj;

public class Librarian {
    private String fullName;
    private String employeeId;
    private String password;
    private String username;
    public Librarian(String fullName,String username, String employeeId,String password){
        this.fullName=fullName;
        this.username=username;
        this.employeeId=employeeId;
        this.password=password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public String getFullInfo(){
        return "Name:"+ getFullName()+"\n"+"EmployeeId:"+
                getEmployeeId();
    }

    @Override
    public String toString() {
        return getFullName()+"("+employeeId+")";
    }
}
