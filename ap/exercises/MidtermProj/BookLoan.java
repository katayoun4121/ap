package ap.exercises.MidtermProj;
import java.util.Date;
public class BookLoan {
    private String bookId;
    private String studentId;
    private String librarianId;
    private String loanDate;
    private String returnDate;
    private boolean returned;
    public BookLoan(String bookId,String studentId,String librarianId,
                    String loanDate,String returnDate){
this.bookId=bookId;
this.studentId=studentId;
this.librarianId=librarianId;
this.loanDate=loanDate;
this.returnDate=returnDate;
this.returned=false;
    }

    public String getBookId() {
        return bookId;
    }


    public String getStudentId() {
        return studentId;
    }
    public String getLibrarianId(){
        return librarianId;
    }

    public boolean isReturned() {
        return returned;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
