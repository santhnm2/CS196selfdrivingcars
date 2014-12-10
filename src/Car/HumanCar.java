package Car;
import Constants.Directions;
import Map.Map;
import Map.Tile;
import Map.Road.Road;
import Map.Road.TrafficLight;
import Optimization.PathGenerator;


public class HumanCar extends Car {
	/**
	 * 
	 */

    public HumanCar(int x,int y,Map map)
    {
    	super(x, y, map);
    }
    public void move(int dir)
    {
    		Tile nextTile=getNextTile(xPos, yPos, dir);
    		
    		Road road=(Road) map.get(xPos, yPos);
    		road.carDecrement(this);
	        if(nextTile instanceof Road&&nextTile.getX()>=0 && nextTile.getY()>=0 && nextTile.getX()<map.getLengthX() && nextTile.getY()<map.getLengthY()) {
	        	{
	        		road=(Road)nextTile;
	        		xPos=nextTile.getX();yPos=nextTile.getY();
	        		road.carIncrement(this);
	        	} 
    	
    }
}
    }
