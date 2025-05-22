package ap.exercises.midtermExam;

public class Laptop {
    private double sizeOfScreen;
    private double weight;
    private String color;
    private double price;
    public Laptop(double price,double sizeOfScreen,double weight,String color){
        this.price=price;
        this.color=color;
        this.sizeOfScreen=sizeOfScreen;
        this.weight=weight;
    }

    public double getSizeOfScreen() {
        return sizeOfScreen;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "laptop{"+"price"+price+"color"+color+"size of screen"+sizeOfScreen+
                "weight:"+weight+"}";
    }


    public void add(Laptop laptop) {

    }
}
