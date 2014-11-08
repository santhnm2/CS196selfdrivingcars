import java.util.ArrayList;
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
        map = new Map(testMap, new ArrayList<Car>());
//        map.createHorizontalRoad(3, 2, 1, 99, 5, 2);
//        map.createVerticalRoad(4, 2, 3, 99, 5, 2);
//        map.createHorizontalRoad(7, 2, 5, 99, 5, 2);
//        map.createVerticalRoad(16, 2, 4, 99, 5, 2);
        System.out.println(map);
        //JPanelRunner a = new JPanelRunner();
        Scanner scan=new Scanner(System.in);
        y[0] = new Car(2,4,18,8, map);
        y[1] = new Car(2,4,18,8, map);
        y[2] = new Car(1,4,18,8, map);
        y[3] = new Car(3,4,18,8, map);
        //y[0] = new Car(2,4,18,8, map);
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