package Optimization;

import Map.*;
import Map.Road.*;

public class MapConverter {
   // converts a given map to a graph w/ vertexes at traffic lights.
   public static graphNode[] toGraph(Map map) {
      graphNode[] graph = new graphNode[0];
      for (int y = 0; y < map.getLengthY(); y++)
         for (int x = 0; x < map.getLengthX(); x++)
            if (map.get(x, y) instanceof TrafficLight)
               addNode(graph, x, y);
      return graph;
   }

   public static void addNode(graphNode[] graph, int x, int y) {
      graphNode[] temp = new graphNode[graph.length + 1];
      temp[graph.length] = new graphNode(x, y);
      // search up until non-road or TL then set this node's up and that node's
      // down.
      // search left until non-road or TL then set this node's left and that
      // node's right.

      // also need to do right and down for when we add start points and
      // destinations.
   }

   public static int nodeIndex(graphNode[] graph, int x, int y) {
      for (int i = 0; i < graph.length; i++)
         if (graph[i].getX() == x && graph[i].getY() == y)
            return i;
      return -1;
   }

}
