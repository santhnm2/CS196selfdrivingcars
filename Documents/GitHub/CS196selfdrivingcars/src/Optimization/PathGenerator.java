
import java.util.ArrayList;


public class PathGenerator {
   
   //determines baseline path
   public ArrayList<Integer> baseline(Car car)
   {
      ArrayList<Integer> path;
      int[] position = new int[2];
      position[0] = car.getXPos();
      position[1] = car.getYPos();
      int distX =  car.getDestX() - position[0];
      int distY =  car.getDestY() - position[1];
      boolean cont = true;
      int dir = car.getDir();
      if (dir == Directions.LEFT || dir == Directions.RIGHT)
      {
         PathGenerator.travelHorizontally(path, car, position);
         dir = path.get(path.size()-1);
         if (position[0] == car.getDestX() && position[1] == car.getDestY())
            return path;
         if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
             turnLeft(path);
         else if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
            turnRight(path);
      }
      
      PathGenerator.travelVertically(path, car, position);
      if (position[0] == car.getDestX() && position[1] == car.getDestY())
         return path;
      if (distX > 0 && dir == Directions.UP || distX<0 && dir == Directions.DOWN)
         turnRight(path);
      else if (distX < 0 && dir == Directions.UP || distX > 0 && dir == Directions.DOWN)
         turnLeft(path);
      
      PathGenerator.travelHorizontally(path, car, position);
      if (position[0] == car.getDestX() && position[1] == car.getDestY())
         return path;
      if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
         turnLeft(path);
      else if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
         turnRight(path);
      
      PathGenerator.travelVertically(path, car, position);
         return path;
   }
   
   public static void travelHorizontally(ArrayList<Integer> path, Car car, int[] position)
   {
      int dir = path.get(path.size()-1);
      int distX =  car.getDestX() - position[0];
      short inverter = -1;
      if (dir == Directions.RIGHT)
         inverter = 1;
      boolean cont = true;
      for (int i = 0; i < distX;i++)
      {
         if (car.getNextTile(position[0], position[1], dir) instanceof TrafficLight)
         {
            cont = false;
            for (int j = 0; i+j> Math.abs(distX) && !cont ; j++)
            {
               if ((car.getNextTile(position[0]+inverter*j, position[1], dir) instanceof TrafficLight) || (position[0] + inverter*j == car.getDestX() && position[1] == car.getDestY()))
                  cont = true;
            }
         }
         if (cont)
         {
            path.add(dir);
            position[0]+= inverter;
         }
      }
      
   }
 
   public static void travelVertically(ArrayList<Integer> path, Car car, int[] position)
   {
      int dir = path.get(path.size()-1);
      int distY = car.getDestY() - position[1];
      short inverter = -1;
      if (dir == Directions.UP)
         inverter = 1;
      boolean cont = true;
      for (int i = 0; i < distY;i++)
      {
         if (car.getNextTile(position[0], position[1], dir) instanceof TrafficLight)
         {
            cont = false;
            for (int j = 0; i+j> Math.abs(distY) && !cont ; j++)
            {
               if ((car.getNextTile(position[0], position[1] + inverter*j, dir) instanceof TrafficLight) || (position[1] + inverter*j == car.getDestY() && position[0] == car.getDestX()))
                  cont = true;
            }
         }
         if (cont)
         {
            path.add(dir);
            position[1]+= inverter;
         }
      }
      
   }
 
}
