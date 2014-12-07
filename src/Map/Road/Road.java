package Map.Road;

import Map.*;
import Car.*;

public class Road extends Tile
{
	private final int speedLimit;
	protected final int dir;
	private int max;
	protected Car[] occupants;
	protected boolean hasCar=false;
	
	public Road(int x, int y, int speed, int direction, int lanes) {
		super(x, y, 0);
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
	public boolean carIncrement(Car c){
		for(int i = 0; i<occupants.length; i++)
		{
			if(occupants[i] == null) {
				occupants[i] = c;
				hasCar = true;
				return true;
			}
		}
		return false;
	}
	
	public boolean carDecrement(Car c){
		for(int i = 0; i<occupants.length; i++)
		{
			if(occupants[i]!=null && occupants[i].equals(c)) {
				occupants[i] = null;
				for(Car car:occupants) {
					if(car!=null) {
						hasCar = true;
						break;
					}
					else  {
						hasCar = false;
					}
				}
				return true;
			}
		}
		return false;
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
	public int getFilled() {
		int filled = 0;
		for(int i = 0; i < occupants.length; i++) {
			if(occupants[i] != null)
				filled++;
		}
		return filled;
	}
	public int getMaxOccupants() {
		return occupants.length;
	}
	public String toString() {
		if(hasCar) return getFilled() + "" + getFilled();
		switch(dir){
            case 0: return "UR";
            case 1: return "RR";
            case 2: return "DR";
            case 3: return "LR";
		}

		throw new RuntimeException("Invalid direction; cannot string-ify");
	}
}
