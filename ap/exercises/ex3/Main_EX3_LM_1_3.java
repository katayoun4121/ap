package ap.exercises.ex3;
class Student{
    private String name;
    public Student(String name){
        this.name=name;
    }
    public String getName(){
        return  name;
    }
}
public class Main_EX3_LM_1_3 {
    public static boolean searchStudent(Student[] students, String findName) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(findName)) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        Student[] students={new Student("katy"),
                new Student("mina"),new Student("melika")};
        String findName="katy";
        boolean found=searchStudent(students,findName);
        if (found){
            System.out.println("name found");
        }else {
            System.out.println("name didn't find");
        }
    }
}
