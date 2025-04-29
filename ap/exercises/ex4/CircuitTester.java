package ap.exercises.ex4;

public class CircuitTester {
    public static void main(String[] args) {
        HallLight circuit= new HallLight();
        System.out.println("testing all switch combinations:");
        System.out.println("---------------------------------");
        testCombination(circuit,false,false,"both down");
        circuit.toggleSwitch1();
        testCombination(circuit,true,false,"first up, second down");
        circuit.toggleSwitch2();
        testCombination(circuit,true,true,"both up");
        circuit.toggleSwitch1();
        testCombination(circuit,false,true,"first down, second up");
    }
    private static void testCombination(HallLight circuit,boolean expected1,boolean expected2,String description){
        boolean actual1=circuit.getSwitch1();
        boolean actual2=circuit.getSwitch2();
        boolean actualLight=circuit.getState();
        boolean expectedLamp=expected1!=expected2;
        System.out.println("Test:"+description);
        System.out.println("switch1 actual: "+actual1+ " expected:"+expected1);
        System.out.println("switch2 actual: "+expected2+" expected:"+expected2);
        System.out.println("light_actual: "+actualLight+" expected:"+expectedLamp);
        if(actual1==expected1 && actual2==expected2 && actualLight==expectedLamp){
            System.out.println("result: passed");
        }else System.out.println("result:failed");
    }
}
