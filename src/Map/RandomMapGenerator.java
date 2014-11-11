package Map;

import Map.NonRoad.House;
import Car.Car;
import Map.Road.Road;
import Map.Road.TrafficLight;

import java.util.ArrayList;

/**
 * @author arshsab
 * @since 10 2014
 */

public class RandomMapGenerator implements MapGenerator {
    private static final double DENSITY = 0.34;

    private final int length;
    private final int cars;

    public RandomMapGenerator(int length, int cars) {
        this.length = length;
        this.cars = cars;
    }

    @Override
    public Map generateMap() {
        Tile[][] grid = new Tile[length][length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new House(i, j);
            }
        }

        Map m = new Map(grid, new ArrayList<Car>());

        for (int t = 0; t < 3; t++) {
            for (int i = 0; i < length; i += 4){
                if(Math.random() < .85){
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;

                    m.createHorizontalRoad(start, i, width, 2, 4);
                }
            }

            for (int i = 0; i < length; i += 4){
                if (Math.random() < .85) {
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;

                    m.createVerticalRoad(i, start, width, 2, 4);
                }
            }
        }

        ArrayList<Road> roads = new ArrayList<Road>();

        for (Tile[] a : grid) {
            for (Tile b : a) {
                if (b instanceof Road && !(b instanceof TrafficLight)) {
                    roads.add((Road) b);
                }
            }
        }

        for (int i = 0; i < cars; i++) {
            Road start, end;

            do {
                start = roads.get((int) (roads.size() * Math.random()));
                end   = roads.get((int) (roads.size() * Math.random()));
            } while (start == end);

            m.placeCar(new Car(start.x, start.y, end.x, end.y, m));
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

    public static void main(String[] args) {
        MapGenerator mapGen = new RandomMapGenerator(30, 1);

        System.out.println(mapGen.generateMap());
    }
}
