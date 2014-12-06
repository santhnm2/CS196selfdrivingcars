package Map.Road;

import java.util.ArrayList;

import Constants.Directions;

public class TrafficLight extends Road {
	private boolean isRed;
	private ArrayList<Road> traffic;
	
	public TrafficLight(int x, int y, boolean isRed) {
        //ASSUME THAT TRAFFIC LIGHTS ALWAYS HAVE 2 LANES BECAUSE I SAID SO
		super(x, y, 1, Directions.NO_DIR, 2); //Temporary Speed and Direction are 1 and -1
		this.isRed = isRed;
	}
	
	public void setRed(boolean b){
		isRed = b;
	}

	public void toggle() {
		isRed = !isRed;
	}

    public String toString() {
		if(hasCar) return getFilled()+""+getFilled();
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
    	if(traffic==null)
    		return 0;
    	for(Road r:traffic) {
    		
    		total += 2-r.getRemaining(); //get number of cars in the input roads
    	}
    	int len = traffic.size();
    	return (double)(total)/len;
    }
}
