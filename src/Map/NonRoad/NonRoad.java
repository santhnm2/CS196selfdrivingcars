package Map.NonRoad;

import Map.*;

public class NonRoad extends Tile {
    private final double density;

    public NonRoad(int x, int y, double density) {
        super(x, y);

        this.density = density;
    }
}
