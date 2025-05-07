package ap.exercises;
import java.util.ArrayList;
class book{
    private String name;
    private int price;
    public book(String name, int price){
        this.name=name;
        this.price=price;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;

    }
}
class pen{
    private String color;
    private int cost;
    private String brand;
    public pen(String color,int cost,String brand){
        this.color=color;
        this.cost=cost;
        this.brand=brand;
    }
    public String getcolor(){
        return color;
    }
    public String getBrand(){
        return brand;
    }
    public int getCost(){
        return cost;
    }
}
public class Main {
    public static void main(String[] args){
        ArrayList<book> books=new ArrayList<>();
        books.add(new book("life and goals",80));
        books.add(new book("death",75));
        ArrayList<pen> pens= new ArrayList<>();
        pens.add(new pen("blue",50,"kian"));
        pens.add(new pen("red",10,"bik"));
        System.out.println("books");
        for (book book: books){
            System.out.println("name:"+book.getName()+"price:"+book.getPrice());
        }
        System.out.println("pens");
        for(pen pen: pens){
            System.out.println("color:"+pen.getcolor()+"cost:"+pen.getCost()+"brand:"+pen.getBrand());
        }
    }
}
