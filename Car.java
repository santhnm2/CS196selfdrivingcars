import java.util.Map;
import java.util.ArrayList;

class Car {
	private int speed=0;
	private int dir=0; 
	private int xPos=0;
	private int yPos=0;
//	final private Tile destination=new Tile();   waiting to be implemented
	
	Map map; //Grid that the car is in
	
	public Car(Map map) {
		this.map = map;
	}
	
	public boolean move()
	{
		return true;

	}
	public boolean turn()
	{
		dir++;
		return true;
	}
	public boolean exists()
	{ 
		return true;
	}
	/*
	 * Given a direction, will find the tile (if it exists) in that direction and return it
	 * @param Direction to check in
	 * @return Tile in the given direction, or null if it doesn't exist.
	 */
	public Tile getNextTile(int dir)
	{
		int x = xPos;
		int y = yPos;
		switch(dir) {
		case Directions.UP:
			y--;
			break;
		case Directions.DOWN:
			y++;
			break;
		case Directions.RIGHT:
			x++;
			break;
		case Directions.LEFT:
			x--;
			break;
		}
		if(x>=0 && x<map.getTiles().length && y>=0 && y<map.getTiles()[0].length) //Ensure tile is in grid
			return map.get(x,y);
		else
			return null; //If not in grid, return null
	}
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int speed)
	{
		speed=this.speed;
	}
	public int getDir()
	{
		return dir;
	}
	public void seDir(int dir)
	{
		dir=this.dir;
	}
	// unsafe, do not use.
	public ArrayList<Integer> genPath(Tile dest) {
		int destX = dest.getX();
		int destY = dest.getY();
		int curX = xPos;
		int curY = yPos;
		ArrayList<Integer> path = new ArrayList<Integer>();
		while(curX!=destX && curY!=destY) {
			if(curX<destX && map.get(curX+1,curY) instanceof Road && (Road)map.get(curX+1,curY).getDirection()==Directions.RIGHT) {
				curX++;
				path.add(Directions.RIGHT);
			}
			else if(curX>destX && map.get(curX-1,curY) instanceof Road && (Road)map.get(curX-1,curY).getDirection()==Directions.LEFT) {
				curX--;
				path.add(Directions.LEFT);
			}
			else if(curY<destY && map.get(curX,curY+1) instanceof Road && (Road)map.get(curX,curY+1).getDirection()==Directions.DOWN) {
				curY++;
				path.add(Directions.DOWN);
			}
			else if(curY>destY && map.get(curX,curY-1) instanceof Road && (Road)map.get(curX,curY-1).getDirection()==Directions.UP) {
				curY--;
				path.add(Directions.UP);
			}
			else {
				//May throw an error in certain start conditions
				int nextDir = map.get(curX,curY).getDirection();
				path.add(nextDir);
				curX = getNextTile(nextDir).getX();
				curY = getNextTile(nextDir).getY();
			}
		}
		return path;
	}
	
}
