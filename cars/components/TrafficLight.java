package cs196.cars.components;

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
