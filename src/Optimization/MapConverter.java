package Optimization;

import Map.*;
import Map.Road.*;

public class MapConverter {
   // converts a given map to a graph w/ vertexes at traffic lights.
   public static graphNode[] toGraph(Map map) {
      graphNode[] graph = new graphNode[0];
      for (int x = 0; x < map.getLengthX(); x++)
         for (int y = 0; y < map.getLengthY(); y++)
            if (map.get(x, y) instanceof TrafficLight)
               addNode(graph, x, y, map);
      return graph;
   }

   public static void addNode(graphNode[] graph, int x, int y, Map map) {
      graphNode[] temp = new graphNode[graph.length + 1];
      for(int i = 0; i < graph.length; i++) {
    	  temp[i] = graph[i];
      }
      temp[graph.length] = new graphNode(x, y);
      // search up until you get to a node and then set this node's up and that node's
      // down.
      for(int i = y; i >= 0; i--) {
    	  if(nodeIndex(graph, x, i) != -1) {
    		  temp[graph.length].setUp(temp[nodeIndex(graph, x, i)]);
    		  temp[nodeIndex(graph, x, i)].setDown(temp[graph.length]);
    	  }
      }
      // search left until you get to a node and then set this node's left and that
      // node's right.
      for(int i = x; i >= 0; i--) {
    	  if(nodeIndex(graph, i, y) != -1) {
    		  temp[graph.length].setLeft(temp[nodeIndex(graph, i, y)]);
    		  temp[nodeIndex(graph, i, y)].setRight(temp[graph.length]);
    	  }
      }
      // search down until you get to a node and then set this node's down and that
      // node's up. This is done because it will help when we add the source and the
      // destination as nodes.
      for(int i = y; i < map.getLengthY(); i++) {
    	  if(nodeIndex(graph, x, i) != -1) {
    		  temp[graph.length].setDown(temp[nodeIndex(graph, x, i)]);
    		  temp[nodeIndex(graph, x, i)].setUp(temp[graph.length]);
    	  }
      }
      // search right until you get to a node and then set this node's right and that
      // node's left. This is done because it will help when we add the source and the
      // destination as nodes.
      for(int i = x; i < map.getLengthX(); i++) {
    	  if(nodeIndex(graph, i, y) != -1) {
    		  temp[graph.length].setRight(temp[nodeIndex(graph, i, y)]);
    		  temp[nodeIndex(graph, i, y)].setLeft(temp[graph.length]);
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
