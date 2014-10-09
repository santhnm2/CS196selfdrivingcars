
class Car {
	private int speed=0;
	private int dir=0; 
	private int xPos=0;
	private int yPos=0;
//	final private Tile destination=new Tile();   waiting to be implemented
	
	Grid grid; //Grid that the car is in
	
	public Car(Grid grid) {
		this.grid = grid;
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
		if(x>=0 && x<grid.tiles.length && y>=0 && y<grid.tiles[0].length) //Ensure tile is in grid
			return grid.tiles[x][y];
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
	
	
}
