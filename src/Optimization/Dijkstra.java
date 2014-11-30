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
   public static ArrayList<Integer> findPath(Map map, Car car, boolean useSpeed) {
      ArrayList<Integer> path = new ArrayList<Integer>();
      graphNode[] graph = MapConverter.toGraph(map);
      int[][] completed = new int[2][0];
      
      graph = MapConverter.addNode(graph, car.getXPos(), car.getYPos(), map);
      //adds the point next to the destination as a node
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
         graph =  MapConverter.addNode(graph, car.getDestX() - 1, car.getDestY(), map);
         graph[graph.length - 1].setHorizontal(Directions.RIGHT);

      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.RIGHT) {
         graph =  MapConverter.addNode(graph, car.getDestX(), car.getDestY() - 1, map);
         graph[graph.length - 1].setVertical(Directions.DOWN);

      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.DOWN) {
         graph =  MapConverter.addNode(graph, car.getDestX() + 1, car.getDestY(), map);
         graph[graph.length - 1].setHorizontal(Directions.LEFT);
      }
      if(((Road)map.get(car.getDestX(), car.getDestY())).getDirection() == Directions.LEFT) {
         graph =  MapConverter.addNode(graph, car.getDestX(), car.getDestY() + 1, map);
         graph[graph.length - 1].setVertical(Directions.UP  );
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
         if (current[1] == -1)
            return path;
         if (graph[current[1]].getX() == car.getDestX()
               && graph[current[1]].getY() == car.getDestY())
            destination = true;
         else
            relax(current, graph, pq, completed, useSpeed);
         completed = completedItem(completed, current);
      }

      // repeat until you pop the destination
      toArrayList(path, completed, completed[0][completed[0].length-1], graph);
      return path;
   }

   public static void toArrayList(ArrayList<Integer> path, int[][] arr, int index, graphNode[] graph) {
      // converts an array of connected items to an an ArrayList.
      // as stated at the top, it doesn't stay in the right lane to get to the
      // next node.
      if (arr[1][indexInCompleted(arr, index)] != -1)
         toArrayList(path, arr, arr[1][indexInCompleted(arr, index)], graph);
      else
         return;
      moveInDirection(graph[arr[1][indexInCompleted(arr, index)]], graph[arr[0][indexInCompleted(arr, index)]], path);
   }

   public static void relax(int[] current, graphNode[] graph, PriorityQueue pq, int[][] completed, boolean useSpeed) {
      int pos = current[1];
      int weight = current[0];
      int speedHorizontal = graph[pos].getHorizontalSpeed();
      int speedVertical = graph[pos].getVerticalSpeed();
      if (!useSpeed){
          speedHorizontal = 1; speedVertical = 1;
      }
      

      // Adding all the new weights for all four directions to the priority
      // queue
      if (graph[pos].getUp() != null && graph[pos].getVertical() == Directions.UP && indexInCompleted(completed, graph[pos].getUp().getIndex()) == -1) {
         int tempWeight = weight + (graph[pos].getY() - graph[pos].getUp().getY())*6/speedVertical;
         pq.add(tempWeight, graph[pos].getUp().getIndex(), pos);
      }
      if (graph[pos].getRight() != null && graph[pos].getHorizontal() == Directions.RIGHT && indexInCompleted(completed, graph[pos].getRight().getIndex()) == -1) {
         int tempWeight = weight + (graph[pos].getRight().getX() - graph[pos].getX())*6/speedHorizontal;
         pq.add(tempWeight, graph[pos].getRight().getIndex(), pos);
      }
      if (graph[pos].getDown() != null && graph[pos].getVertical() == Directions.DOWN && indexInCompleted(completed, graph[pos].getDown().getIndex()) == -1) {
         int tempWeight = weight + (graph[pos].getDown().getY() - graph[pos].getY())*6/speedVertical;
         pq.add(tempWeight, graph[pos].getDown().getIndex(), pos);
      }
      if (graph[pos].getLeft() != null && graph[pos].getHorizontal() == Directions.LEFT && indexInCompleted(completed, graph[pos].getLeft().getIndex()) == -1) {
         int tempWeight = weight + (graph[pos].getX() - graph[pos].getLeft().getX())*6/speedHorizontal;
         pq.add(tempWeight, graph[pos].getLeft().getIndex(), pos);
      }
   }
   public static int indexInCompleted(int[][] completed, int item)
   {
      for (int i = 0; i< completed[0].length; i++)
         if (completed[0][i] == item)
            return i;
      return -1;
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

//   public static boolean finalDestOneDirection(int start, int end) {
//      if (start == end || start + 1 == end || start - 1 == end)
//         return true;
//      return false;
//   }

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
