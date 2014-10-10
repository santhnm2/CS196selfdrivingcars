package cs196.cars.components;

public class Road extends Tile {
    private final int speedLimit, dir;
    private final Car[] occupancy;


    public Road(int dir) {
        this.speedLimit = 0;
        this.dir = dir;
        this.occupancy = new Car[4];
    }

    @Override
    public String toString() {
        String s = "";

        switch (dir) {
            case Directions.UP:
                s += "U";
                break;
            case Directions.DOWN:
                s += "D";
                break;
            case Directions.RIGHT:
                s += "R";
                break;
            case Directions.LEFT:
                s += "L";
                break;
        }

        return s + "R";
    }
}
