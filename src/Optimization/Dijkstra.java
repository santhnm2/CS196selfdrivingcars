package Optimization;

import Car.Car;
import Map.Map;
import Map.Road.Road;
import Constants.Directions;
import java.util.ArrayList;

/*PROBLEMS:
 *the toArrayList function doesn't create a path which stays in the right lane.
 *this is related to the fact that all there are 4 nodes per intersection.
 *also the fact that edges aren't directional.
 */
public class Dijkstra {
   // take in a converted map, take in the coordinates of a starting point and a
   // destination, and add those two nodes to the converted map.
   // find the shortest path between those two points. Return an arraylist with
   // the directions.
   public static ArrayList<Integer> findPath(Map map, Car car) {
      ArrayList<Integer> path = new ArrayList<Integer>();
      graphNode[] graph = MapConverter.toGraph(map);
      int[][] completed = new int[2][0];
      
      graph = MapConverter.addNode(graph, car.getXPos(), car.getYPos(), map);
      if(((Road)map.get(car.getXPos(), car.getYPos())).getDirection() == Directions.UP) {
         graph[graph.length - 1].setHorizontal(Directions.LEFT);
         graph =  MapConverter.addNode(graph, car.getXPos() - 1, car.getYPos(), map);
      }
      if(((Road)map.get(car.getXPos(), car.getYPos())).getDirection() == Directions.RIGHT) {
         graph[graph.length - 1].setVertical(Directions.UP);
         graph =  MapConverter.addNode(graph, car.getXPos(), car.getYPos() - 1, map);
      }
      if(((Road)map.get(car.getXPos(), car.getYPos())).getDirection() == Directions.DOWN) {
         graph[graph.length - 1].setHorizontal(Directions.RIGHT);
         graph =  MapConverter.addNode(graph, car.getXPos() + 1, car.getYPos(), map);
      }
      if(((Road)map.get(car.getXPos(), car.getYPos())).getDirection() == Directions.LEFT) {
         graph[graph.length - 1].setVertical(Directions.DOWN);
         graph =  MapConverter.addNode(graph, car.getXPos(), car.getYPos() + 1, map);
      }
      
      graph = MapConverter.addNode(graph, car.getDestX(), car.getDestY(), map);
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.UP) {
         graph[graph.length - 1].setHorizontal(Directions.LEFT);
         graph =  MapConverter.addNode(graph, car.getDestX() - 1, car.getDestY(), map);
      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.RIGHT) {
         graph[graph.length - 1].setVertical(Directions.UP);
         graph =  MapConverter.addNode(graph, car.getDestX(), car.getDestY() - 1, map);
      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.DOWN) {
         graph[graph.length - 1].setHorizontal(Directions.RIGHT);
         graph =  MapConverter.addNode(graph, car.getDestX() + 1, car.getDestY(), map);
      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.LEFT) {
         graph[graph.length - 1].setVertical(Directions.DOWN);
         graph =  MapConverter.addNode(graph, car.getDestX(), car.getDestY() + 1, map);
      }
      // put start position in priority queue with 'weight' of 0
      PriorityQueue pq = new PriorityQueue();
      pq.add(0, MapConverter.nodeIndex(graph, car.getXPos(), car.getYPos()), -1);
      boolean destination = false;
      while (!destination) {
         // heap the queue
         pq.heapMyArray();
         // pop the top
         int[] current = pq.remove();
         if (finalDestOneDirection(graph[current[1]].getX(), car.getDestX())
               && finalDestOneDirection(graph[current[1]].getY(), car.getDestY()))
            destination = true;
         else
            relax(current, graph, pq);
         completed = completedItem(completed, current);
      }

      // repeat until you pop the destination
      toArrayList(path, completed, completed[0].length, graph);
      return path;
   }

   public static void toArrayList(ArrayList<Integer> path, int[][] arr, int index, graphNode[] graph) {
      // converts an array of connected items to an an ArrayList.
      // as stated at the top, it doesn't stay in the right lane to get to the
      // next node.
      if (arr[1][index] != -1)
         toArrayList(path, arr, arr[1][index], graph);
      else
         return;
      moveInDirection(graph[arr[1][index]], graph[arr[0][index]], path);

   }

   public static void relax(int[] current, graphNode[] graph, PriorityQueue pq) {
      int pos = current[1];
      int weight = current[0];

      // Adding all the new weights for all four directions to the priority
      // queue
      if (graph[pos].getUp() != null && graph[pos].getVertical() == Directions.UP) {
         int tempWeight = weight + graph[pos].getY() - graph[pos].getUp().getY();
         pq.add(tempWeight, graph[pos].getUp().getIndex(), pos);
      }
      if (graph[pos].getRight() != null && graph[pos].getHorizontal() == Directions.RIGHT) {
         int tempWeight = weight + graph[pos].getRight().getX() - graph[pos].getX();
         pq.add(tempWeight, graph[pos].getRight().getIndex(), pos);
      }
      if (graph[pos].getDown() != null && graph[pos].getVertical() == Directions.DOWN) {
         int tempWeight = weight + graph[pos].getDown().getY() - graph[pos].getY();
         pq.add(tempWeight, graph[pos].getDown().getIndex(), pos);
      }
      if (graph[pos].getLeft() != null && graph[pos].getHorizontal() == Directions.LEFT) {
         int tempWeight = weight + graph[pos].getX() - graph[pos].getLeft().getX();
         pq.add(tempWeight, graph[pos].getLeft().getIndex(), pos);
      }
   }

   public static int[][] completedItem(int[][] completed, int[] current) {
      int[][] temp = new int[2][completed[0].length + 1];
      for (int i = 0; i < completed[0].length; i++) {
         temp[0][i] = completed[0][i];
         temp[1][i] = completed[1][i];
      }
      temp[0][completed[0].length] = current[1];
      temp[1][completed[0].length] = current[2];
      return temp;
   }

   public static Boolean finalDestOneDirection(int start, int end) {
      if (start == end || start + 1 == end || start - 1 == end)
         return true;
      return false;
   }

   // function to add one direction to the list at a time
   public static void moveInDirection(graphNode a, graphNode b, ArrayList<Integer> path) {
      if (a.getX() == b.getX()) {
         if (a.getY() > b.getY()) {
            for (int i = 0; i < a.getY() - b.getY(); i++) {
               path.add(Directions.UP);
            }
         } else {
            for (int i = 0; i < b.getY() - a.getY(); i++) {
               path.add(Directions.DOWN);
            }
         }
      } else {
         if (a.getX() > b.getX()) {
            for (int i = 0; i < a.getX() - b.getX(); i++) {
               path.add(Directions.LEFT);
            }
         } else {
            for (int i = 0; i < b.getX() - a.getX(); i++) {
               path.add(Directions.RIGHT);
            }
         }
      }
   }

}
