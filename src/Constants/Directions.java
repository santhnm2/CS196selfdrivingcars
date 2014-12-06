package Constants;
public class Directions {
	public static final int NO_DIR = -1;
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public static final int[] dirs = {0,1,2,3};

	public static int getOppositeDir(int dir) { 
		int val = dir + 2;
		if(val >= 4) val -= 4;
		return val;
	}
}