package ap.exercises.ex2;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_3 {
    private static final String SAVE_FILE = "game_save.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (new File(SAVE_FILE).exists()) {
            System.out.print("u have a saved game. Load it?! ");
            char choice = scanner.next().charAt(0);
            if (choice == 'y' || choice == 'Y') {
                try {
                    GameState savedState = loadGame();
                    playGame(savedState, scanner);
                    return;
                } catch (Exception e) {
                    System.out.println("Error loading saved game");
                }
            }
        }
        System.out.print("Enter k:");
        int k = scanner.nextInt();

        char[][] ground = new char[k + 2][k + 2];
        initializeGround(ground, k);

        int maxDots = k * k;
        int dotCount;

        do {
            System.out.print("Enter number of dots: ");
            dotCount = scanner.nextInt();
            if (dotCount > maxDots) {
                System.out.println("decrease the num od dots.");
            }
        } while (dotCount > maxDots);

        placeDots(ground, k, dotCount);

        GameState initialState = new GameState();
        initialState.ground = ground;
        initialState.playerX = 1;
        initialState.playerY = 1;
        initialState.score = 0;
        initialState.dotCount = dotCount;
        initialState.k = k;
        initialState.startTime = System.currentTimeMillis();

        ground[initialState.playerX][initialState.playerY] = 'X';
        printGround(ground);

        playGame(initialState, scanner);
    }

    private static void playGame(GameState state, Scanner scanner) {
        while (true) {
            System.out.print("Move (w=up, s=down, a=left, d=right, q=quit, save=save game): ");
            String input = scanner.next();

            if (input.equals("q")) {
                System.out.println("Game quit. Final score: " + state.score);
                break;
            }

            if (input.equals("save")) {
                saveGame(state);
                continue;
            }

            char direction = input.charAt(0);
            int newX = state.playerX;
            int newY = state.playerY;

            switch (direction) {
                case 'w':
                    newX--;
                    break;
                case 's':
                    newX++;
                    break;
                case 'd':
                    newY++;
                    break;
                case 'a':
                    newY--;
                    break;
                default:
                    continue;
            }

            if (state.ground[newX][newY] == '*') {
                System.out.println("Hitting the game wall.watch out:)) ");
                continue;
            }

            if (state.ground[newX][newY] == '.') {
                state.score++;
                System.out.println("Score: " + state.score);

                if (state.score == state.dotCount) {
                    long endTime = System.currentTimeMillis();
                    double gameTime = (endTime - state.startTime) / 1000.0;
                    System.out.println("All dots collected!");
                    System.out.println("Final score is: " + state.score);
                    System.out.println("game's total time: " + gameTime + " seconds");
                    new File(SAVE_FILE).delete();
                    return;
                }
            }

            state.ground[state.playerX][state.playerY] = ' ';
            state.playerX = newX;
            state.playerY = newY;
            state.ground[state.playerX][state.playerY] = 'X';

            printGround(state.ground);
        }
    }

    private static void saveGame(GameState state) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(state.k);
            writer.println(state.dotCount);
            writer.println(state.score);
            writer.println(state.playerX);
            writer.println(state.playerY);
            writer.println(state.startTime);

            for (int i = 0; i < state.ground.length; i++) {
                writer.println(new String(state.ground[i]));
            }

            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game");
        }
    }

    private static GameState loadGame() throws IOException {
        GameState state = new GameState();
        BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE));

        state.k = Integer.parseInt(reader.readLine());
        state.dotCount = Integer.parseInt(reader.readLine());
        state.score = Integer.parseInt(reader.readLine());
        state.playerX = Integer.parseInt(reader.readLine());
        state.playerY = Integer.parseInt(reader.readLine());
        state.startTime = Long.parseLong(reader.readLine());

        state.ground = new char[state.k + 2][state.k + 2];
        for (int i = 0; i < state.ground.length; i++) {
            String line = reader.readLine();
            state.ground[i] = line.toCharArray();
        }

        reader.close();
        System.out.println("Game loaded successfully!");
        printGround(state.ground);
        System.out.println("ur current score: " + state.score);

        return state;
    }

    private static void initializeGround(char[][] ground, int k) {
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                if (i == 0 ||i == k + 1|| j == 0 || j == k + 1){
                    ground[i][j] = '*';
                } else{
                    ground[i][j] = ' ';
                }
            }
        }
    }

    private static void placeDots(char[][] ground, int k, int dotCount) {
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

    private static void printGround(char[][] ground) {
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                System.out.print(ground[i][j]);
            }
            System.out.println();
        }
    }

    static class GameState {
        char[][] ground;
        int playerX;
        int playerY;
        int score;
        int dotCount;
        int k;
        long startTime;
    }
}
