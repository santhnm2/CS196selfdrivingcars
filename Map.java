package com.Cars;

public class Map {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Grid x = new Grid(20,20);
		//hardcoded threeway intersection
/*		x.print();
		System.out.println();
		x.createHorizontalRoad(3, 2, 0, 19);
		x.createVerticalRoad(4, 2, 3, 19);
		x.createHorizontalRoad(7, 2, 5, 19);
		x.createVerticalRoad(16, 2, 4, 19);
		x.print();
		System.out.println();
*/		
		//random map
		int size = 20;
		for(int i = 0; i < size; i += 4){
			if(Math.random() < 0.75){
				x.createHorizontalRoad(i, 2, (int)(Math.random()/2.0 * 20), 19);
			}
		}
		
		for(int j = 0; j < size; j += 4){
			if(Math.random() < 0.75)
				x.createVerticalRoad(j, 2, (int)(Math.random()/2.0 * 20), 19);
		}
		x.print();
		
	}
}
