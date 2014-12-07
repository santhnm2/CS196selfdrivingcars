package Map;

public class Tile {
    protected final int x, y;
    public double density;

    public Tile(int x, int y) {
         this.x = x;
         this.y = y;
         density = 0;
    }
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
