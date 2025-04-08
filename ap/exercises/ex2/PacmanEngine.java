package ap.exercises.ex2;
import java.util.Random;
import java.util.Stack;

public class PacmanEngine {
    private char[][] ground;
    private int k;
    private int playerX;
    private int playerY;
    private int score;
    private long startTime;
    private int dotCount;
    private Stack<char[][]> history;
    private Stack<Integer> scoreHistory;

    public PacmanEngine(int k, int dotCount) {
        this.k = k;
        this.dotCount = Math.min(dotCount, k * k);
        this.ground = new char[k + 2][k + 2];
        this.history = new Stack<>();
        this.scoreHistory = new Stack<>();

        printGround();
        placeDots();

        this.playerX = 1;
        this.playerY = 1;
        this.ground[playerX][playerY] = 'X';
        this.score = 0;
        this.startTime = System.currentTimeMillis();
    }

    private void printGround() {
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                if (i == 0 || i == k + 1 || j == 0 || j == k + 1) {
                    ground[i][j] = '*';
                } else {
                    ground[i][j] = ' ';
                }
            }
        }
    }

    private void placeDots() {
        Random random = new Random();
        int dotsPlaced = 0;

        while (dotsPlaced < dotCount) {
            int x = random.nextInt(k) + 1;
            int y = random.nextInt(k) + 1;

            if (ground[x][y] == ' ') {
                ground[x][y] = '.';
                dotsPlaced++;
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                System.out.print(ground[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printScore() {
        System.out.println("Score: " + score);
    }

    public void printRemainTime() {
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - startTime) / 1000.0;
        System.out.println("Time: " + elapsedTime + " seconds");
    }

    public void move(int direction) {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
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
        }

        if (ground[newX][newY] == '*') {
            System.out.println("Hit the wall!");
            return;
        }

        if (ground[newX][newY] == '.') {
            score++;
            System.out.println("Collected a dot! Score: " + score);

            if (score == dotCount) {
                long endTime = System.currentTimeMillis();
                double gameTime = (endTime - startTime) / 1000.0;
                System.out.println("All dots collected!");
                System.out.println("Final score: " + score);
                System.out.println("Total time: " + gameTime + " seconds");
                System.exit(0);
            }
        }

        ground[playerX][playerY] = ' ';
        playerX = newX;
        playerY = newY;
        ground[playerX][playerY] = 'X';
    }

    public void save() {
        char[][] copy = new char[ground.length][ground[0].length];
        for (int i = 0; i < ground.length; i++) {
            System.arraycopy(ground[i], 0, copy[i], 0, ground[i].length);
        }
        history.push(copy);
        scoreHistory.push(score);
    }
}
