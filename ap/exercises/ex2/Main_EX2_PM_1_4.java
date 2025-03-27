package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter on of the following keys:(w,a,s,d,q)");
        while (true){
            System.out.println("enter tha character:");
            String input = scanner.nextLine().toLowerCase();
            if (input.isEmpty()){
                continue;
            }
            char m = input.charAt(0);
            switch (m){
                case 'w':
                    System.out.println("up");
                    break;
                case 'a':
                    System.out.println("left");
                    break;
                case 's':
                    System.out.println("down");
                    break;
                case 'd':
                    System.out.println("right");
                    break;
                case 'q':
                    System.out.println("EXIT");
                    break;
                default:
                    System.out.println("WARNING");
            }
            scanner.close();
            System.exit(0);

        }
    }
}
