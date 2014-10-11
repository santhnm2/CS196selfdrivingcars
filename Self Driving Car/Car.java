import java.util.ArrayList;


public class Car {
	private int speed=0;
	private int dir=0; //0 up 1 down 2 right 3 down 4
	private int xPos=0;
	private int yPos=0;
    ArrayList<Integer> path=new ArrayList<Integer>();
//	final private Tile destination=new Tile();   waiting to be implemented
// ArrayList of Directions
	public Car()//without direction
    {
    }
    public Car(ArrayList<Integer> path) //direction
    {
        path=this.path;
        
    }
    boolean move() // plug off the first item of the arraylist and execute that
	{
    	Tile nextTile=Car.getNextTile(path.get(0));
        if(nextTile.getX()>=0);
        {
        	xPos=nextTile.getX();
        	yPos=nextTile.getY();
        	path.remove(0);
        	return true;
        }
        else
        {
        	return false;
        }
		 
        

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
	static Tile getNextTile(int dir)
	{
		if(dir==0)
			return new Tile(xPos,yPos+1);
		else if(dir==1)
			return new Tile(xPos+1,yPos);
		else if(dir==2)
			return new Tile(xPos,yPos-1);
		else if(dir==3)
			return new Tile(xPos-1,yPos);
		else return new Tile(-1,-1); //missing Tile constructor
        
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
