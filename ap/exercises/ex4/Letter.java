package ap.exercises.ex4;

public class Letter {
    private String from;
    private String to;
    private String body;
    public Letter(String from,String to){
        this.from=from;
        this.to=to;
        this.body="";
    }
    public void addLine(String line){
        body=body.concat(line).concat("\n");
    }
    public String getText(){
        String lettertext="dear "+to+":\n\n";
        lettertext=lettertext.concat(body);
        lettertext=lettertext.concat("\n sincerely ,\n\n");
        lettertext=lettertext.concat(from);
        return lettertext;
    }
}
