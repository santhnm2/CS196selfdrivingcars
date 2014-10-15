package Map.Road;

import Map.*;

public class TrafficLight extends Tile {
	private int dir;
	private boolean isRed;

	public TrafficLight(int x, int y, int dir, boolean isRed) {
        super(x, y);
		this.dir = dir;
		this.isRed = isRed;
	}
	
	public boolean getStatus() {
		return isRed;
	}

	public void toggle() {
		isRed = !isRed;
	}

    public String toString() {
		return "TL";
	}

    public boolean isRed() {
        return isRed;
    }
}
