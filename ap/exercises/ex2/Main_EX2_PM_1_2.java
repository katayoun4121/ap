package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args){
        System.out.println("please enter k:");
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int size = k+2;
        char[][] array = new char[size][size];
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if (i==0||i==size-1||j==0||j==size-1){
                    array[i][j]='*';
                }
                else{
                    array[i][j]=' ';
                }
            }
        }
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(array[i][j] + "");
            }
            System.out.println();
        }
    }
}