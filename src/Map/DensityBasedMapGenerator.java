package Map;

import Constants.Directions;
import Map.NonRoad.Cornfield;
import Map.NonRoad.House;
import Car.Car;
import Map.NonRoad.Mall;
import Map.NonRoad.Office;
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

public class DensityBasedMapGenerator implements MapGenerator {
    private static final double DENSITY = 0.34;

    private final int length;
    private final int cars;
    private final double[][] densities;

    public DensityBasedMapGenerator(int length, int cars) {
        this(length, cars, new double[length][length]);
    }

    public DensityBasedMapGenerator(int length, int cars, double[][] densities) {
        this.length = length;
        this.cars = cars;
        this.densities = densities;
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

        final int val = length / 4;

        for (int t = 0; t < 12; t++) {
            for (int i = 0; i < length; i += 4){
                int start = (int) (Math.random() * length / 2) * 2;
                int width = (int) (Math.random() * (length - start - val) / 2) * 2 + val;

                width = Math.min(width, length - start - 1);

                if (Math.random() < (densities[start][i] + densities[start + width][i]) / 2) {
                    m.createHorizontalRoad(start, i, width, 2, 4);
                }
            }

            for (int i = 0; i < length; i += 4){
                int start = (int) (Math.random() * length / 2) * 2;
                int width = (int) (Math.random() * (length - start - val) / 2) * 2 + val;

                width = Math.min(width, length - start - 1);


                if (Math.random() < (densities[start][i] + densities[start + width][i]) / 2) {
                    m.createVerticalRoad(i, start, width, 2, 4);
                }
            }
        }


        if (removeIslands(m) >= 4)
            return generateMap();


        ArrayList<Road> roads = new ArrayList<Road>();

        for (Tile[] a : grid) {
            for (Tile b : a) {
                if (b instanceof Road && !(b instanceof TrafficLight)) {
                    for (int i = 0; i < (int) (densities[b.getX()][b.getY()] * 100); i++)
                        roads.add((Road) b);
                }
            }
        }

        for (int i = 0; i < cars; i++) {
            Road start, end;

            do {
                start = roads.get((int) (roads.size() * Math.random()));
                end   = roads.get((int) (roads.size() * Math.random()));
            } while (start == end);

            m.placeCar(new Car(start.x, start.y, end.x, end.y, m, 0));
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] instanceof Road) continue;

                int fixed = (int) Math.round(densities[i][j] / .25);

                switch (fixed) {
                    case 0:
                        grid[i][j] = new Cornfield(i, j);
                        break;
                    case 1:
                        grid[i][j] = new House(i, j);
                        break;
                    case 2:
                        grid[i][j] = new Mall(i, j);
                        break;
                    case 3:
                        grid[i][j] = new Office(i, j);
                        break;
                }
            }
        }

        return m;
    }

    public void spot(int x, int y) {
        spot(x, y, 1.0);
    }

    private void spot(int x, int y, double a) {
        if (0 <= x && x < length &&
                0 <= y && y < length &&
                densities[x][y] == 0.0 &&
                a > 0.0) {

            densities[x][y] += a;
            densities[x][y] = Math.min(densities[x][y], 1.0);


            for (int i = 0; i < 4; i++) {
                int altX = x + Directions.dx[i];
                int altY = y + Directions.dy[i];

                spot(altX, altY, a - 0.03);
            }
        }
    }

    private int removeIslands(Map m) {
        int[][] dp = new int[length][length];

        int num = 1;

        for (int[] arr : dp) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }

        int max = -1;
        int total = 0;
        int maxNum = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (m.grid[i][j] instanceof House) {
                    dp[i][j] = 0;
                } else if (dp[i][j] > num) {
                    int count = floodfill(m, dp, num, i, j);

                    total += count;

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

        return num;
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
        DensityBasedMapGenerator mapGen = new DensityBasedMapGenerator(50, 1);
        mapGen.spot(1, 1);
        mapGen.spot(20, 20);

        mapGen.spot(40, 40);
        
        Map map = mapGen.generateMap();

        System.out.println(map);
        System.out.println(map.getCars());
    }


    public static String arrayToString(double[][] arr) {
        StringBuilder total = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                total.append(arr[j][i]).append(" ");
            }

            total.append('\n');
        }

        return total.toString();
    }
}