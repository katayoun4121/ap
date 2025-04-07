package ap.exercises.ex3;

class Book {
    private String title;
    private String author;
    private String numPage;
    private String year;

    public Book(String title, String author, String numPage, String year) {
        this.title = title;
        this.author = author;
        this.numPage = numPage;
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNumPage(String numPage) {
        this.numPage = numPage;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void displayInfo() {
        System.out.println("book name:" + title);
        System.out.println("author name:" + author);
        System.out.println("number of pages:" + numPage);
        System.out.println("publication year:" + year);
    }
}

class Student {
    private String fullName;
    private String studentId;
    private String major;

    public Student(String fullName, String studentId, String major) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.major = major;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void displayInfo() {
        System.out.println("student's full name:" + fullName);
        System.out.println("studentId:" + studentId);
        System.out.println("student's major:" + major);
    }
}

public class Main_EX3_LM_1_1 {
    public static void main(String[] args) {
        Book book1 = new Book("sinoohe's bio", "sinoohe", "10000", "1899");
        Book book2 = new Book("hassani", "ali", "15", "1999");
        Student student1 = new Student("katayoun akhoundi", "403463111", "computer science");
        Student student2 = new Student("saber abar", "403463112", "electric");
        System.out.println("--------------------------------");
        System.out.println("main information: ");

        book1.displayInfo();
        System.out.println("--------------------------------");
        book2.displayInfo();
        System.out.println("--------------------------------");
        student1.displayInfo();
        System.out.println("--------------------------------");
        student2.displayInfo();
        System.out.println("--------------------------------");
        student1.setStudentId("402575111");
        book1.setYear("2017");
        System.out.println("--------------------------------");
        System.out.println("edited information.");
        book1.displayInfo();
        System.out.println("--------------------------------");
        student1.displayInfo();

    }
}

