package ap.exercises.midtermExam;

import java.util.ArrayList;

public class Shop {
   private ArrayList<Laptop> laptops;
    private ArrayList<Case> cases;


    public Shop(){
          laptops=new ArrayList<>();
         cases= new ArrayList<>();

    }
public void printAll(){
    System.out.println("---laptops---");
    for(Laptop laptops:laptops){
        System.out.println(laptops);
    }
    System.out.println("---Cases---");
    for (Case cases: cases){
        System.out.println(cases);
    }
}

    public void add(Case cases) {
        cases.add(new Case(cases.getPrice(), cases.getColor(), cases.getCaseType(), cases.getModel()));
    }
    public void add(Laptop laptops){
        laptops.add(new Laptop(laptops.getWeight(), laptops.getSizeOfScreen(), laptops.getPrice(), laptops.getColor()));
    }
}
