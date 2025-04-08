package ap.exercises.ex2;
import java.util.*;

public class Main_EX2_PM_2_4 {

    public static void main(String[] args) {

        int k=9;
        int c=15;

        Random rnd = new Random();

        PacmanEngine pacmanEngine = new PacmanEngine(k,c);

        while(true) {
            pacmanEngine.printMatrix();
            pacmanEngine.printScore();
            pacmanEngine.printRemainTime();

            try {
                Thread.sleep(5000);
            } catch (Exception e) {}

            int direction=rnd.nextInt(4);
            pacmanEngine.move(direction);
            pacmanEngine.save();
        }

    }
}


