package ap.exercises.MidtermProj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminManager {
    private List<Admin> admins;
    public AdminManager(){
        this.admins=new ArrayList<>();
        admins.add(new Admin("ddd","123","mr.amiri"));
    }
    public void showAdminMenu(Scanner scan){
        System.out.println("\n---manager's  menu---");
        System.out.println("1.adding librarian");
        System.out.println("your choice:");
        int choice= scan.nextInt();
        scan.nextLine();
        if (choice==1){
            addLibrarian(scan);
        }else System.out.println("invalid choice");
    }

private void addLibrarian(Scanner scan) {

    System.out.println("\n---adding librarian---");
    System.out.println("username");
    String username = scan.nextLine();
    System.out.println("password");
    String password = scan.nextLine();
    System.out.println("fullname");
    String fullname = scan.nextLine();
    System.out.println("employeeId");
    String employeeId = scan.nextLine();
    System.out.println("new librarian added.");

}
public List<Admin> getAdmins(){
        return admins;
}

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}