package ap.exercises.ex4;

public class Employee {
    private String name;
    private double salary;
    public Employee(String employeeName,double salary){
        this.name=employeeName;
        this.salary=salary;
    }
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }

    public void raiseSalary(double percent) {
        if(percent>0){
            double raise=(salary*percent)/100;
            salary+=raise;
        }
    }
}
