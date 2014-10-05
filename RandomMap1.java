//svlin2

public class RandomMap1 
{
	 public static void main (String args[])
	 {
		 int row = (int)((Math.random() * 25) + 1);
		 int col = row;		// ensures that the map is a square
		 
		 int [][] map = new int[row][col];
		 
		 //generates a random location for the traffic light
		 int center_x = (int) ((Math.random() * row));		
		 int center_y = (int) ((Math.random() * col));
		 
		 // ensures that the traffic light is not too close to the edge of the map
		 while ((center_x >= row - 4) || (row - center_x >= row - 4))
		 {
			 center_x = (int) ((Math.random() * row));
		 }
		 while ((center_y >= col - 4) || (col - center_y >= col - 4))
		 {
			 center_y = (int) ((Math.random() * col)); 
		 }
		 
		 // traffic intersection
		 for (int i = center_y + 1; i <= center_y + 3; i++)
		 {
			 map[center_x][i] = 1;
		 }
		 for (int i = center_y + 1; i >= center_y - 3; i--)
		 {
			 map[center_x][i] = 1;
		 }
		 
		 for (int i = 1; i <= 3; i++)
		 {
			 map[center_x + i][center_y] = 1;
		 }
		 for (int i = 1; i >= -3; i--)
		 {
			 map[center_x + i][center_y] = 1;
		 }
		 
		 map[center_x][center_y] = 9;		// traffic light
		 
		 // adds in cars into the roads with the traffic intersection
		 for (int i = 0; i < col; i++)
		 {
			 if (map[center_x][i] != 1 && map[center_x][i] != 9)
			 {
				 map[center_x][i] = 6;
			 }
		 }
		 
		 for (int i = 0; i < row; i++)
		 {
			 if (map[i][center_y] != 1 && map[i][center_y] != 9)
			 {
				 map[i][center_y] = 6;
			 }
		 }
		 
		 // prints out the entire 2D array
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
