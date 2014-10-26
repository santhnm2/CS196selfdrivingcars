package Map.Road;

import Map.*;
import Car.*;

public class Road extends Tile
{
	private final int speedLimit;
	protected final int dir;
	private int max;
	private Car[] occupants;
	protected boolean hasCar=false;
	
	public Road(int x, int y, int speed, int direction, int lanes) {
		super(x, y);
		speedLimit = speed;
		dir = direction;
		max = lanes;
		occupants = new Car[max];
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
	
	public void carIncrement(Car c){
		for(int i = 0; i<occupants.length; i++)
		{
			if(occupants[i] == null)
				occupants[i] = c;
		}
	}
	
	public void carDecrement(Car c){
		for(int i = 0; i<occupants.length; i++)
		{
			if(occupants[i].equals(c))
				occupants[i] = null;
		}
	}
	
	public int getRemaining(){
		int empty = 0;
		for(int i = 0; i<occupants.length; i++)
		{
			if(occupants[i] == null)
				empty++;
		}
		return empty;
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
