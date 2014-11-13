package Map.Road;

import java.util.ArrayList;

import Constants.Directions;
import Map.Map;
import Map.Tile;

public class Intersection {
	
	private final ArrayList<TrafficLight> lights;
	private ArrayList<Road> inputRoads = new ArrayList<Road>();
	private Map map;
	
	private int timeSinceToggle; //To be implemented
	
	public Intersection(ArrayList<TrafficLight> lights, Map m) {
		map = m;
        this.lights = lights;
        lights.get(0).setRed(true);
        lights.get(1).setRed(false);
        lights.get(2).setRed(true);
        if(this.lights.size() == 4)
        {
        	lights.get(3).setRed(false);
        }

        findInputRoads();
	}
    public void toggle() {
		for (TrafficLight light : lights) {
			light.toggle();
		}
	}
    public boolean shouldToggle() {
    	ArrayList<TrafficLight> greenLights = new ArrayList<TrafficLight>();
    	ArrayList<TrafficLight> redLights = new ArrayList<TrafficLight>();
    	for(TrafficLight light:lights) {
    		if(light.isRed()) redLights.add(light);
    		else greenLights.add(light);
    	}
    	//Find average traffic density
    	double greenDensity = 0;
    	for(TrafficLight light : greenLights) {
    		greenDensity += light.getTrafficDensity();
    	}
    	greenDensity/=greenLights.size();
    	double redDensity = 0;
    	for(TrafficLight light : redLights) {
    		redDensity += light.getTrafficDensity();
    	}
    	redDensity/=redLights.size();
    	if(redDensity > greenDensity) return true; //Check timer?
    	return false;
    	
    }
    private void findInputRoads() {
    	//Find the roots around the light
    	for(TrafficLight light:lights) {
    		for(int dir:Directions.dirs) {
    			if(dir!=-1){
    				Tile check = map.getInDir(light, dir);
    				if(check instanceof Road && !(check instanceof TrafficLight)) {
    					if(map.getInDir(check, ((Road) check).getDirection()).equals(light)) {
    						inputRoads.add((Road) check);
    						light.setTraffic(traceBack((Road)check));
    					}
    				}
    			}
    		}
    	}
    	//Find the rest of the road back to the last light or beginning of road
    }
    private ArrayList<Road> traceBack(Road r) {
    	ArrayList<Road> result = new ArrayList<Road>();
		int oppositeDir = r.getDirection()-2;
		if(oppositeDir<0) oppositeDir+=4;
		Road curRoad = r;
		while(map.getInDir(curRoad, oppositeDir) instanceof Road && !(map.getInDir(curRoad, oppositeDir) instanceof TrafficLight)) {
			
			curRoad = (Road)map.getInDir(curRoad, oppositeDir);
			result.add(curRoad);
		}
		return result;
    }
}