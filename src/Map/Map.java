package Map;

import Car.Car;
import Constants.Directions;
import Map.Road.Road;
import Map.Road.TrafficLight;
import Map.Coord;

public class Map {
    private final Tile[][] grid;
    private final Car[] cars;
    private final double[][] density;
    public Map(Tile[][] grid, Car[] cars) {
        this.grid = grid;
        this.cars = cars;
        density = new double[grid.length][grid[0].length];
    }
    public Map(Tile[][] grid, Car[] cars, double[][] density){
        this.grid = grid;
        this.cars = cars;
        this.density = density;
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

    //will create road with least turns between the two density coordinates.
    public void createRoadBetweenDensities(Coord one, Coord two, double avgDens){
        if(Math.random() > .5){
            if(one.x < two.x) {
                this.createHorizontalRoad(one.y, 2, one.x, two.x, 25, 3);
            } else {
                this.createHorizontalRoad(one.y, 2, two.x, one.x, 25, 3);
            }
            if(one.y < two.y){
                this.createVerticalRoad(two.x, 2, one.y, two.y, 25, 3);
            } else {
                this.createVerticalRoad(two.x, 2, two.y, one.y, 25, 3);
            }
        } else {
            if(one.y > two.y){
                this.createVerticalRoad(one.x, 2, one.y, two.y, 25, 3);
            } else {
                this.createVerticalRoad(one.x, 2, one.y, two.y, 25, 3);
            }
            if(one.x < two.x) {
                this.createHorizontalRoad(two.y, 2, one.x, two.x, 25, 3);
            } else {
                this.createHorizontalRoad(two.y, 2, two.x, one.x, 25, 3);
            }
        }

    }


    //width should always be 2. Lanes refers to occupancy of road.
    public void createVerticalRoad(int col, int width, int start, int end, int speed, int lanes){
        for(int i = 0; i < width; i++){
            for(int j = start; j <= end; j++){
                if(j > 0 && j < grid[1].length - 1){
                    if(grid[col + i][j] instanceof Road || grid[col + i][j] instanceof TrafficLight){
                        if(grid[col + i][j + 1] instanceof Road) // look ahead to the right first, then check behind.
                            grid[col + i][j + 1] = new TrafficLight(col + i, j + 1, false);
                        else if(grid[col + i][j - 1] instanceof Road) // if ahead is not a road, check behind to make sure you haven't started on a road
                            grid[col + i][j - 1] = new TrafficLight(col+i, j-1,false);
                        // should turn above block of if/else into a method. Fosho. Seriously this code is a pain.
                        grid[col + i][j] = new TrafficLight(col+i, j, false);
                    } else
                        grid[col + i][j] = new Road(col + i, j, speed, (i == 0 ? Directions.DOWN : Directions.UP), lanes);
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
