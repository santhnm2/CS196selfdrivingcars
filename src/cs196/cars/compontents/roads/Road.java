package cs196.cars.compontents.roads;

import cs196.cars.compontents.Car;
import cs196.cars.compontents.Directions;
import cs196.cars.compontents.Tile;

/**
 * @author arshsab
 * @since 10 2014
 */

public class Road extends Tile {
    private final int speedLimit, dir;
    private final Car[] occupancy;


    public Road(int dir) {
        this.speedLimit = 0;
        this.dir = dir;
        this.occupancy = new Car[4];
    }
}
