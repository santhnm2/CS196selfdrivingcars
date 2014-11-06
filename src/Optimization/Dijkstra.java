package Optimization;

import Car.Car;
import Map.Map;
import java.util.ArrayList;
public class Dijkstra {
   //take in a converted map, take in the coordinates of a starting point and a destination, and add those two nodes to the converted map.
   //find the shortest path between those two points. Return an arraylist with the directions.
   public static ArrayList<Integer> findPath(Map map, graphNode[] graph, Car car){
      ArrayList<Integer> path = new ArrayList<Integer>();
      int[][] completed = new int[2][0];
      MapConverter.addNode(graph, car.getXPos(), car.getYPos(), map);
      MapConverter.addNode(graph, car.getDestX(), car.getDestY(), map);
      //put start position in priority queue with 'weight' of 0
      PriorityQueue pq = new PriorityQueue();
      pq.add(0, MapConverter.nodeIndex(graph, car.getXPos(), car.getYPos()), -1);
      boolean destination = false;
      while(!destination) {
         //heap the queue
         pq.heapMyArray();
         //pop the top 
         int[] current = pq.remove();
         if (finalDestOneDirection(graph[current[1]].getX(), car.getDestX()) && finalDestOneDirection(graph[current[1]].getY(), car.getDestY()))
            destination = true;
         else
            relax(current, graph, pq);
         completedItem(completed, current);
      }     
      
      //repeat until you pop the destination
      toArrayList(path, completed, completed[0].length, graph);
      return path;
   }

   public static void toArrayList(ArrayList<Integer> path, int[][] arr, int index, graphNode[] graph){
      //converts an array of connected items to an an ArrayList.      
      if (arr[1][index] != -1)
         toArrayList(path, arr, arr[1][index], graph);
      else
         return;
     moveInDirection(graph[arr[1][index]], graph[arr[0][index]], path);
     
         
      }
   
	public static void relax(int[] current, graphNode[] graph, PriorityQueue pq) {
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
   public static void completedItem(int[][] completed, int[] current){
      int[][] temp = new int[0][completed[0].length+1];
      for (int i = 0; i< completed[0].length; i++){
         temp[0][i] = completed[0][i];
         temp[1][i] = completed[1][i];
      }
      temp[0][completed[0].length] = current[1];
      temp[1][completed[0].length] = current[2];      
   }
   public static Boolean finalDestOneDirection(int start, int end) {
      if (start == end || start + 1 == end || start - 1 == end)
         return true;
      return false;
   }

}
	

