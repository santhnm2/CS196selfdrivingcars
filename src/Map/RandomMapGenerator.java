package Map;

import Map.NonRoad.House;
import Car.Car;

/**
 * @author arshsab
 * @since 10 2014
 */

public class RandomMapGenerator implements MapGenerator {
    private final Tile[][] grid;

    public RandomMapGenerator(Tile[][] grid) {
        this.grid = grid;
    }

    @Override
    public Map generateMap() {
        throw new RuntimeException("Todo.");
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

    public static void main(String[] args) {
        // todo
    }
}
