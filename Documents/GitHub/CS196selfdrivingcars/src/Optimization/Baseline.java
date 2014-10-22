import java.util.ArrayList;
public class Baseline {

   
   //determines quickest path
   public ArrayList<Integer> quickest(Car car)
   {
      ArrayList<Integer> path;
      int curX = car.getCurX();
      int curY = car.getCurY()
      int distX =  car.getDestX() - curX;
      int distY =  car.getDestY() - curY;
      Boolean cont = false;
      for (int i = 0; i < Math.abs(distX);i++)
      {
         int dir = Directions.LEFT;
         if (distX > 0)
            dir = Directions.RIGHT;
         if (car.getNextTile(curX, curY, dir) instanceof TrafficLight)
         {
            for (int j = 0; i+j> Math.abs(distX) ; j++)
            {
               if ((car.getNextTile(curX+j, curY, dir) instanceof TrafficLight) || (curX + j == car.getDestX() && curY == car.getDestY()))
               {
                  cont = true;
                  j = distX;
               }
            }
            if (!cont)
            {
               if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
                  turnLeft();
               else if ((distY>0 && dir == Directions.LEFT) || (distY < 0 && dir == Directions.RIGHT))
                  turnRight();
            }
         }
         
      }
      return path;
   }
 
}
