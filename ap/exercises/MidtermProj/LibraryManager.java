package ap.exercises.MidtermProj;
import java.util.Scanner;
public class LibraryManager {
    private StudentManager studentManager;
    private LibrarianManager librarianManager;
    private AdminManager adminManager;
    private DataStorage dataStorage;
    public  LibraryManager(){
        this.studentManager=new StudentManager();
        this.librarianManager= new LibrarianManager();
        this.adminManager = new AdminManager();
        this.dataStorage= new DataStorage();
        loadData();
    }
    public void handleUserType(int userType,Scanner scan){
        switch (userType){
            case 1:
                studentManager.showStudentMenu(scan);
                break;
            case 2:
                librarianManager.showLibrarianMenu(scan);
                break;
            case 3:
                adminManager.showAdminMenu(scan);
                break;
        }
        saveData();
    }
    private void loadData(){
        studentManager.setStudents(dataStorage.loadStudents());
        librarianManager.setLibrarians(dataStorage.loadlibrarians());
        adminManager.setAdmins(dataStorage.loadAdmins());
        System.out.println("--------------");
    }
    private void saveData(){
        dataStorage.saveStudent(studentManager.getStudents());
        dataStorage.saveLibrarian(librarianManager.getLibrarians());
        dataStorage.saveAdmins(adminManager.getAdmins());
    }
}