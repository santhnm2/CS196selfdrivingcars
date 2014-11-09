package Map;
import Car.*;
import Map.NonRoad.*;


public class TestMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tile[][] testMap = new Tile[100][100];
		for(int i = 0; i < testMap.length; i++){
			for(int j = 0; j < testMap[0].length; j++){
				testMap[i][j] = new House(i,j);
			}
		}
		
		Car[] y = new Car[20];
		Map x = new RandomMapGenerator(30, 1).generateMap();
		//hardcoded threeway intersection
		System.out.println("Road initialized");

		System.out.print(x.toString());
		System.out.println("Road with roads created");
		
		//random map
/*		int size = 100;
		for(int i = 0; i < size; i += 4){
			if(Math.random() < 0.75)
				x.createHorizontalRoad(i, 2, (int)(Math.random()/2.0 * 20), 99);
		}
		for(int j = 0; j < size; j += 4){
			if(Math.random() < 0.75)
				x.createVerticalRoad(j, 2, (int)(Math.random()/2.0 * 20), 99);
		}
		x.print();
*/		
	}
}
