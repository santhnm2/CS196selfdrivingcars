package cs196.cars.maps;

import cs196.cars.components.Car;
import cs196.cars.components.Tile;
import cs196.cars.components.nonroads.Office;

/**
 * @author arshsab
 * @since 10 2014
 */

public class RandomMapGenerator implements MapGenerator {
    private static final double DENSITY = 0.75;

    private final int length, cars, lights;

    public RandomMapGenerator(int length, int cars, int lights) {
        this.length = length;
        this.cars = cars;
        this.lights = lights;
    }

    @Override
    public Map generateMap() {
        Tile[][] grid = new Tile[length][length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Office();
            }
        }

        Map m = new Map(grid, new Car[0]);

        for(int i = 0; i < length; i += 4){
            if(Math.random() < DENSITY){
                m.createHorizontalRoad(i, 2, (int) (Math.random() * length) / 2 * 2, length - 1);
            }
        }

        for(int j = 0; j < length; j += 4){
            if(Math.random() < DENSITY)
                m.createVerticalRoad(j, 2, (int) (Math.random() * length) / 2 * 2, length - 1);
        }

        return m;
    }


    private class Coord {
        final int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x << 15 + y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != getClass()) {
                return false;
            }

            Coord other = (Coord) o;

            return this.x == other.x && this.y == other.y;
        }
    }
}
