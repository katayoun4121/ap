package ap.exercises.MidtermProj;

public class BorrowRecord {
    private String recordId;
    private String stdusername;
    private String bookId;
    private String borrowDate;
    private String approvedBy;
    private String returnApprovedBy;
    private boolean approved;
    private  boolean returned;
    private  boolean returnRequest;
    public BorrowRecord(String recordId,String stdusername,String bookId,
                        boolean approved, boolean returned,String borrowDate){
        this.recordId=recordId;
        this.stdusername=stdusername;
        this.bookId=bookId;
        this.approved=approved;
        this.returned=returned;
        this.borrowDate=borrowDate;
        this.returnRequest=false;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getStdusername() {
        return stdusername;
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isReturned() {
        return returned;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public boolean isReturnRequest() {
        return returnRequest;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getReturnApprovedBy() {
        return returnApprovedBy;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnRequest(boolean returnRequest) {
        this.returnRequest = returnRequest;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public void setReturnApprovedBy(String returnApprovedBy) {
        this.returnApprovedBy = returnApprovedBy;
    }
}
