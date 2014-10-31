package Map.Road;

import java.util.ArrayList;

import Constants.Directions;

public class TrafficLight extends Road {
	private boolean isRed;
	private ArrayList<Road> traffic;
	
	public TrafficLight(int x, int y, boolean isRed) {
        super(x, y, 0, Directions.NO_DIR, 0); //Temporary Speed and Direction are 0 and 0
		this.isRed = isRed;
	}
	
	public void setRed(boolean b){
		isRed = b;
	}

	public void toggle() {
		isRed = !isRed;
	}

    public String toString() {
		if(hasCar) return "::";
		else if(isRed) return "RL";
		else return "GL";
	}

    public boolean isRed() {
        return isRed;
    }
    
    public void setTraffic(ArrayList<Road> l) {
    	traffic = l;
    }
    public double getTrafficDensity() {
    	int total = 0;
    	for(Road r:traffic) {
    		total += r.getNumOccupants();
    	}
    	int len = traffic.size();
    	return (double)(total)/len;
    }
}
