package ap.exercises.midtermExam;

public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop();
        System.out.println("welcome to the shop.");
       shop.add(new Laptop(2000,14,2.0,"blue"));
       shop.add(new Case(2000,"blue","mini","new"));
        shop.printAll();
        }
    }

