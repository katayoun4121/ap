package ap.exercises.FinalExam;

public class Book extends Product {
    private String writer;
    private String title;
    public Book(String name, double price, String writer, String title){
        super(name,price);
        this.title=title;
        this.writer=writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer){
        this.writer=writer;
    }
    @Override
    public String toString() {
        return "name=" + getName() + " price= " + getPrice() +
                " author= " + writer + " title= " + title + " ";
    }
}
