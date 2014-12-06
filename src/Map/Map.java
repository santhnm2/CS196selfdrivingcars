package Map;

import Car.Car;
import Constants.Directions;
import Map.NonRoad.NonRoad;
import Map.Road.Intersection;
import Map.Road.Road;
import Map.Road.TrafficLight;

import java.util.ArrayList;


public class Map implements java.io.Serializable {
    private static final String Road = null;
    final Tile[][] grid;

    private final ArrayList<Car> cars;
    private TrafficLightHandler handler = new TrafficLightHandler(new ArrayList<Intersection>());

    public Map(Tile[][] grid, ArrayList<Car> cars) {
        this.grid = grid;
        this.cars = cars;
    }

    public Tile get(int x, int y) {
        if (0 <= x && x < grid.length &&
                0 <= y && y < grid[x].length) {

            return this.grid[x][y];
        }

        return null;
    }

    public int getLengthX() {
        return grid[0].length;
    }

    public int getLengthY() {
        return grid.length;
    }

    public ArrayList<Car> getCars() {
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

    void createVerticalRoad(int startX, int startY, int length, int lanes, int speed){
        startX += startX % 2;
        startY += startY % 2;
        length  += length % 2;

        for (int i = 0; i < length; i++) {
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

        fixLights();
    }

    private void fixLights() {
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();

        for (int i = 0; i < grid.length; i += 2) {
            for (int j = 0; j < grid[i].length; j += 2) {
                if (grid[i][j] instanceof TrafficLight) {
                    int[] dx = { 0, 1, 1, 0 };
                    int[] dy = { 0, 0, 1, 1 };

                    ArrayList<TrafficLight> l = new ArrayList<TrafficLight>();

                    for (int k = 0; k < 4; k++)
                        l.add((TrafficLight) grid[i + dx[k]][j + dy[k]]);


                    intersections.add(new Intersection(l, this));
                }
            }
        }

        this.handler = new TrafficLightHandler(intersections);
    }

    void placeCar(Car c) {
        cars.add(c);
    }

    void createHorizontalRoad(int startX, int startY, int length, int lanes, int speed){
        startX += startX % 2;
        startY += startY % 2;
        length  += length % 2;

        for (int i = 0; i < length; i++) {
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

        fixLights();
    }

    public boolean[] generateValidMoves(int x, int y) {
        Tile curr = get(x, y);

        if (curr instanceof NonRoad)
            return new boolean[4];

        boolean[] valid = new boolean[4];

        if (curr instanceof TrafficLight) {
            for (int i = 0; i < 4; i++) {
                if (get(x + Directions.dx[i], y + Directions.dy[i]) instanceof Road) {
                    valid[i] = true;
                }
            }

            return valid;
        }

        Road r = (Road) curr;

        int dir = r.getDirection();

        if (get(x + Directions.dx[dir], y + Directions.dy[dir]) instanceof Road) {
            Road potential = (Road) get(x + Directions.dx[dir], y + Directions.dy[dir]);

            if (potential.getDirection() == dir || potential instanceof TrafficLight)
                valid[dir] = true;
        }

        boolean anyValid = valid[0] || valid[1] || valid[2] || valid[3];

        int altDir = (dir + 3) % 4;

        if (getInDir(curr, altDir) instanceof Road) {
            Road adj = (Road) getInDir(curr, altDir);

            if ((adj.getDirection() + 2) % 4 == dir) {
                valid[altDir] = true;
            }
        }

        return valid;
    }

    public boolean pointIsValid(int x, int y) {
        return 0 <= y && y < grid.length &&
               0 <= x && x < grid[y].length;
    }

    public void destroyCar(Car c) {
    	Road r=(Road)get(c.getXPos(),c.getYPos());
    	r.carDecrement(c);
    	cars.remove(c);
    	
    }

    //Written by Car Logic
    public Tile getInDir(Tile t, int dir) {
    	int x = t.getX();
        int y = t.getY();


    	return get(x + Directions.dx[dir], y + Directions.dy[dir]);
    }

    public TrafficLightHandler getHandler() {
        return handler;
    }
}
