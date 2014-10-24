package Map;

import Car.Car;
import Constants.Directions;
import Map.Road.Road;
import Map.Road.TrafficLight;


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

    public void createVerticalRoad(int row, int width, int start, int end, int speed, int lanes){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid[1].length - 1){
                    if(grid[row + i][j] instanceof Road || grid[row + i][j] instanceof TrafficLight){
                        if(grid[row + i][j + 1] instanceof Road) // look ahead to the right first, then check behind.
                            grid[row + i][j + 1] = new TrafficLight(row + i, j + 1, false);
                        else if(grid[row + i][j - 1] instanceof Road) // if ahead is not a road, check behind to make sure you haven't started on a road
                            grid[row + i][j - 1] = new TrafficLight(row+i, j-1,false);
                        // should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
                        grid[row + i][j] = new TrafficLight(row+i, j, false);
                    } else
                        grid[row + i][j] = new Road(row+i, j, speed, (i == 0 ? Directions.DOWN : Directions.UP), lanes);
                }
            }
        }
    }

    public void createHorizontalRoad(int row, int width, int start, int end, int speed, int lanes){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid.length - 1){

                    if(grid[j][row + i] instanceof Road || grid[j][row + i] instanceof TrafficLight){
                        if(grid[j + 1][row + i] instanceof Road)
                            grid[j + 1][row + i] = new TrafficLight(j+1, row+i, false);
                        else if(grid[j - 1][row + i] instanceof Road)
                            grid[j - 1][row + i] = new TrafficLight(j-1, row+i, false);
                        //same as above; should turn above block into method.
                        grid[j][row + i] = new TrafficLight(j, row+i, false);
                    } else
                        grid[j][row + i] = new Road(j, row + i, speed, (i == 1 ? Directions.RIGHT : Directions.LEFT), lanes);
                }
            }
        }
    }

    public boolean pointIsValid(int x, int y) {
        return 0 <= y && y < grid.length &&
               0 <= x && x < grid[y].length;
    }
}
