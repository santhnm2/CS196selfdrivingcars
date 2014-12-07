package Map;

import Constants.Directions;
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

    public boolean checkCorn (Tile[][] grid, int corn_coord_x, int corn_coord_y, int middle_coord, int middle_l)
    {
        for (int i = corn_coord_x; i <= corn_coord_x + 8; i++)
        {
            for(int j = corn_coord_y; j <= corn_coord_y + 8; j++)
            {
                if((i >= middle_coord - middle_l / 2 && i <= middle_coord + middle_l / 2)
                        || (j >= middle_coord - middle_l / 2 && j <= middle_coord + middle_l / 2))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static int generateCoord (int l)
    {
        int a = (int) (Math.random() * l);
        return a;
    }

    public static boolean checkCoord (int x, int y, int l, int w, int map_length) {
        if ((x + l) > map_length || (y + w) > map_length) {
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean checkValidArea (Tile[][] t, int x, int y, int l, int w) {
        for (int i = x; i < x + l; i++) {
            for (int j = y; j < y + w; j++) {
                if (t[i][j].density != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean checkArea (int count, int l, int w, int total_area)
    {
        int area = l * w;

        if (count * area >= total_area)
        {
            return true;
        }

        return false;
    }

    @Override
    public Map generateMap() {
        Tile[][] grid = new Tile[length][length];
        int area = length * length;
        int x, y;

        int cornfield_area = (int) ((0.065 * area) % 64) * 64;
        int mall_area = (int) ((0.02 * area) % 10) * 10;
        int office_area = (int) ((0.025 * area) % 4) * 4;
        int house_area = (int) ((0.015 * area) - cornfield_area - mall_area - office_area);

        // corn
        int not_corn = (int) (0.27 * length);
        int corn_coord_x = (int) (length * Math.random());
        int corn_coord_y = (int) (length * Math.random());

        int middle_coord = (int) ((length / 2) - (0.27/2));
        int middle_l = middle_coord + not_corn;

        while (!checkCoord(corn_coord_x, corn_coord_y, 8, 8, length))
        {
            corn_coord_x = (int) (length * Math.random());
            corn_coord_y = (int) (length * Math.random());
        }

        while(!checkCorn (grid, corn_coord_x, corn_coord_y, middle_coord, middle_l))
        {
            corn_coord_x = (int) (length * Math.random());
            corn_coord_y = (int) (length * Math.random());
        }

        for (int i = corn_coord_x; i < corn_coord_x + 8; i++)
        {
            for (int j = corn_coord_y; j < corn_coord_y + 8; j++)
            {
                grid[i][j] = new Cornfield(i, j);
            }
        }

        boolean flag = true;
        int count = 0;

        //mall
        while (flag)
        {
            x = generateCoord(length);
            y = generateCoord(length);

            if (checkCoord(x, y, 5, 2, length) && checkValidArea(grid, x, y, 5, 2)) {
                for (int i = x; i < 5; i++) {
                    for (int j = y; j < 2; j++) {
                        grid[i][j] = new Mall(i, j);
                        count++;
                    }
                }
            }

            if (checkArea(count, 5, 2, mall_area))
            {
                flag = false;
            }
        }
        flag = true;
        count = 0;

        // office
        while (flag)
        {
            x = generateCoord(length);
            y = generateCoord(length);

            if (checkCoord(x, y, 2, 2, length) && checkValidArea(grid, x, y, 2, 2)) {
                for (int i = x; i < 2; i++) {
                    for (int j = y; j < 2; j++) {
                        grid[i][j] = new Office(i, j);
                        count++;
                    }
                }
            }

            if (checkArea(count, 2, 2, office_area))
            {
                flag = false;
            }
        }
        flag = true;
        count = 0;

        // house
        while (flag)
        {
            x = generateCoord(length);
            y = generateCoord(length);

            if (checkCoord(x, y, 1, 1, length) && checkValidArea(grid, x, y, 1, 1)) {
                for (int i = x; i < 1; i++) {
                    for (int j = y; j < 1; j++) {
                        grid[i][j] = new House(i, j);
                        count++;
                    }
                }
            }

            if (checkArea(count, 1, 1, house_area))
            {
                flag = false;
            }
        }
        flag = true;
        count = 0;

        Map m = new Map(grid, new ArrayList<Car>());

        while (flag) {
            for (int i = 0; i < length; i += 4) {
                if (Math.random() < .85) {
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;


                    for (int k = start; k < start + width; k++) {
                        if (!(grid[k][i] instanceof NonRoad)) {
                            m.createHorizontalRoad(start, i, width, 2, 4);
                            count++;
                        }
                    }
                }
            }

            if (count == (3/4)*length)
            {
                flag = false;
            }
        }

        flag = true;
        count = 0;

        while (flag) {
            for (int i = 0; i < length; i += 4) {
                if (Math.random() < .85) {
                    int start = (int) (Math.random() * length / 2) * 2;
                    int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;


                    for (int k = start; k < start + width; k++) {
                        if (!(grid[k][i] instanceof NonRoad)) {
                            m.createVerticalRoad(i, start, width, 2, 4);
                            count++;
                        }
                    }
                }
            }

            if (count == (3/4)*length)
            {
                flag = false;
            }
        }

        //}

               /* for (int i = 0; i < length; i += 4){
                    if (Math.random() < .85) {
                        int start = (int) (Math.random() * length / 2) * 2;
                        int width = (int) (Math.random() * (length - start - 6) / 2) * 2 + 6;

                        m.createVerticalRoad(i, start, width, 2, 4);
                    }
                }
            }*/


        removeIslands(m);

        ArrayList<Road> roads = new ArrayList<Road>();

        for (Tile[] a : grid) {
            for (Tile b : a) {
                if (b instanceof Road && !(b instanceof TrafficLight)) {
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

            m.placeCar(new Car(start.x, start.y, end.x, end.y, m));
        }


        return m;
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