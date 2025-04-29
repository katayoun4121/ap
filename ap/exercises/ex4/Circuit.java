package ap.exercises.ex4;

public class Circuit {
    private int switch1;
    private int switch2;
    public Circuit(){
        switch1=0;
        switch2=0;
    }
    public int getSwitchSate(int switchNum){
        if(switchNum==1){
            return  switch1;
        } else if (switchNum == 2) {
            return  switch2;
        }else {throw new IllegalArgumentException("INVALID NUMBER");}
    }
    public int getLightState(){
        return (switch1!=switch2)?1:0;
    }
    public void toggleSwitch(int switchNum){
        if (switchNum==1){
            switch1=1-switch1;
        } else if (switchNum == 2) {
            switch2=1-switch2;
        }else {
            throw  new IllegalArgumentException("invalid number");
        }
    }
}
