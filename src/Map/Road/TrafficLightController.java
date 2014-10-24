package Map.Road;

import java.util.ArrayList;

public class TrafficLightController {
	
	private final ArrayList<TrafficLight> lights;
	
	public TrafficLightController(ArrayList<TrafficLight> lights) {
        this.lights = lights;
        lights.get(0).setRed(true);
        lights.get(1).setRed(false);
        lights.get(2).setRed(true);
        if(this.lights.size() == 4)
        {
        	lights.get(3).setRed(false);
        }
	}

    public void toggle() {
		for (TrafficLight light : lights) {
			light.toggle();
		}
	}
}
