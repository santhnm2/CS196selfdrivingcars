import java.util.ArrayList;
import java.util.Scanner;

import Car.Car;
import Map.Map;
import Map.RandomMapGenerator;
import Map.Tile;
import Map.NonRoad.House;
import Map.Road.Road;
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
    	
    	Car[] y = new Car[10];
    	
    	map = new Map(testMap, y);
    	
    	map.createHorizontalRoad(1,2,18,2,99);
        map.createHorizontalRoad(5,7,15,2,99);
        map.createVerticalRoad(5,2,16,2,99);
        map.createVerticalRoad(16,4,16,2,99);
    	System.out.println(map);
    	y[1]=new Car(4,3,16,19,map);
    	y[2]=new Car(4,3,16,19,map);
    	y[3]=new Car(4,3,16,19,map);
 /**   	for(int i=0;i<y.length;i++){
    		int x1,y1,x2,y2;
    		do {
    			 
    			x1=(int)(map.getLengthX()*Math.random());
    	    	y1=(int)(map.getLengthY()*Math.random());
    	    	x2=(int)(map.getLengthX()*Math.random());
    	    	y2=(int)(map.getLengthY()*Math.random());

    		}
    		while(!((map.get(x1, y1) instanceof Road) && (map.get(x2, y2) instanceof Road) ));
    		y[i]=new Car(x1,y1,x2,y2,map);
    		System.out.println(x1+" "+y1+" "+x2+" "+y2+" ");
    	}**/
    	/**ArrayList<Road> validPoints=new ArrayList<Road>();
    	for(int i=0;i<map.getLengthX();i++)
    	{
    		for(int j=0;j<map.getLengthY();j++)
    		{
    			if(map.get(i, j) instanceof Road && !(map.get(i, j) instanceof TrafficLight))
    			{
    				validPoints.add((Road)map.get(i,j));
    			}
    		}
    	}
    	for(int i=0;i<y.length;i++)
    	{
    		Tile randomStart=validPoints.get((int)(validPoints.size()*Math.random()));
    		Tile randomEnd=validPoints.get((int)(validPoints.size()*Math.random()));
    		y[i]=new Car(randomStart.getX(),randomStart.getY(),randomEnd.getX(),randomEnd.getY(),map);
    	}**/

    	//        map.createHorizontalRoad(3, 2, 1, 99, 5, 2);
//        map.createVerticalRoad(4, 2, 3, 99, 5, 2);
//        map.createHorizontalRoad(7, 2, 5, 99, 5, 2);
//        map.createVerticalRoad(16, 2, 4, 99, 5, 2);
        System.out.println(map);
        //JPanelRunner a = new JPanelRunner();
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