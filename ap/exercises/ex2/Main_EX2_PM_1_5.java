package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_1_5 {
    public static void main(String[] args) {
        System.out.println("please enter k:");
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int size = k + 2;
        char[][] array = new char[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    array[i][j] = '*';
                } else {
                    array[i][j] = ' ';
                }
            }
        }
        int x = 1;
        int y = 8;
        array[x][y] = 'X';
        printMatrix(array);
        int moveCount = 0;
        int maxMoves = 3;
        while (moveCount < maxMoves) {
            int direction = random.nextInt(4);
            System.out.println("Direction:" + direction +  "(0:up, 1:right, 2:down, 3:left)");
            System.out.println("the random direction is:" + direction);
            int newX = x;
            int newY = y;
            printMatrix(array);
            switch (direction) {
                case 0:
                    newX--;
                    break;
                case 1:
                    newY++;
                    break;
                case 2:
                    newX++;
                    break;
                case 3:
                    newY--;
                    break;
            }
            moveCount++;
            try {
                Thread.sleep(1000);
            } catch (Exception _) {
            }
            if (array[newX][newY] == '*') {
                System.out.println("hitting the game wall. watch out:) ");
            } else {
                array[x][y] = ' ';
                x = newX;
                y = newY;
                array[x][y] = 'X';
            }
        }
    }

    private static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
