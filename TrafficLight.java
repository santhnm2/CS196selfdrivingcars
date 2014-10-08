
public class TrafficLight extends Tile {
	private int dir;
	private boolean canMoveLeft, canMoveRight, canMoveUp, canMoveDown;
	
	public TrafficLight(int x, int y, int direction)
	{
		posX = x;
		posY = y;
		dir = direction;
		canMoveLeft = true;
	}
	
	public boolean getStatus()
	{
		return canMoveLeft;
	}
	
}
