package ap.exercises.ex4;
import java.util.ArrayList;
import java.util.List;
public class RegistCash {
    private List<Double> soldItems;
    private double total;
    public RegistCash(){
        soldItems=new ArrayList<>();
        total=0;
    }
     public void addItem(double price){
        soldItems.add(price);
        total+=price;
     }

    public double getTotal() {
        return total;
    }
    public int getCount(){
        return soldItems.size();
    }
    public void clear(){
        soldItems.clear();
        total=0;
    }
    public void printReceipt(){
        String receipt="Receipt";
        receipt=receipt.concat("---------------\n");
        for(double price :soldItems){
            receipt=receipt.concat(String.format("item: %.2f $\n",price));
        }
        receipt=receipt.concat(String.format("total: %.2f $\n",total));
        System.out.println(receipt);
    }

    public static void main(String[] args) {
        RegistCash register=new RegistCash();
        register.addItem(18.5);
        register.addItem(22.7);
        register.addItem(10.11);
        register.addItem(100.33);
        register.printReceipt();
    }
}
