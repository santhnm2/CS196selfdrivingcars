
public class TrafficLight extends Tile {
	private int dir;
	
	private boolean isRed;
	public TrafficLight(int x, int y, int direction, boolean isRed)
	{
		posX = x;
		posY = y;
		dir = direction;
		this.isRed = isRed;
	}
	
	public boolean getStatus()
	{
		return isRed;
	}
	public void toggle() {
		isRed = !isRed;
	}
}
