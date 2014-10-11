package src.Map.Road;

import src.Map.*;
import src.Car.*;

public class Road extends Tile
{
	private int speedLimit;
	private int dir;
	private Car[] occupancy;
	
	public Road(int x, int y, int speed, int direction)
	{
		posX = x;
		posY = y;
		speedLimit = speed;
		dir = direction;
	}
	
	public int getSpeed()
	{
		return speedLimit;
	}
	
	public int getDirection()
	{
		return dir;
	}
	public String toString(){
		switch(dir){
		case 0: return "UR";
		case 1: return "RR";
		case 2: return "DR";
		case 3: return "LR";
		}
		return null;
	}
}
