package Map;

import java.util.ArrayList;

import Map.Road.Intersection;

public class TrafficLightHandler implements java.io.Serializable {
	
	public ArrayList<Intersection> intersections;
	
	public TrafficLightHandler(ArrayList<Intersection> i) {
		intersections = i;
	}
	public void toggleAll() {
		for(Intersection i:intersections) {
			i.toggle();
			
		}
	}
}
