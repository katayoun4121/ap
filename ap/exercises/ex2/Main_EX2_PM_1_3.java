package ap.exercises.ex2;

import java.util.Scanner;

import java.util.Random;

public class Main_EX2_PM_1_3 {
    public static void main(String[] args) {
        System.out.println("please enter k:");
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int size = k + 2;
        char[][] array = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    array[i][j] = '*';
                } else {
                    array[i][j] = ' ';
                }
            }
        }
        printArray(array);

        //part 2 of the third task
        int maxEmpty = k * k;
        int c;
        do {
            System.out.print("enter c ( c<=" + maxEmpty + "):");
            Scanner in = new Scanner(System.in);
            c = in.nextInt();
            if (c>maxEmpty){
                System.out.println("error, c is bigger than maxEmpty.");
            }
        }while (c>maxEmpty);
        Random random = new Random();
        int points =0;
        while (points< c){
            int row = random.nextInt(k)+1;
            int column = random.nextInt(k)+1;
            if (array[row][column]==' '){
                array[row][column] ='.';
                points++;
            }
        }
        printArray(array);
    }
    private static void printArray(char[][] array){
        for (int i=0;i< array.length;i++) {
            for (int j=0;j<array[i].length;j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
