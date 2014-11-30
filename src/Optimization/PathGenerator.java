package Optimization;

import Constants.Directions;
import Car.Car;
import Map.Map;
import Map.Road.TrafficLight;
import Optimization.Dijkstra;

import java.util.ArrayList;

public class PathGenerator {

   public static ArrayList<Integer> useDijkstra(Map map, Car car){
      return Dijkstra.findPath(map, car, true);
   }
   public static ArrayList<Integer> minimizeDistance(Map map, Car car){
       return Dijkstra.findPath(map, car, false);
   }
   //this is meant to find a path, by heading until it reaches the correct
   //lattitude or longitude.
   //only works if the map is a grid with no anomalies.
   //a better way to implement this would be to use dijkstra ignoring speed.
   //it is probably possible to adapt this to road anomalies, will atempt.
   public ArrayList<Integer> baseline(Car car) {
      ArrayList<Integer> path = new ArrayList<Integer>();
      int[] position = new int[2];
      position[0] = car.getXPos();
      position[1] = car.getYPos();
      int distX = car.getDestX() - position[0];
      int distY = car.getDestY() - position[1];
      int dir = car.getDir();
      // checks if car is moving horizontally or vertically to start
      // then moves the car on that axis towards the destination
      while (!(finalXDest(position[0], car.getDestX()) && finalYDest(
            position[1], car.getDestY()))) {
         if (dir == Directions.LEFT || dir == Directions.RIGHT)
            PathGenerator.travelHorizontally(path, car, position, dir);
         else if (dir == Directions.UP || dir == Directions.DOWN)
            PathGenerator.travelVertically(path, car, position, dir);

         if (!(finalXDest(position[0], car.getDestX())
               && finalYDest(position[1], car.getDestY()))) {
            // determine if the destination is to the left or right and turns
            // that way
            if ((distY > 0 && dir == Directions.LEFT)
                  || (distY < 0 && dir == Directions.RIGHT))
               turnLeft(path, position, dir);
            else if ((distY < 0 && dir == Directions.LEFT)
                  || (distY > 0 && dir == Directions.RIGHT))
               turnRight(path, position, dir);
            else if (distX > 0 && dir == Directions.UP || distX < 0
                  && dir == Directions.DOWN)
               turnRight(path, position, dir);
            else if (distX < 0 && dir == Directions.UP || distX > 0
                  && dir == Directions.DOWN)
               turnLeft(path, position, dir);
         }
         dir = path.get(path.size() - 1);
      }
      return path;
   }

   public static void travelHorizontally(ArrayList<Integer> path, Car car,
         int[] position, int dir) {
      int distX = car.getDestX() - position[0];
      // used to differentiate between moving in negative X or positive X
      // direction
      short inverter = -1;
      if (dir == Directions.RIGHT)
         inverter = 1;
      boolean cont = true;
      // moves the car horizontally until the last turn before destination X
      // value
      for (int i = 0; cont; i++) {
         // checks whether a light is the last one before destination X
         if (car.getNextTile(position[0], position[1], dir) instanceof TrafficLight) {
            cont = false;
            for (int j = 0; i + j > Math.abs(distX) && !cont; j++) {
               // checks each square after the turn to see if it's the
               // destination or a turn preceding the destination
               if ((car.getNextTile(position[0] + inverter * j, position[1],
                     dir) instanceof TrafficLight)
                     || (finalXDest(position[0] + inverter * j, car.getDestX()) && finalYDest(
                           position[1], car.getDestY())))
                  cont = true;
            }
         }
         if (finalXDest(position[0], car.getDestX())
               && finalYDest(position[1], car.getDestY()))
            cont = false;
         if (cont) {
            path.add(dir);
            position[0] += inverter;
         }
      }

   }

   // same idea as the horizontal class just modifying the y direction
   public static void travelVertically(ArrayList<Integer> path, Car car,
         int[] position, int dir) {
      int distY = car.getDestY() - position[1];
      short inverter = -1;
      if (dir == Directions.UP)
         inverter = 1;
      boolean cont = true;
      for (int i = 0; cont; i++) {
         if (car.getNextTile(position[0], position[1], dir) instanceof TrafficLight) {
            cont = false;
            for (int j = 0; i + j > Math.abs(distY) && !cont; j++) {
               if ((car.getNextTile(position[0], position[1] + inverter * j,
                     dir) instanceof TrafficLight)
                     || (finalYDest(position[1] + inverter * j, car.getDestY()) && finalXDest(
                           position[0], car.getDestX())))
                  cont = true;
            }
         }
         if (finalXDest(position[0], car.getDestX())
               && finalYDest(position[1], car.getDestY()))
            cont = false;
         if (cont) {
            path.add(dir);
            position[1] += inverter;
         }
      }

   }

   // checks if the current x is the destination or is on the other side of the
   // street (or on the side of the road...)
   public static Boolean finalXDest(int xPos, int destX) {
      if (xPos == destX || xPos + 1 == destX || xPos - 1 == destX)
         return true;
      return false;
   }

   // checks if the current y is the destination or is on the other side of the
   // street (or on the side of the road...)
   public static Boolean finalYDest(int yPos, int destY) {
      if (yPos == destY || yPos + 1 == destY || yPos - 1 == destY)
         return true;
      return false;
   }

   public static void turnRight(ArrayList<Integer> path, int[] position,
         int currentDir) {
      if (currentDir == 0) {
         position[0] = position[0] + 1;
         position[1] = position[1] - 1;
         path.add(0);
         path.add(1);
      } else if (currentDir == 1) {
         position[0] = position[0] + 1;
         position[1] = position[1] + 1;
         path.add(1);
         path.add(2);
      } else if (currentDir == 2) {
         position[0] = position[0] - 1;
         position[1] = position[1] + 1;
         path.add(2);
         path.add(3);
      } else {
         position[0] = position[0] - 1;
         position[1] = position[1] - 1;
         path.add(3);
         path.add(0);
      }
   }

   public static void turnLeft(ArrayList<Integer> path, int[] position,
         int currentDir) {
      if (currentDir == 0) {
         position[0] = position[0] - 2;
         position[1] = position[1] - 2;
         path.add(0);
         path.add(0);
         path.add(3);
         path.add(3);
      } else if (currentDir == 1) {
         position[0] = position[0] + 2;
         position[1] = position[1] - 2;
         path.add(1);
         path.add(1);
         path.add(0);
         path.add(0);
      } else if (currentDir == 2) {
         position[0] = position[0] + 2;
         position[1] = position[1] + 2;
         path.add(2);
         path.add(2);
         path.add(1);
         path.add(1);
      } else {
         position[0] = position[0] - 2;
         position[1] = position[1] + 2;
         path.add(3);
         path.add(3);
         path.add(2);
         path.add(2);
      }
   }
}
