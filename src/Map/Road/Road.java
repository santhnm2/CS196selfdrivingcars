package Map.Road;

import Map.*;
import Car.*;

public class Road extends Tile
{
	private final int speedLimit;
	private final int dir;
	private Car[] occupancy;
	protected boolean hasCar=false;
	
	public Road(int x, int y, int speed, int direction) {
		super(x, y);
		speedLimit = speed;
		dir = direction;
	}
	
	public int getSpeed() {
		return speedLimit;
	}
	
	public int getDirection() {
		return dir;
	}
	public void setCar(boolean b) {
		hasCar = b;
	}

	public String toString() {
		if(hasCar) return "::";
		switch(dir){
            case 0: return "UR";
            case 1: return "RR";
            case 2: return "DR";
            case 3: return "LR";
		}

		throw new RuntimeException("Invalid direction; cannot string-ify");
	}
}
