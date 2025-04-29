package ap.exercises.ex4;

public class HallLight {
    private boolean switch1;
    private boolean switch2;
    private boolean state;
    public HallLight(){
        switch1=false;
        switch2=false;
        state=false;
    }
    public boolean getSwitch1(){
        return switch1;
    }
    public boolean getSwitch2(){
        return  switch2;
    }
    public boolean getState(){
        return  state;
    }

    public void toggleSwitch1(){
        switch1=!switch1;
        updateLampState();
    }
    public void toggleSwitch2(){
        switch2 =!switch2;
        updateLampState();
    }
    private  void updateLampState(){
        state=switch1!=switch2;
    }

    public static void main(String[] args) {
        HallLight light=new HallLight();
        System.out.println("state:");
        System.out.println("switch 1: "+light.getSwitch1());
        System.out.println("switch 2: "+light.getSwitch2());
        System.out.println("lamp: "+light.getState());
        System.out.println("-----------------------------------------");
        System.out.println("toggle first switch:");
        light.toggleSwitch1();
        System.out.println("switch 1: "+light.getSwitch1());
        System.out.println("switch 2: "+light.getSwitch2());
        System.out.println("lamp: "+light.getState());
        System.out.println("-----------------------------------------");
        System.out.println("toggle second switch:");
        light.toggleSwitch2();
        System.out.println("switch 1: "+light.getSwitch1());
        System.out.println("switch 2: "+light.getSwitch2());
        System.out.println("lamp: "+light.getState());
        System.out.println("-----------------------------------------");
        System.out.println("toggle first switch again:");
        light.toggleSwitch1();
        System.out.println("switch 1: "+light.getSwitch1());
        System.out.println("switch 2: "+light.getSwitch2());
        System.out.println("lamp: "+light.getState());
    }
}
