package ap.exercises.FinalExam;

public class Pen extends Product{
    private String color;
    public Pen(String name, double price, String color) {
        super(name, price);
        if (color.equals("green") || color.equals("blue") ||
                color.equals("red") || color.equals("black")) {
            this.color = color;
        } else {
            System.out.println("color is invalid for you");
        }

    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        if (color.equals("green") || color.equals("blue") ||
                color.equals("red") || color.equals("black")) {
            this.color = color;
        }else {
            System.out.println("invalid color");
        }
    }
        @Override
        public String toString () {
            return " pen " + getName() + "  price" + getPrice() + "  color " + color;
        }

    }
