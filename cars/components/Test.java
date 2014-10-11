package cs196.cars.components;

public class Test {
    public static void main(String[] args) {
    	RandomMapGenerator j = new RandomMapGenerator(20, 20, 100);
    	Map x = j.generateMap();
    	System.out.print(x.toString());
    }
}
