import java.util.Arrays;
import java.util.Scanner;

import Car.Car;
import Map.Map;
import Map.Tile;
import Map.NonRoad.House;
import Map.Road.TrafficLight;


public class CarLogicDemo {

    static Map map;
    static int iterations = 0;

    public static void main(String[] args) {
        long curTime = System.currentTimeMillis();
        Scanner kboard = new Scanner(System.in);
        init();
        boolean running=true;
        while(!kboard.nextLine().equals("end") && running) {
            step();
        }
    }
    public static void init() {
        Tile[][] testMap = new Tile[20][20];
        for(int i = 0; i < testMap.length; i++){
            for(int j = 0; j < testMap[0].length; j++){
                testMap[i][j] = new House(i,j);
            }
        }
        Car[] y = new Car[20];
        map = new Map(testMap, y);
        map.createHorizontalRoad(3, 2, 1, 99, 5);
        map.createVerticalRoad(4, 2, 3, 99, 5);
        map.createHorizontalRoad(7, 2, 5, 99, 5);
        map.createVerticalRoad(16, 2, 4, 99, 5);
        System.out.println(map);
        Scanner scan=new Scanner(System.in);
        System.out.println("Please set your statring point in the format x,y");
        String start=scan.nextLine();
        int startx= Integer.parseInt(start.substring(0,start.indexOf(",")));
        int starty= Integer.parseInt(start.substring(start.indexOf(",")+1));
        System.out.println("Please set your destination in the format x,y");
        String dest=scan.nextLine();
        int destx= Integer.parseInt(dest.substring(0,dest.indexOf(",")));
        int desty= Integer.parseInt(dest.substring(dest.indexOf(",")+1));        
        //Write different versions of y[0] here as test cases
        y[0] = new Car(startx,starty , destx, desty, map);
        
        
        System.out.println(map);
    }
    public static void step() {
        //Print out state
        iterations++;
        if(iterations % 10 == 0) { //Toggle all lights every ten iterations
            for(int i = 0; i < map.getLengthX(); i++) {
                for (int j = 0; j < map.getLengthY(); j++) {
                    Tile t = map.get(i, j);

                    if(t instanceof TrafficLight) {
                        TrafficLight l  = (TrafficLight)t;
                        l.toggle();
                    }
                }
            }
        }

        for(Car c:map.getCars()) {
            if(c!=null && c.getPath().size()>0) c.move();
        }

        System.out.println(map);
    }
}