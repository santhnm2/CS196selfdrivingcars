package cs196.cars.maps;

import cs196.cars.components.Car;
import cs196.cars.components.Directions;
import cs196.cars.components.Tile;
import cs196.cars.components.nonroads.Office;
import cs196.cars.components.roads.Road;
import cs196.cars.components.roads.TrafficLight;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arshsab
 * @since 10 2014
 */

public class TextFileMapGenerator implements MapGenerator {
    private final Tile[][] grid;

    private final String TRAFFIC_LIGHT = "TL",
                         UP_ROAD = "UR",
                         DOWN_ROAD = "DR",
                         RIGHT_ROAD = "RR",
                         LEFT_ROAD = "LR",
                         NON_ROAD = "NR";


    public TextFileMapGenerator(String path) throws IOException {
        this.grid = loadGrid(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
    }

    private Tile[][] loadGrid(BufferedReader br) throws IOException {
        String line;

        List<Tile[]> grid = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(" ");

            Tile[] row = new Tile[tokens.length];

            for (int i = 0; i < row.length; i++) {
                switch (tokens[i]) {
                    case TRAFFIC_LIGHT:
                        row[i] = new TrafficLight();
                        break;
                    case UP_ROAD:
                        row[i] = new Road(Directions.UP);
                        break;
                    case RIGHT_ROAD:
                        row[i] = new Road(Directions.RIGHT);
                        break;
                    case LEFT_ROAD:
                        row[i] = new Road(Directions.LEFT);
                        break;
                    case DOWN_ROAD:
                        row[i] = new Road(Directions.DOWN);
                        break;
                    case NON_ROAD:
                        row[i] = new Office();
                        break;
                }
            }

            grid.add(row);
        }

        return grid.toArray(new Tile[0][]);
    }

    @Override
    public Map generateMap() {
        return new Map(this.grid, new Car[0]);
    }
}
