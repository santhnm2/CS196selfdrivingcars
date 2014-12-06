package Map;

public class Tile implements java.io.Serializable{
    protected final int x, y;

    public Tile(int x, int y) {
         this.x = x;
         this.y = y;
    }
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
