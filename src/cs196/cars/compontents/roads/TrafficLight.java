package cs196.cars.compontents.roads;

import cs196.cars.compontents.Directions;
import cs196.cars.compontents.roads.Road;

/**
 * @author arshsab
 * @since 10 2014
 */

public class TrafficLight extends Road {

    public TrafficLight() {
        super(Directions.UP);
    }

    public boolean canMoveLeft() {
        throw new RuntimeException();
    }

    public boolean canMoveRight() {
        throw new RuntimeException();
    }

    public boolean canMoveUp() {
        throw new RuntimeException();
    }

    public boolean canMoveDown() {
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return "TL";
    }
}
