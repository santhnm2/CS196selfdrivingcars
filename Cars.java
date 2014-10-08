
class Cars {
	final private int speed=0;
	final private int dir=0; 
	final private int xPos=0;
	final private int yPos=0;
//	final private Tile destination=new Tile();   waiting to be implemented
	boolean move()
	{
		return true;

	}
	boolean turn()
	{
		dir++;
		return true;
	}
	boolean exists()
	{ 
		return true;
	}
	Tile getNextTile(int dir)
	{
		if(dir==0)
			xPos++;
		else if(dir==1)
			xPos--;
		else if(dir==2)
			yPos++;
		else if(dir==3)
			yPos--;
	}
	int getSpeed()
	{
		return speed;
	}
	void setSpeed(int speed)
	{
		speed=this.speed;
	}
	int getDir()
	{
		return dir;
	}
	void seDir(int dir)
	{
		dir=this.dir;
	}
	
	
}
