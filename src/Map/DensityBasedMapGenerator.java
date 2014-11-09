package Map;

import Map.NonRoad.House;
import Car.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author arshsab
 * @since 10 2014
 */

public class DensityBasedMapGenerator implements MapGenerator {
    private final Tile[][] grid;

    public DensityBasedMapGenerator(Tile[][] grid) {
        this.grid = grid;
    }

    @Override
    public Map generateMap() {
        Map map = new Map(grid, new ArrayList<Car>());


        int[][] visits = simulatePaths(1000, grid);

        convertPaths(visits, grid);

        return map;
    }


    private int[][] simulatePaths(int iterations, Tile[][] grid) {
        throw new RuntimeException("todo");
    }

    private void convertPaths(int[][] visits, Tile[][] grid) {
        throw new RuntimeException("todo");
    }

    public static void main(String[] args) {
        Tile[][] tile = new Tile[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                tile[i][j] = new House(i, j);
            }
        }

        MapGenerator x = new DensityBasedMapGenerator(tile);
        Car[] cars = new Car[5];
        Map y = x.generateMap();
        System.out.println(y);

    }
}