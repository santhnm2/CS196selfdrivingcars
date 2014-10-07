package cs196.cars.components.maps;

import cs196.cars.components.Tile;
import cs196.cars.components.roads.TrafficLight;
import cs196.cars.maps.Map;
import cs196.cars.maps.RandomMapGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author arshsab
 * @since 10 2014
 */

public class TestMapGenerator {
    @Test
    public void testMapSizes() {
        for (int i = 50; i < 75; i++) {
            RandomMapGenerator gen = new RandomMapGenerator(i, 10, (i - 50) / 4);
            Map map = gen.generateMap();


            int count = 0;
            for (Tile[] row : map.getTiles()) {
                for (Tile tile : row) {
                    if (tile instanceof TrafficLight) {
                        count++;
                    }
                }
            }

            assertEquals(map.getCars().length, 10);
            assertEquals(count, ((i - 50) / 4) * 4);
            assertEquals(map.getTiles().length, i);
            assertEquals(map.getCars().length, 10);
        }

    }

}
