package ap.exercises.ex4;

public class EmployeeTester {
    public static void main(String[] args) {
        Employee harry=new Employee("hacker,hary",50000);
        System.out.println("employee name:"+harry.getName());
        System.out.println("current salary: $"+harry.getSalary());
        System.out.println();
        System.out.println("giving him a 10% raise.");
        harry.raiseSalary(10);
        System.out.println("new salary: $"+harry.getSalary());
        System.out.println("---------------------------------------");
        Employee lisa=new Employee("smith,lisa",75000);
        System.out.println("new employee added.");
        System.out.println("name:"+lisa.getName());
        System.out.println("salary: $"+lisa.getSalary());
        System.out.println("giving her a 15% raise.");
        lisa.raiseSalary(15);
        System.out.println("lisa's new salary: $"+lisa.getSalary());

    }
}
