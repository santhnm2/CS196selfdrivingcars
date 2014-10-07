package cs196.cars.maps;

/**
 * @author arshsab
 * @since 10 2014
 */

public class Test {
    public static void main(String[] args) {
        RandomMapGenerator mapGen = new RandomMapGenerator(100, 0, 10);

        Map m = mapGen.generateMap();

        System.out.println(m);
    }
}
