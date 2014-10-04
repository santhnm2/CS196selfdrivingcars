package cs196.cars.maps;

import cs196.cars.compontents.Car;
import cs196.cars.compontents.Tile;

/**
 * @author arshsab
 * @since 10 2014
 */

public class Map {
    private final Tile[][] grid;
    private final Car[] cars;

    public Map(Tile[][] grid, Car[] cars) {
        this.grid = grid;
        this.cars = cars;
    }

    public Tile get(int x, int y) {
        return this.grid[x][y];
    }

    public Tile[][] getTiles() {
        return this.grid;
    }

    public Car[] getCars() {
        return this.cars;
    }
}
