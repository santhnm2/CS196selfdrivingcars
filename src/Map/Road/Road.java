
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
}
