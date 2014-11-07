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

    public void createVerticalRoad(int startX, int startY, int width, int lanes, int speed){
        startX += startX % 2;
        startY += startY % 2;
        width  += width % 2;

        for (int i = 0; i < width; i++) {
            if (startY + i >= getLengthY()) break;

            if (grid[startX][startY + i] instanceof Road) {
                if (((Road) grid[startX][startY + i]).getDirection() == Directions.DOWN) break;

                grid[startX][startY + i] = new TrafficLight(startX, startY + i, true);
            } else {
                grid[startX][startY + i] = new Road(startX, startY + i, speed, Directions.DOWN, lanes);
            }

            if (grid[startX + 1][startY + i] instanceof Road) {
                grid[startX + 1][startY + i] = new TrafficLight(startX + 1, startY + i, true);
            } else {
                grid[startX + 1][startY + i] = new Road(startX + 1, startY + i, speed, Directions.UP, lanes);
            }
        }
    }

    public void createHorizontalRoad(int startX, int startY, int width, int lanes, int speed){
        startX += startX % 2;
        startY += startY % 2;
        width  += width % 2;

        for (int i = 0; i < width; i++) {
            if (startX + i >= getLengthX()) break;

            if (grid[startX + i][startY] instanceof Road) {
                if (((Road) grid[startX + i][startY]).getDirection() == Directions.LEFT) break;

                grid[startX + i][startY] = new TrafficLight(startX + i, startY, true);
            } else {
                grid[startX + i][startY] = new Road(startX + i, startY, speed, Directions.LEFT, lanes);
            }

            if (grid[startX + i][startY + 1] instanceof Road) {
                grid[startX + i][startY + 1] = new TrafficLight(startX + i, startY + 1, true);
            } else {
                grid[startX + i][startY + 1] = new Road(startX + i, startY + 1, speed, Directions.RIGHT, lanes);
            }
        }
    }

    public boolean pointIsValid(int x, int y) {
        return 0 <= y && y < grid.length &&
               0 <= x && x < grid[y].length;
    }
    
    
    
    
    
    
    
    
    
    
    
    //Written by Car Logic
    public Tile getInDir(Tile t, int dir) {
    	int x = t.getX(); int y = t.getY();
    	switch(dir) {
    	case 0:
    		return get(x,y-1);
    	case 1:
    		return get(x+1,y);
    	case 2:
    		return get(x,y+1);
    	case 3:
    		return get(x-1,y);
    	default:
    		return t;
    	}
    }
}
