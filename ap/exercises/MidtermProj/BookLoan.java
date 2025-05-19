package ap.exercises.MidtermProj;
import java.util.Date;
public class BookLoan {
    private Book bookId;
    private Student studentId;
    private Librarian librarianId;
    private Date loanDate;
    private Date returnDate;
    private  Date dueDate;
    private Book borrowedBook;
    private Librarian returnLib;
    private boolean returned;
    public BookLoan(Book bookId,Student studentId,Librarian returnLib,Librarian librarianId,
                    Date loanDate,Date dueDate,Date returnDate){
this.bookId=bookId;
this.dueDate=dueDate;
this.returnLib=returnLib;
this.studentId=studentId;
this.librarianId=librarianId;
this.loanDate=loanDate;
this.returnDate=returnDate;
this.returned=false;
    }
    public boolean isOverdue(){
        Date now=new Date();
        if (returnDate!=null){
            return returnDate.after(dueDate);
        }
        return now.after(dueDate);
    }
}
