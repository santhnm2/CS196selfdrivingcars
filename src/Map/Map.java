package src.Map;

import java.util.Arrays;
import src.Car.*;
import src.Map.*;
import src.Map.NonRoad.*;
import src.Map.Road.*;
import src.Constants.*;


public class Map {
    private final Tile[][] grid;
    private final Car[] cars;

    public Map(Tile[][] grid, Car[] cars) {
        this.grid = grid;
        this.cars = cars;
    }

    public Tile get(int x, int y) {
        return this.grid[x][y];
    }

    public Tile[][] getTiles() {
        return this.grid;
    }

    public Car[] getCars() {
        return this.cars;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(grid).replaceAll("], ", "]\n");
    }

    public void createHorizontalRoad(int row, int width, int start, int end, int speed){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid[1].length - 1){
                    if(grid[row + i][j] instanceof Road || grid[row + i][j] instanceof TrafficLight){
                        if(grid[row + i][j + 1] instanceof Road) // look ahead to the right first, then check behind.
                            grid[row + i][j + 1] = new TrafficLight(row+i, j+1, (i==0 ? Directions.LEFT : Directions.RIGHT), false);
                        else if(grid[row + i][j - 1] instanceof Road) // if ahead is not a road, check behind to make sure you haven't started on a road
                            grid[row + i][j - 1] = new TrafficLight(row+i, j-1, (i==0 ? Directions.LEFT : Directions.RIGHT), false);
                        // should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
                        grid[row + i][j] = new TrafficLight(row+i, j, (i==0 ? Directions.LEFT : Directions.RIGHT), false);
                    } else
                        grid[row + i][j] = new Road(row+i, j, speed, (i == 0 ? Directions.LEFT : Directions.RIGHT));
                }
            }
        }
    }

    public void createVerticalRoad(int column, int width, int start, int end, int speed){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid.length - 1){

                    if(grid[j][column + i] instanceof Road || grid[j][column + i] instanceof TrafficLight){
                        if(grid[j + 1][column + i] instanceof Road)
                            grid[j + 1][column + i] = new TrafficLight(j+1, column+i, (i==1 ? Directions.UP : Directions.DOWN), false);
                        else if(grid[j - 1][column + i] instanceof Road)
                            grid[j - 1][column + i] = new TrafficLight(j-1, column+i, (i==1 ? Directions.UP : Directions.DOWN), false);
                        //same as above; should turn above block into method.
                        grid[j][column + i] = new TrafficLight(j, column+i, (i==1 ? Directions.UP : Directions.DOWN), false);
                    } else
                        grid[j][column + i] = new Road(j, column + i, speed, (i == 1 ? Directions.UP : Directions.DOWN));
                }
            }
        }
    }
}
