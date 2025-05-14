package ap.exercises.MidtermProj;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class DataStorage {
    public void saveStudent(List<Student> students){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.dat"))){
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("error in saving");
        }
    }
    public List<Student> loadStudents(){
        try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream("students.dat"))){
            return(List<Student>) ois.readObject();
        }catch (ClassNotFoundException e){
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("error in loading.");
            return new ArrayList<>();
        }
    }
    public void saveLibrarian(List<Librarian> librarians){
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("librarians.dat"))){
            oos.writeObject(librarians);
        } catch (IOException e) {
            System.out.println("error in saving");
        }
    }
    public List<Librarian> loadlibrarians(){
        try(ObjectInputStream ois =new ObjectInputStream(new FileInputStream("librarians.dat"))){
            return(List<Librarian>) ois.readObject();
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("error in loading");
            return  new ArrayList<>();
        }
    }
    public void saveAdmins(List<Admin> admins){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("admins.dat"))){
            oos.writeObject(admins);
        } catch (IOException e) {
            System.out.println("error in saving.");
        }
    }
    public List<Admin> loadAdmins(){
        try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream("admins.dat"))){
            return (List<Admin>) ois.readObject();
        } catch (ClassNotFoundException e) {
            List<Admin> Admins= new ArrayList<>();
            Admins.add(new Admin("ddd","123","mr.amiri"));
            return  Admins;
        } catch (IOException e) {
            System.out.println("error in loading");
            List<Admin> Admins = new ArrayList<>();
            Admins.add(new Admin("ddd","123","mr.amiri"));
            return Admins;
        }
    }
}
