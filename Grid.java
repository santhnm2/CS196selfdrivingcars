package com.Cars;

import java.util.Arrays;


public class Grid {
    private final Tile[][] grid;
    private final Car[] cars;

    public Grid(Tile[][] grid, Car[] cars) {
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

    public void createHorizontalRoad(int row, int width, int start, int end){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid[1].length - 1){
                    if(grid[row + i][j] instanceof Road || grid[row + i][j] instanceof TrafficLight){
                        if(grid[row + i][j + 1] instanceof Road) // look ahead to the right first, then check behind.
                            grid[row + i][j + 1] = new TrafficLight();
                        else if(grid[row + i][j - 1] instanceof Road) // if ahead is not a road, check behind to make sure you haven't started on a road
                            grid[row + i][j - 1] = new TrafficLight();
                        // should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
                        grid[row + i][j] = new TrafficLight();
                    } else
                        grid[row + i][j] = new Road((i == 0 ? Directions.LEFT : Directions.RIGHT));
                }
            }
        }
    }

    public void createVerticalRoad(int column, int width, int start, int end){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid.length - 1){

                    if(grid[j][column + i] instanceof Road || grid[j][column + i] instanceof TrafficLight){
                        if(grid[j + 1][column + i] instanceof Road)
                            grid[j + 1][column + i] = new TrafficLight();
                        else if(grid[j - 1][column + i] instanceof Road)
                            grid[j - 1][column + i] = new TrafficLight();
                        //same as above; should turn above block into method.
                        grid[j][column + i] = new TrafficLight();
                    } else
                        grid[j][column + i] = new Road((i == 1 ? Directions.UP : Directions.DOWN));
                }
            }
        }
    }
}
