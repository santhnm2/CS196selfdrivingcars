package Optimization;

import Constants.Directions;
import Map.*;
import Map.Road.*;
import Map.NonRoad.*;
/*PROBLEMS:
*currently the MapConverter stores each intersection as four nodes. It would be better if it was one node.
*destination is stored as a node on only one side of the road. the other side isn't recognized as the destination.
*
*/
public class MapConverter {
   // converts a given map to a graph w/ vertices at traffic lights.
   public static graphNode[] toGraph(Map map) {
      graphNode[] graph = new graphNode[0];
      for (int x = 0; x < map.getLengthX(); x++)
         for (int y = 0; y < map.getLengthY(); y++)
            if (map.get(x, y) instanceof TrafficLight){
               addNode(graph, x, y, map);
               for (int i = -2; i <= 2; i++){
                  if (map.get(x+i, y) instanceof Road && !(map.get(x+i, y) instanceof TrafficLight) && graph[graph.length -1].getHorizontal() == Directions.NO_DIR)
                     graph[graph.length-1].setHorizontal(((Road)map.get(x+1,y)).getDirection());
                  if (map.get(x, y+i) instanceof Road && !(map.get(x, y+i) instanceof TrafficLight) && graph[graph.length -1].getHorizontal() == Directions.NO_DIR)
                     graph[graph.length-1].setVertical(((Road)map.get(x,y+i)).getDirection());
               }
            }
      return graph;
   }
   
   public static void addNode(graphNode[] graph, int x, int y, Map map) {
      graphNode[] temp = new graphNode[graph.length + 1];
      for(int i = 0; i < graph.length; i++) {
    	  temp[i] = graph[i];
      }
      temp[graph.length] = new graphNode(x, y,graph.length);
      // search up until you get to a node and then set this node's up and that node's
      // down.
      for(int i = y; i >= 0; i--) {
    	  if(nodeIndex(graph, x, i) != -1) {
    		  temp[graph.length].setUp(temp[nodeIndex(graph, x, i)]);
    		  temp[nodeIndex(graph, x, i)].setDown(temp[graph.length]);
           //forces the for loop to end because there is a break in the road.
           if (map.get(x, i) instanceof NonRoad)
              i = 0;
    	  }
      }
      // search left until you get to a node and then set this node's left and that
      // node's right.
      for(int i = x; i >= 0; i--) {
    	  if(nodeIndex(graph, i, y) != -1) {
    		  temp[graph.length].setLeft(temp[nodeIndex(graph, i, y)]);
    		  temp[nodeIndex(graph, i, y)].setRight(temp[graph.length]);
           //forces the for loop to end because there is a break in the road.
           if (map.get(i, x) instanceof NonRoad)
              i = 0;
    	  }
      }
      // search down until you get to a node and then set this node's down and that
      // node's up. This is done only when we add a source or a destination.
      for(int i = y; i < map.getLengthY(); i++) {
    	  if(nodeIndex(graph, x, i) != -1) {
    		  temp[graph.length].setDown(temp[nodeIndex(graph, x, i)]);
    		  temp[nodeIndex(graph, x, i)].setUp(temp[graph.length]);
           //forces the for loop to end because there is a break in the road.
           if (map.get(x, i) instanceof NonRoad)
              i = map.getLengthY();
    	  }
      }
      // search right until you get to a node and then set this node's right and that
      // node's left.  This is done only when we add a source or a destination.
      for(int i = x; i < map.getLengthX(); i++) {
    	  if(nodeIndex(graph, i, y) != -1) {
    		  temp[graph.length].setRight(temp[nodeIndex(graph, i, y)]);
    		  temp[nodeIndex(graph, i, y)].setLeft(temp[graph.length]);
    		//forces the for loop to end because there is a break in the road.
           if (map.get(i, y) instanceof NonRoad)
              i = map.getLengthX();
    	  }
      }
   }

   public static int nodeIndex(graphNode[] graph, int x, int y) {
      for (int i = 0; i < graph.length; i++)
         if (graph[i].getX() == x && graph[i].getY() == y)
            return i;
		return -1;
	}

}
