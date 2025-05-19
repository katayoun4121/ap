package ap.exercises.MidtermProj;

public class Admin {
    private String username ;
    private String password ;
    private String fullname ;
    private  String education;

    public Admin(String username,String education,String password,String fullname){
        this.fullname=fullname;
        this.education=education;
        this.username=username;
        this.password=password;
    }

    public String getEducation() {
        return education;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullname;
    }
}
