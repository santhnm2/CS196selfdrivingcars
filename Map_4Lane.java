// svlin2

public class Map_4Lane 
{
	 public static void main (String args[])
	 {
		int [] [] map = new int [9][9];
		
		for (int i = 2; i < map.length - 2; i++)
		{
			map[4][i] = 1;
			map[i][4] = 1;
		}
		
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map.length; j++)
			{
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	 }
}
