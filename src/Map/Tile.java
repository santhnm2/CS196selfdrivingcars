package Map;

public class Tile {
    private final int x, y;
    private double density;

    public Tile(int x, int y, double density) {
         this.x = x;
         this.y = y;
        this.density = density;
    }
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.density = 0;
    }
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

    public double getDensity(){return density;}
}
