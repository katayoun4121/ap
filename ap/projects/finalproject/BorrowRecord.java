package ap.projects.finalproject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowRecord implements Serializable {
    private static final long serialVersionUID = 3L;
    private String studentUsername;
    private String bookIsbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public BorrowRecord(String studentUsername, String bookIsbn, LocalDate borrowDate, LocalDate dueDate) {
        this.studentUsername = studentUsername;
        this.bookIsbn = bookIsbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.isReturned = false;
        this.returnDate = null;
    }

    public String getStudentUsername() { return studentUsername; }
    public String getBookIsbn() { return bookIsbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }

    public void markAsReturned() {
        this.isReturned = true;
        this.returnDate = LocalDate.now();
    }

    public boolean isOverdue() {
        return !isReturned && LocalDate.now().isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) return 0;
        return LocalDate.now().toEpochDay() - dueDate.toEpochDay();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String status = isReturned ? "Returned on " + returnDate.format(formatter) :
                isOverdue() ? "OVERDUE (" + getDaysOverdue() + " days)" : "Due on " + dueDate.format(formatter);

        return "Book ISBN: " + bookIsbn +
                " | Borrowed: " + borrowDate.format(formatter) +
                " | " + status;
    }
}