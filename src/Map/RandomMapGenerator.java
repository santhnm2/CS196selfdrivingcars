package Map;

import Constants.Directions;
import Map.NonRoad.House;
import Map.NonRoad.*;
import Car.Car;
import Map.Road.Road;
import Map.Road.TrafficLight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author arshsab
 * @since 10 2014
 */

public class RandomMapGenerator implements MapGenerator {
    private static final double DENSITY = 0.34;

    private final int length;
    private final int cars;

    public RandomMapGenerator(int length, int cars) {
        this.length = length;
        this.cars = cars;
    }

    @Override
    public Map generateMap() {
        Tile[][] grid = new Tile[length][length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new House(i, j);
            }
        }

        Map m = new Map(grid, new ArrayList<Car>());

        for (int t = 0; t < 3; t++) {
            for (int i = 0; i < length; i += 4){
                if(Math.random() < .85){
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;

                    m.createHorizontalRoad(start, i, width, 2, 2);
                }
            }

            for (int i = 0; i < length; i += 4){
                if (Math.random() < .85) {
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;

                    m.createVerticalRoad(i, start, width, 2, 2);
                }
            }
        }

        ArrayList<Road> roads = new ArrayList<Road>();

        for (Tile[] a : grid) {
            for (Tile b : a) {
                if (b instanceof Road && !(b instanceof TrafficLight)) {
                    roads.add((Road) b);
                }
            }
        }
     //   if(!assignSpeeds(m, roads))
       //     for(int i = 0; i < 30; i++) System.out.println("MAPGEN DONE GOOFED");

        for (int i = 0; i < cars; i++) {
            Road start, end;

            do {
                start = roads.get((int) (roads.size() * Math.random()));
                end   = roads.get((int) (roads.size() * Math.random()));
            } while (start == end);

            m.placeCar(new Car(start.x, start.y, end.x, end.y, m));
        }


        return m;
    }
    private static Tile[] getAdjTiles(Tile[][] grid, Tile x){
        Tile[] y = new Tile[4];
        if(!(x.getX() == 0))
            y[3] = grid[x.getX() - 1][x.getY()];
        if(!(x.getY() == 0))
            y[0] = grid[x.getX()][x.getY() - 1];
        if(!(x.getX() == grid.length - 1))
            y[1] = grid[x.getX() + 1][x.getY()];
        if(!(x.getY() == grid[0].length - 1))
            y[2] = grid[x.getX()][x.getY() + 1];


        return y;
    }
    private boolean assignSpeeds(Map m, ArrayList<Road> roads){
        int[][] speeds = new int[m.getLengthX()][m.getLengthY()];
        for(Road r : roads){
            Tile[] adjTiles = getAdjTiles(m.grid, m.grid[r.getX()][r.getY()]);
            Road start = null;
            for(int i = 0; i < 4; i++){
                if(adjTiles[i] != null && adjTiles[i] instanceof TrafficLight){
                    start = r;
                }
            }
       //     m.pointIsValid(m.getInDir(r, dir).getX(), m.getInDir(r,dir).getY())
            if(start != null){
                int dir = start.getDirection();
                int length = 1;
                Tile t = r;
                if(m.getInDir(r,dir) != null){
                    System.out.println(m.getInDir(r, dir).getX() +" " + m.getInDir(r,dir).getY());
                    t = m.getInDir(r, dir);

                    if(t instanceof TrafficLight){
                       int reverseDir = (dir + 2 > 3)? dir - 2 : dir + 2;
                     if(m.getInDir(r, reverseDir) != null)
                         t = m.getInDir(r, reverseDir);
                     while(!(t == null || t instanceof TrafficLight || t instanceof NonRoad)){
                         length++;
                         if(m.getInDir(t, reverseDir) != null)
                            t = m.getInDir(t, reverseDir);
                         else
                             break;
                     }
                     for(int i = 0; i < length; i++){
                         t = m.getInDir(t, dir); // reverse direction
                         speeds[t.getX()][t.getY()] = determineSpeed(length);
                     }

                    } else {
                     while(!(t == null || t instanceof TrafficLight || t instanceof NonRoad)){
                         length++;
                         if(m.getInDir(t, dir) != null)
                             t = m.getInDir(t, dir);
                         else
                             break;
                        }
                        int reverseDir = (dir + 2 > 3)? dir - 2: dir + 2;
                     for(int i = 0; i < length; i ++){
                         t = m.getInDir(t, reverseDir); //reverse directions
                         speeds[t.getX()][t.getY()] = determineSpeed(length);
                     }

                    }
                }
            }
        }
        for(Road r : roads){
            r.setSpeed(speeds[r.getX()][r.getY()]);
        }
        return true;
    }
    private int determineSpeed(int length){
        if(length <= 4) return 1;
        if(length <= 8) return 2;
        return 3;

    }
    private void removeIslands(Map m) {
        int[][] dp = new int[length][length];

        int num = 1;

        for (int[] arr : dp) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }

        int max = -1;
        int maxNum = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (m.grid[i][j] instanceof House) {
                    dp[i][j] = 0;
                } else if (dp[i][j] > num) {
                    int count = floodfill(m, dp, num, i, j);

                    if (count > max) {
                        max = count;
                        maxNum = num;
                    }

                    ++num;

                }
            }
        }

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] != maxNum) {
                    m.grid[i][j] = new House(i, j);
                }
            }
        }

    }

    private int floodfill(Map m, int[][] dp, int val, int x, int y) {
        Queue<Coord> q = new ArrayDeque<Coord>();

        q.add(new Coord(x, y));

        dp[x][y] = val;
        int count = 1;
        while (!q.isEmpty()) {
            Coord nex = q.poll();

            boolean[] valid = m.generateValidMoves(nex.x, nex.y);

            for (int i = 0; i < 4; i++) {
                if (!valid[i]) continue;

                int altX = nex.x + Directions.dx[i];
                int altY = nex.y + Directions.dy[i];

                if (!m.pointIsValid(altX, altY)
                        || dp[altX][altY] <= val) continue;

                dp[altX][altY] = val;
                q.add(new Coord(altX, altY));

                count++;
            }
        }


        return count;
    }

    private class Coord {
        final int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x << 15 + y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != getClass()) {
                return false;
            }

            Coord other = (Coord) o;

            return this.x == other.x && this.y == other.y;
        }

        @Override
        public String toString() {
            return "(" + x + " " + y + ")";
        }
    }

    public static void main(String[] args) {
        MapGenerator mapGen = new RandomMapGenerator(30, 1);

        System.out.println(mapGen.generateMap());
    }
}