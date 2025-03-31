package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter k:");
        int k = scanner.nextInt();
        char[][] ground = new char[k+2][k+2];
        for (int i=0;i< ground.length;i++){
            for (int j=0;j< ground[i].length;j++){
                if (i==0||i==k+1||j==0||j==k+1){
                    ground[i][j]='*';
                }else {
                    ground[i][j]=' ';
                }
            }
        }
        int maxDots=k*k;
        int dotCount;
        do {
            System.out.println("Enter number of dots(max "+ maxDots+"): ");
            dotCount= scanner.nextInt();
            if (dotCount>maxDots){
                System.out.println("Decrease number of dots. It's reaching the maximum.");
            }
        }while (dotCount >maxDots);
        placeDots(ground,k,dotCount);
        int playerX=1;
        int playerY=1;
        ground[playerX][playerY]='X';
        for (int i=0;i<k+2;i++){
            for (int j=0;j<k+2;j++){
                System.out.print(ground[i][j] + " ");
            }
            System.out.println();
        }
        int score=0;
                long startTime=System.currentTimeMillis();
        while (true){
            System.out.println("Move (w=up, s=down, a=left, d=right, q=quit )");
            char input = scanner.next().charAt(0);
            if (input=='q'){
                System.out.println("quit game. final score: " + score);
                break;
            }
            int newX=playerX;
            int newY=playerY;
            switch (input) {
                case 'w':
                    newX--;
                    break;
                case 'd':
                    newY++;
                    break;
                case 's':
                    newX++;
                    break;
                case 'a':
                    newY--;
                    break;
                default:
                    System.out.println("input is incorrect:)) ");
                    continue;
            }
            if (ground[newX][newY]=='*'){
                System.out.println("hitting the game wall.watch out:)) ");
                continue;
            }
            if (ground[newX][newY]=='.'){
                score++;
                System.out.println("score:"+score);
                if (score==dotCount){
                    long endTime=System.currentTimeMillis();
                    double gameTime=(endTime-startTime)/1000.0;
                    System.out.println("All dots collected.");
                    System.out.println("Final score is : "+score);
                    System.out.println("Game total time : "+ gameTime +"seconds");
                    break;
                }
            }
            ground[playerX][playerY]=' ';
            playerX = newX;
            playerY = newY;
            ground[playerX][playerY]='X';
            for (int i=0;i<k+2;i++){
                for (int j=0;j<k+2;j++){
                    System.out.print(ground[i][j] + " ");
                }
                System.out.println();
            }
        }
        scanner.close();

    }
    private static void placeDots(char[][] ground,int k,int dotCount){
        Random random = new Random();
        int dotsPlaced = 0;
        while ( dotsPlaced<dotCount ){
            int x= random.nextInt(k)+1;
            int y = random.nextInt(k)+1;
            if (ground[x][y]==' '){
                ground[x][y]='.';
                dotsPlaced++;
            }
        }
    }
}
