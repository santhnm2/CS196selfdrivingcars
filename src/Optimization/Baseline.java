package Optimization;

import Constants.Directions;
import Map.Map;
import Map.NonRoad.*;
import Map.Road.TrafficLight;
import java.util.ArrayList;

public class Baseline {

   public static ArrayList<Integer> findPath(int sourceX, int sourceY, 
         int destX, int destY, ArrayList<Integer> path, int dir, Map map){
      if (dir == Directions.DOWN || dir == Directions.UP)
         sourceY = moveInDirection(path, map, sourceX, sourceY, destX, destY, dir);
      else
         sourceX = moveInDirection(path, map, sourceX, sourceY, destX, destY, dir);
      if (finalXDest(sourceX, destX)
            && finalYDest(sourceY, destY))
         return path;
      if (sourceX < 0 || sourceY < 0)
         return Baseline.findPath(Math.abs(sourceX), Math.abs(sourceY), destX, destY, path, (dir+2)%4, map);
      if (dir == Directions.UP && sourceX<destX ||
            dir == Directions.DOWN && sourceX>destX ||
            dir == Directions.RIGHT && sourceY<destY ||
            dir == Directions.LEFT && sourceY>destY)
         dir = turnRight(path, sourceX, sourceY, dir);
      else dir = turnLeft(path, sourceX, sourceY, dir);
      return Baseline.findPath(sourceX, sourceY, destX, destY, path, dir, map);
      
      
   }
   
   public static int moveInDirection(ArrayList<Integer> path, Map map, int currentX, int currentY, int destX, int destY, int dir) {
      boolean cont = true;
      // moves the car until it reaches a traffic light
      for (int i = 0; cont; i++) {
         if (map.get(currentX, currentY) instanceof TrafficLight)
            cont = false;
         if (currentX<0 || currentY<0 || currentX>= map.getLengthX() || currentY >= map.getLengthY() || map.get(currentX, currentY) instanceof NonRoad){
            path.add((dir+3)%4); cont = false;
            if (dir == Directions.DOWN || dir == Directions.UP)return -currentY;
            else return -currentX;
         }
         
         if (finalXDest(currentX, destX)
               && finalYDest(currentY, destY))
            cont = false;
         if (cont) {
            path.add(dir);
            if (dir == Directions.DOWN)currentY++;
            if (dir == Directions.UP)currentY--;
            if (dir == Directions.RIGHT)currentX++;
            if (dir == Directions.LEFT)currentX--; 
         }
      }
      if (dir == Directions.DOWN || dir == Directions.UP)return currentY;
      else return currentX;
      

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

   public static int turnRight(ArrayList<Integer> path, int x, int y,
         int currentDir) {
      if (currentDir == 0) {
         x = x + 1;
         y = y - 1;
         path.add(0);
         path.add(1);
      } else if (currentDir == 1) {
         x = x + 1;
         y = y + 1;
         path.add(1);
         path.add(2);
      } else if (currentDir == 2) {
         x = x - 1;
         y = y + 1;
         path.add(2);
         path.add(3);
      } else {
         x = x - 1;
         y = y - 1;
         path.add(3);
         path.add(0);
      }
      return (currentDir+1)%4;
   }

   public static int turnLeft(ArrayList<Integer> path, int x, int y,
         int currentDir) {
      if (currentDir == 0) {
         x = x - 2;
         y = y - 2;
         path.add(0);
         path.add(0);
         path.add(3);
         path.add(3);
      } else if (currentDir == 1) {
         x = x + 2;
         y = y - 2;
         path.add(1);
         path.add(1);
         path.add(0);
         path.add(0);
      } else if (currentDir == 2) {
         x = x + 2;
         y = y + 2;
         path.add(2);
         path.add(2);
         path.add(1);
         path.add(1);
      } else {
         x = x - 2;
         y = y + 2;
         path.add(3);
         path.add(3);
         path.add(2);
         path.add(2);
      }
      return (currentDir+3)%4;
   }

}
