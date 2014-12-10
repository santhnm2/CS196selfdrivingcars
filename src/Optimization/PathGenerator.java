package Optimization;

import Constants.Directions;
import Car.Car;
import Map.Map;
import Map.Road.*;
import Optimization.Dijkstra;

import java.util.ArrayList;

public class PathGenerator {

   public static ArrayList<Integer> minimizeTime(Map map, Car car){
      return Dijkstra.findPath(map, car, 0);
   }
   public static ArrayList<Integer> minimizeDistance(Map map, Car car){
      return Dijkstra.findPath(map, car, 1);
   }
   public static ArrayList<Integer> base(Map map, Car car){
      return Dijkstra.findPath(map, car, 2);        
   }
   public static ArrayList<Integer> avoidHighways(Map map, Car car){
      return Dijkstra.findPath(map, car, 3);        
  }
}