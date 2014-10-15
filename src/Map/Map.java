package Map;

import java.util.Arrays;
import Car.*;
import Map.*;
import Map.NonRoad.*;
import Map.Road.*;
import Constants.*;


public class Map {
    private final Tile[][] grid;
    private final Car[] cars;

    public Map(Tile[][] grid, Car[] cars) {
        this.grid = grid;
        this.cars = cars;
    }

    public Tile get(int x, int y) {
        return this.grid[y][x];
    }

    public int getLengthX() {
        return grid[0].length;
    }

    public int getLengthY() {
        return grid.length;
    }

    public Car[] getCars() {
        return this.cars;
    }

    @Override
    public String toString() {
        StringBuilder total = new StringBuilder();

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                total.append(grid[j][i]).append(" ");
            }

            total.append('\n');
        }

        return total.toString();
    }

    public void createVerticalRoad(int row, int width, int start, int end, int speed){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid[1].length - 1){
                    if(grid[row + i][j] instanceof Road || grid[row + i][j] instanceof TrafficLight){
                        if(grid[row + i][j + 1] instanceof Road) // look ahead to the right first, then check behind.
                            grid[row + i][j + 1] = new TrafficLight(row + i, j + 1, (i == 0 ? Directions.UP : Directions.DOWN), false);
                        else if(grid[row + i][j - 1] instanceof Road) // if ahead is not a road, check behind to make sure you haven't started on a road
                            grid[row + i][j - 1] = new TrafficLight(row+i, j-1, (i==0 ? Directions.UP : Directions.DOWN), false);
                        // should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
                        grid[row + i][j] = new TrafficLight(row+i, j, (i==0 ? Directions.UP : Directions.DOWN), false);
                    } else
                        grid[row + i][j] = new Road(row+i, j, speed, (i == 0 ? Directions.UP : Directions.DOWN));
                }
            }
        }
    }

    public void createHorizontalRoad(int row, int width, int start, int end, int speed){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid.length - 1){

                    if(grid[j][row + i] instanceof Road || grid[j][row + i] instanceof TrafficLight){
                        if(grid[j + 1][row + i] instanceof Road)
                            grid[j + 1][row + i] = new TrafficLight(j+1, row+i, (i==1 ? Directions.LEFT : Directions.RIGHT), false);
                        else if(grid[j - 1][row + i] instanceof Road)
                            grid[j - 1][row + i] = new TrafficLight(j-1, row+i, (i==1 ? Directions.LEFT : Directions.RIGHT), false);
                        //same as above; should turn above block into method.
                        grid[j][row + i] = new TrafficLight(j, row+i, (i==1 ? Directions.LEFT : Directions.RIGHT), false);
                    } else
                        grid[j][row + i] = new Road(j, row + i, speed, (i == 1 ? Directions.LEFT : Directions.RIGHT));
                }
            }
        }
    }

    public boolean pointIsValid(int x, int y) {
        return 0 <= y && y < grid.length &&
               0 <= x && x < grid[y].length;
    }
}
