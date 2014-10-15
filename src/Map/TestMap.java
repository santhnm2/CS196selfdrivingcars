package src.Map;
import src.Car.*;
import src.Map.NonRoad.*;


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
		Map x = new Map(testMap, y);
		//hardcoded threeway intersection
		System.out.println("Road initialized");
		
		x.createHorizontalRoad(3, 2, 0, 99, 5);
		x.createVerticalRoad(4, 2, 3, 99, 5);
		x.createHorizontalRoad(7, 2, 5, 99, 5);
		x.createVerticalRoad(16, 2, 4, 99, 5);
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
