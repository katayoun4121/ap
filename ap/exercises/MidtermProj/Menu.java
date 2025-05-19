package ap.exercises.MidtermProj;

public class Menu {
    private String userType;
    public void setUserType(String userType){
        this.userType=userType;
    }
    public void managerMenu(){
        System.out.println("pick up your choice: ");
        System.out.println("1.add new librarian");
        System.out.println("2.get a list of books that were returned lately");
        System.out.println("3.get a list of top 10 popular books");
        System.out.println("4.exit");
    }
    public void studentMenu1(){
        System.out.println("1.sign in");
        System.out.println("2.log in");
        System.out.println("exit");
    }
    public void studentMenu2(){
        System.out.println("1.search books");
        System.out.println("2.borrowing book");
        System.out.println("3.return a book");
        System.out.println("4.get a list of unreturned borrowed books");
        System.out.println("5.exit.");
    }
    public void librarianMenu(){
        System.out.println("1.logging in");
        System.out.println("2.add a new book");
        System.out.println("3.edit information");
        System.out.println("4.exit.");
    }
}
