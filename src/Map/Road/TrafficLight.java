package Map.Road;

import Map.*;

public class TrafficLight extends Road {
	private int dir;
	private boolean isRed;

	public TrafficLight(int x, int y, int dir, boolean isRed) {
        super(x, y, 0, 0); //Temporary Speed and Direction are 0 and 0
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
		if(hasCar) return "::";
		else if(isRed) return "RR";
		else return "GG";
	}

    public boolean isRed() {
        return isRed;
    }
}
