package ap.exercises.ex4;

public class LetterPrinter {
    public static void main(String[] args) {
        Letter letter=new Letter("marry","jhon");
        letter.addLine("i am sorry we must part.");
        letter.addLine("i wish you all the best.");
        System.out.println(letter.getText());
    }
}
