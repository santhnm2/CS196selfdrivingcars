package Optimization;

import Car.Car;
import Map.Map;
import java.util.ArrayList;
public class Dijkstra {
	//take in a converted map, take in the coordinates of a starting point and a destination, and add those two nodes to the converted map.
	//find the shortest path between those two points. Return an arraylist with the directions.
	public static ArrayList<Integer> findPath(Map map, graphNode[] graph, Car car){
		ArrayList<Integer> path = new ArrayList<Integer>();
		MapConverter.addNode(graph, car.getXPos(), car.getYPos(), map);
		MapConverter.addNode(graph, car.getDestX(), car.getDestY(), map);
		
		
		
		return path;
		
	}
	
}