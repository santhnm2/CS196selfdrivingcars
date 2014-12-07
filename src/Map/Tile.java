package Map;

public class Tile implements java.io.Serializable{
	protected final int x, y;
	protected final double density;

	public Tile(int x, int y, double density) {
		this.x = x;
		this.y = y;
		this.density = density;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public double getDensity()
	{
		return density;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
