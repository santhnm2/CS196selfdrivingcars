package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Map.*;
import Map.NonRoad.House;
import Map.NonRoad.NonRoad;
import Map.Road.Road;
import Map.Road.TrafficLight;

import java.util.Arrays;
import java.util.Scanner;

import Car.Car;

public class JPanelRunner extends JFrame {
	static Map map;
    static int iterations = 0;
    private static Tile[][] testMap = new Tile[20][20];    
    static JPanel gui = new JPanel(new BorderLayout());
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                
            	// the GUI as seen by the user (without frame)

                gui.setBorder(new EmptyBorder(2, 3, 2, 3));
                gui.setBackground(Color.WHITE.darker().darker());
                init();
                int w = map.getLengthX();
                int h = map.getLengthY();
                gui.setLayout(new GridLayout(h, w, 2, 2));
                
                Scanner a=new Scanner(map.toString());
                while(a.hasNext()){
                //for (int ii=0; ii<w*h; ii++) {
                	String test=a.next();
                	Color c=null;
                	BufferedImage img;
                	try {                
                    	img = ImageIO.read(new File("C:\\Users\\Shim\\workspace\\CS196SelfDrivingCars\\src\\Graphics\\car.png"));
                     } catch (IOException ex) {
                          // handle exception...
                     }
/*
                	if(test.equals(".."))
                		 c= Color.WHITE ;
                	if(test.equals("GL"))
               		 c= Color.GREEN ;
                	if(test.equals("YL"))
                  		 c= Color.YELLOW ;
                	if(test.equals("RL"))
                  		 c= Color.RED ;
                	if(test.contains("R"))
               		 c= Color.BLACK ;
                	if(test.equals("::"))
               		 c= Color.PINK ;
  */				paint(map);
//                	gui.add(new JLabel(new ColorIcon(c, 16)));
                }

                JFrame f = new JFrame("Demo");
                f.add(gui);
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // should be done last, to avoid flickering, moving,
                // resizing artifacts.
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(r);
    }
    public static void paint(Map map){
    	   for (int i = 0; i < map.getLengthX(); i++) {
               for (int j = 0; j < map.getLengthY(); j++) {
               Color c=null;
            	if(map.get(i, j) instanceof NonRoad)
            		 c= Color.WHITE ;
            	if(map.get(i, j) instanceof TrafficLight)
           		 c= Color.GREEN ;
            	if(map.get(i, j) instanceof Road)
           		 c= Color.BLACK ;
   /*         	if(map.get(i, j) instanceof Car)
           		 c= Color.PINK ;
                */gui.add(new JLabel(new ColorIcon(c, 16)));
                System.out.println(i+" "+j);
               }
            }
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
    public static void init() {

        for(int i = 0; i < testMap.length; i++){
            for(int j = 0; j < testMap[0].length; j++){
                testMap[i][j] = new House(i,j);
            }
        }
        Car[] y = new Car[20];
        map = new Map(testMap, y);
        map.createHorizontalRoad(3, 2, 1, 99, 5, 2);
        map.createVerticalRoad(4, 2, 3, 99, 5, 2);
        map.createHorizontalRoad(7, 2, 5, 99, 5, 2);
        map.createVerticalRoad(16, 2, 4, 99, 5, 2);
        System.out.println(map);
        JPanelRunner a=new JPanelRunner();
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
        //y[0] = new Car(startx,starty , destx, desty, map);
        
        
   //     System.out.println(map);
    }
    
}

class ColorIcon implements Icon {

    Color color;
    int preferredSize = -1;

    private ColorIcon() {
    }

    public ColorIcon(Color color, int preferredSize) {
        this.color = color;
        this.preferredSize = preferredSize;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(0, 0, preferredSize, preferredSize);
    }

    @Override
    public int getIconWidth() {
        return preferredSize;
    }

    @Override
    public int getIconHeight() {
        return preferredSize;
    }
}