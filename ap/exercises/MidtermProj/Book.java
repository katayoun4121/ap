package ap.exercises.MidtermProj;

public class Book {
    private String title;
    private String id;
    //private String isBorrowed;
    private String author;
    private int pageNum;
    private  int year;
    private int borrowCount;
    private boolean available;
    public Book(String title,String id,String author,int pageNum,int year){
        this.title=title;
       // this.isBorrowed=isBorrowed;
        this.available=true;
        this.borrowCount=0;
        this.id=id;
        this.author=author;
        this.pageNum=pageNum;
        this.year=year;
    }

    public Book() {

    }
    public Book(String title, String author, int pageNum, int year) {
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    //public String getIsBorrowed() {
      //  return isBorrowed;
    //}

    public boolean isAvailable() {
        return available;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void increaseBorrowCount(){
        borrowCount++;
    }
    @Override
    public String toString(){
        return "Book{"+"name="+title+'\''+",author="+author+'\''+"page number="+pageNum+
                ",year="+year+'}';
    }
}
