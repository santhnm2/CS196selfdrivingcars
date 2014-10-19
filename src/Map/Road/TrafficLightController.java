package Map.Road;

import java.util.ArrayList;

public class TrafficLightController {
	
	private final ArrayList<TrafficLight> lights;
	
	public TrafficLightController(ArrayList<TrafficLight> lights) {
        this.lights = lights;
	}

    public void toggle() {
		for (TrafficLight light : lights) {
			light.toggle();
		}
	}
}
