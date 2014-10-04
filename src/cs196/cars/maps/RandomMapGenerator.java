package cs196.cars.maps;

import cs196.cars.compontents.Directions;
import cs196.cars.compontents.Tile;
import cs196.cars.compontents.nonroads.NonRoad;
import cs196.cars.compontents.nonroads.Office;
import cs196.cars.compontents.roads.Road;
import cs196.cars.compontents.roads.TrafficLight;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author arshsab
 * @since 10 2014
 */

public class RandomMapGenerator implements MapGenerator {
    private final int length, cars, lights;

    public RandomMapGenerator(int length, int cars, int lights) {
        this.length = length;
        this.cars = cars;
        this.lights = lights;
    }

    @Override
    public Map generateMap() {
        Tile[][] tiles = new Tile[length][length];

        Random r = new Random();
        Set<Coord> lightPos = new HashSet<>();
        for (int i = 0; i < lights; i++) {
            Coord c;

            do {
                c = new Coord(r.nextInt((length - 1) / 2), r.nextInt((length - 1) / 2));
            } while(lightPos.contains(c));

            lightPos.add(c);
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                tiles[i][j] = new Office();
            }
        }

        for (Coord c : lightPos) {
            int actualX = c.x * 2;
            int actualY = c.y * 2;

            for (int i = 0; i < length; i++) {
                if (tiles[actualX][i] instanceof NonRoad) {
                    tiles[actualX][i] = new Road(Directions.DOWN);
                }

                if (tiles[actualX + 1][i] instanceof NonRoad) {
                    tiles[actualX + 1][i] = new Road(Directions.UP);
                }

                if (tiles[i][actualY] instanceof NonRoad) {
                    tiles[i][actualY] = new Road(Directions.RIGHT);
                }

                if (tiles[i][actualY + 1] instanceof NonRoad) {
                    tiles[i][actualY + 1] = new Road(Directions.LEFT);
                }
            }

            tiles[actualX][actualY] = new TrafficLight();
            tiles[actualX + 1][actualY] = new TrafficLight();
            tiles[actualX][actualY + 1] = new TrafficLight();
            tiles[actualX + 1][actualY + 1] = new TrafficLight();
        }

        return new Map(tiles);
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
