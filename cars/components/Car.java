package cs196.cars.components;

public class Car extends Entity {
    private int dir, speed;
    private int x, y;

    public Car(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile getNextTile() {
        throw new RuntimeException();
    }

    public boolean move() {
        throw new RuntimeException();
    }

    public boolean turn() {
        throw new RuntimeException();
    }

    public boolean exists() {
        throw new RuntimeException();
    }

    public Tile getDestination() {
        throw new RuntimeException();
    }
}
