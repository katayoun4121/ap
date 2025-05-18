package ap.exercises.MidtermProj;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class File {
    public static void saveStudents(List<Student> students){
        try(FileWriter writer =new FileWriter("students.txt")){
            for (Student student : students){
                writer.write(student.getUsername()+","+student.getPassword()+","+
                        student.getFullName()+","+student.getStudentId()+","+student.getMajor()+"\n");
            }
        }catch (IOException e){
            System.out.println("error in saving.");
        }
    }
    public List<Student> loadStudents(){
        List<Student> students= new ArrayList<>();
        File file= new File("students.txt");
        if (!file.exists()){
            return  students;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line= reader.readLine())!= null){
                String[] pts=line.split(",");
                if (pts.length==5) {
                    Student student = new Student(pts[0], pts[1], pts[2], pts[3],pts[4]);
                    students.add(student);
                }
            }
        }catch (IOException e){
            System.out.println("error in loading stds.");
        }
        return  students;
    }
    public static void saveLibrarians(List<Librarian> librarian){
        try(FileWriter writer =new FileWriter("librarians.txt")){
            for (Librarian librarian : librarians){
                writer.write(librarian.getUsername()+","+librarian.getPassword()+","+
                        librarian.getFullName()+","+librarian.getEmployeeId()+","+"\n");
            }
        }catch (IOException e){
            System.out.println("error in saving.");
        }
    }
    public List<Librarian> loadLibrarians(){
        List <Librarian> librarians = new ArrayList<>();
        File file= new File("librarians.txt");
        if (!file.exists()){
            return  librarians;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line= reader.readLine())!= null){
                String[] pts=line.split(",");
                if (pts.length==5) {
                    Student student = new Student(pts[0], pts[1], pts[2], pts[3],pts[4]);
                    librarians.add(librarian);
                }
            }
        }catch (IOException e){
            System.out.println("error in loading librarians.");
        }
        return  librarians;
    }
    public static void saveAdmins(List<Admin> admins){
        try(FileWriter writer =new FileWriter("admins.txt")){
            for (Admin admin : admins){
                writer.write(admin.getUsername()+","+admin.getPassword()+","+
                        admin.getFullName()+","+"\n");
            }
        }catch (IOException e){
            System.out.println("error in saving.");
        }
    }
    public List<Student> loadStudents(){
        List<Admin> admins= new ArrayList<>();
        File file= new File("admins.txt");
        if (!file.exists()){
            return  admin;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line= reader.readLine())!= null){
                String[] pts=line.split(",");
                if (pts.length==5) {
                    Student student = new Student(pts[0], pts[1], pts[2], pts[3],pts[4]);
                    admins.add(admin);
                }
            }
        }catch (IOException e){
            System.out.println("error in loading admins.");
        }
        return  admins;
    }
}


