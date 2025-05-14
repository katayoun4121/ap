package ap.exercises.MidtermProj;

public class Admin {
    private String username ;
    private String password ;
    private String fullname ;
    public Admin(String username,String password,String fullname){
        this.fullname=fullname;
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }
}
