package Map.NonRoad;

import Map.*;

public class NonRoad extends Tile {
    public final double density;

    public NonRoad(int x, int y, double density) {
        super(x, y, density);
        this.density = density;
    }

}
