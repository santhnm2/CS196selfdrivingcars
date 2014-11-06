package Optimization;

import Car.Car;
import Constants.Directions;
import Map.Map;
import java.util.ArrayList;
public class Dijkstra {
	//take in a converted map, take in the coordinates of a starting point and a destination, and add those two nodes to the converted map.
	//find the shortest path between those two points. Return an arraylist with the directions.
	public static ArrayList<Integer> findPath(Map map, graphNode[] graph, Car car){
		ArrayList<Integer> path = new ArrayList<Integer>();
		MapConverter.addNode(graph, car.getXPos(), car.getYPos(), map);
		MapConverter.addNode(graph, car.getDestX(), car.getDestY(), map);
		//put start position in priority queue with 'weight' of 0
		PriorityQueue shortest = new PriorityQueue();
		shortest.add(0, MapConverter.nodeIndex(graph, car.getXPos(), car.getYPos()), -1);
			//heap the queue
			//pop the top 
			//for each of the top's children, weight becomes weight of top + their weight and parent index is top
		
		//repeat until you pop the destination
		
		return path;
		
	}
	
	public void relax(int[] current, graphNode[] graph, PriorityQueue pq) {
		int pos = current[1];
		int weight = current[0];
		
		//Adding all the new weights for all four directions to the priority queue
		if(graph[pos].getUp() != null) {
			weight += graph[pos].getY() - graph[pos].getUp().getY();
			pq.add(weight, graph[pos].getUp().getIndex(), pos);
		}
		if(graph[pos].getRight() != null) {
			weight += graph[pos].getRight().getX() - graph[pos].getX();
			pq.add(weight, graph[pos].getRight().getIndex(), pos);
		}
		if(graph[pos].getDown() != null) {
			weight += graph[pos].getDown().getY() - graph[pos].getY();
			pq.add(weight, graph[pos].getDown().getIndex(), pos);
		}
		if(graph[pos].getLeft() != null) {
			weight += graph[pos].getX() - graph[pos].getLeft().getX();
			pq.add(weight, graph[pos].getLeft().getIndex(), pos);
		}
	}
	
}
