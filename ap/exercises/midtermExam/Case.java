package ap.exercises.midtermExam;

public class Case {

    private String caseType;
    private String model;
    private String color;
    private double price;
    public Case(double price,String color,String caseType,String model){
        this.price=price;
        this.color=color;
        this.caseType=caseType;
        this.model=model;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "case{"+"price"+price+"color"+color+"type of case: "+caseType+
                "case model: "+model+"}";
    }


    public void add(Case cases) {
    }
}
