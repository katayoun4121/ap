package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_2_1 {
    public static void main(String[] args) {
        System.out.println("please enter k:");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
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
        int xPos=1;
        int yPos=1;
        array[xPos][yPos]='X';
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(array[i][j] + "");
            }
            System.out.println();
        }
        while (true){
            System.out.println("enter direction: (w:up, d:right, s:down, a:left, q:quit )");
            char input = scanner.next().charAt(0);
            if (input=='q'){
                System.out.println("$ BYE._.BYE $");
                break;
            }
            int newX=xPos;
            int newY=yPos;
            switch (input){
                case 'w':
                    newX =xPos-1;  break;
                case 'd':
                    newY =yPos+1;  break;
                case 's':
                    newX = xPos+1; break;
                case 'a':
                    newY = yPos-1;  break;
            }
            if (array[newX][newY]=='*'){
                System.out.println("hitting the game wall. watch out:)) ");
            }else{array[xPos][yPos]= ' ';
                xPos=newX;
                yPos=newY;
                array[xPos][yPos]= 'X';
                for (int i=0;i<size;i++){
                    for (int j=0;j<size;j++){
                        System.out.print(array[i][j] + "");
                    }
                    System.out.println();
                }
            }

        }

    }
}
