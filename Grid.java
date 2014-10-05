package com.Cars;

public class Grid {
	int[][] map;
	final int NON_ROAD = 0;
	final int ROAD = 1;
	final int TRAFFIC_LIGHT = 2;
	
	public Grid(int sizeX, int sizeY){
		map = new int[sizeX][sizeY];
	}
	public int[][] getMap(){
		return map;
	}
	public int getLength(){
		return map.length;
	}
	public int getHeight(){
		return map[1].length;
	}
	//for now, will have integer array to represent road/non-road/etc.
	//0 = non-road. Anything else = some kind of road, traffic light, etc.
	public void print(){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	public void createHorizontalRoad(int row,int width, int start, int end){
		for(int i = 0; i < width; i++){
			for(int j = start; j <= end; j++){
				if(j > 0 && j < map[1].length - 1){
					
					if(map[row+i][j] == ROAD || map[row+i][j] == TRAFFIC_LIGHT){
						if(map[row + i][j + 1] == ROAD)//look ahead to the right first, then check behind.
							map[row+i][j + 1] = TRAFFIC_LIGHT;
						else if(map[row+i][j - 1] == ROAD)//if ahead is not a road, check behind to make sure you haven't started on a road
							map[row+i][j - 1] = TRAFFIC_LIGHT;
				//should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
						map[row+i][j] = TRAFFIC_LIGHT;
					} else
						map[row + i][j] = ROAD;
				}
			}
		}
	}
	
	public void createVerticalRoad(int column, int width, int start, int end){
		for(int i = 0; i < width; i++){
			for(int j = start; j <= end; j++){
				if(j > 0 && j < map.length - 1){
					
					if(map[j][column + i] == ROAD || map[j][column+i] == TRAFFIC_LIGHT){	
						if(map[j+1][column+i] == ROAD)
							map[j+1][column+i] = TRAFFIC_LIGHT;
						else if(map[j-1][column+i] == ROAD)
							map[j-1][column+i] = TRAFFIC_LIGHT;
				//same as above; should turn above block into method.
						map[j][column + i] = TRAFFIC_LIGHT;
					} else
						map[j][column + i] = ROAD;
				}
			}
		}
	}
	
	
	
}
