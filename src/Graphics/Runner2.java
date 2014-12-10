package Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import Car.Car;
import FileIO.MapIO;
import Map.Map;
import Map.RandomMapGenerator;
import Map.Tile;
import Map.NonRoad.NonRoad;
import Map.Road.Road;
import Map.Road.TrafficLight;

public class Runner2 extends JFrame{
	static int iterations = 0;
	static JPanel gui = new JPanel(new BorderLayout());
	static JLabel [][] labels;
	static boolean run=true;
	// for ease of access during demo
	static int size=40;//size of the map
	static int cars=100;//number of cars
	static int sizeBox=14;//size of each box
	static Map map;
	static JFrame f ;
	static RandomMapGenerator generator;
	static boolean tLOptimized;
	static boolean stopped = true;
	
	public static void main(String[] args) throws InterruptedException {
	
		gui.setBorder(new EmptyBorder(2, 3, 2, 3));
		gui.setBackground(Color.WHITE.darker().darker());
		generator = new RandomMapGenerator(size, cars);

		map = generator.generateMap();
		int w = map.getLengthX();
		int h = map.getLengthY();
		gui.setLayout(new GridLayout(h, w, 2, 2));
		labels = new JLabel[w][h];
		for (int i = 0; i < w; i++) 
			for (int j = 0; j < h; j++)
			{
				labels[i][j] = new JLabel();
				gui.add(labels[i][j]);
			}
		color(gui, map);
		f = new JFrame("Demo");
		f.add(gui);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);
		Jbutton(f);
		addMenus(f);
		f.pack();
		f.setVisible(true);
		run();
	}
		private static void run() throws InterruptedException{
			while(run){
				gui.removeAll();
				if(!stopped) {
					step(map);
				}
				sizeBox=f.getWidth()/size;
				color(gui, map);
				f.pack();
				gui.updateUI();
				Thread.sleep(500);
			}
//		color(gui, map);
//		f.pack();
//		while(run){
//		gui.removeAll();
//		
//		sizeBox=f.getWidth()/size;
//		color(gui, map);
//		step(map);
//		gui.updateUI();
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
//		count++;
		}

	private static void color(JPanel gui, Map map) {
		paint(gui, map);
//		for (int i = 0; i < map.getLengthX(); i++) {
//			for (int j = 0; j < map.getLengthY(); j++) {
//				
//				if (map.get(j, i) instanceof TrafficLight) {
//					TrafficLight light = (TrafficLight) map.get(j, i);
//
//					if (light.isRed()) {
//						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
//						JLabel myLabel = new JLabel(new ColorIcon(Color.RED, sizeBox));
//						gui.add(myLabel);
//						myLabel.setLocation(i, j);
//
//					}
//					else
//					{
//						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
//						JLabel myLabel = new JLabel(new ColorIcon(Color.GREEN, sizeBox));
//						gui.add(myLabel);
//						myLabel.setLocation(i, j);
//					}
//				} else if (map.get(j, i) instanceof NonRoad) {
//					JLabel myLabel = new JLabel(new ColorIcon(Color.WHITE, sizeBox));
//					gui.add(myLabel);
//					myLabel.setLocation(i, j);
////					//paint1(gui, map, i, j);
//					//System.out.println("i = " + i + ", j = " + j);
//				} else if (map.get(j, i) instanceof Road) {
//					Road car = (Road) map.get(j, i);
//					if (car.getFilled() > 0)
//					{	int fill=car.getFilled();
//						
//						JLabel myLabel = new JLabel(new ColorIcon(Color.PINK, sizeBox,fill));
//						gui.add(myLabel);
//						myLabel.setLocation(i, j);
//					}
//					else
//					{
//						labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
//						JLabel myLabel = new JLabel(new ColorIcon(Color.BLACK, sizeBox));
//						gui.add(myLabel);
//						myLabel.setLocation(i, j);	
//					}
//				}
//			}
//		}
		//paint(gui, map);
	}


	//private static ArrayList<Integer> paint_buffer;
	private static void paintWithColor(JPanel gui, Map map, Color color, int x, int y) {
			JLabel myLabel = new JLabel(new ColorIcon(color, sizeBox));
			gui.add(myLabel);
			myLabel.setLocation(x, y);
			gui.add(myLabel);
			
	}
	private static int CT=0;
	private static int CT2=0;
	private static void paintWithBuffer(Color[][] mapColor, int x, int y, int height, int width){
		int non_road_counter = height*width;
		Color color;
		if (non_road_counter<5){
			 color = Color.YELLOW;
		}else if(non_road_counter<15){
			if (CT2%2==0){
				color = Color.CYAN;
			}else{
				color = Color.ORANGE;
			}
			CT2++;
		}else {
			if (CT%2==0){
				color = Color.BLUE;
			}else{
				color = Color.magenta;
			}
			CT++;
		}
		for (int i=y; i<y+height; i++){
			for (int j=x; j<x+width; j++){
				mapColor[i][j] = color;
			}
		}
	}
	private static boolean is_non_road(int x, int y, int height, int width, Map map, Color[][] mapColor){
		for (int i=y; i<y+height; i++){
			for (int j=x; j<x+width; j++){
				if (!(map.get(j, i) instanceof NonRoad)){
					return false;
				}
			}
		}
		return true;
	}
	
	private static void floodFilled(int x, int y, Map map, Color[][] mapColor){
		if ((map.get(x, y) instanceof NonRoad) && mapColor[y][x]==null){
			int height = 1;
			int width = 1;
			mapColor[y][x] = Color.WHITE;
			while (is_non_road(x, y, height, width, map, mapColor) && width<5){
				width++;
			}
			width--;
			while (is_non_road(x, y, height, width, map, mapColor) && height<5) {
				height++;
			}
			height--;
			paintWithBuffer(mapColor, x, y, height, width);
//			paint_buffer.add(x);
//			paint_buffer.add(y);
//			mapColor[y][x] = Color.WHITE;
//			for (int i=0; i<4; i++){
//				int newX = x+neighbour[i][0];
//				int newY = y+neighbour[i][1];
//				if (newX>=0 && newX<map.getLengthX() && newY>=0 && newY<map.getLengthY()){
//					floodFilled(newX, newY, map, mapColor);
//				}
//			}
		}else{
			
			if (map.get(x, y) instanceof TrafficLight){
				TrafficLight light = (TrafficLight) map.get(x, y);
				if (light.isRed()){
					mapColor[y][x] = Color.RED;
				}else{
					mapColor[y][x] = Color.GREEN;
				}
			}else if (map.get(x, y) instanceof Road){
				Road car = (Road) map.get(x, y);
				if (car.getFilled()>0){
					mapColor[y][x] = Color.PINK;
				}else{
					mapColor[y][x] = Color.BLACK;
				}
			}
		}
	}
	public static void paint(JPanel gui, Map map) {
		Color[][] mapColor = new Color[map.getLengthY()][map.getLengthX()];
		for (int i=0; i<map.getLengthX(); i++){
			for (int j=0; j<map.getLengthY(); j++){
				floodFilled(i, j, map, mapColor);
			}
		}
		for (int i=0; i<map.getLengthY(); i++){
			for (int j = 0; j < map.getLengthX(); j++) {
				//System.out.print(mapColor[i][j]);
				paintWithColor(gui, map, mapColor[i][j], j, i);
			}
			//System.out.println();
		}
		//System.out.println("==============");
		CT = 0;			//optimization, avoid huge chunk of boxes
		CT2 = 0;
	}
	static class ColorIcon implements Icon {

		Color color;
		int preferredSize = -1;
		int cars=0;
		//private ColorIcon() {
		//}
		public ColorIcon(Color color, int preferredSize) {
			this.color = color;
			this.preferredSize = preferredSize;
		}

		public ColorIcon(Color color, int preferredSize, int cars) {
			this.color = color;
			this.preferredSize = preferredSize;
			this.cars=cars;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, preferredSize, preferredSize);
			if(this.cars>0){
				
				g.setColor(Color.black);
				for(int a=0;a<this.cars;a++)
					g.drawOval(x+a*3, y+a*3, 3, 3);
				g.setColor(Color.WHITE);
			}
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
//i was here
	public static void step(Map map) {
		//Print out state
				iterations++;
				ArrayList<Intersection> intersections=map.getHandler().intersections;
				if(tLOptimized) {
					for(int i = 0; i < intersections.size(); i++) {
						if(intersections.get(i).shouldToggle()) {
							intersections.get(i).toggle();
						}
					}
				}
				else {
					if(iterations % 10 == 0) { //Toggle all lights every ten iterations
						for(int i = 0; i < intersections.size(); i++) {
							if(intersections.get(i).shouldToggle()) {
								intersections.get(i).toggle();
							}
						}
					}
				}
				int t=0;
				while(!haveAllMoved()&&t<=5)
				{
					
					for (Car c : map.getCars()) {
						if (c != null && c.getPath().size() > 0)
							if(!c.hasMoved) c.move();
						else if(c!=null)
							c.hasMoved=true;
							
					}
					t++;
					
				}
				for (Car c : map.getCars()) {
					c.hasMoved=false;
				}
				
				for (int i=0;i<map.getCars().size();i++) {
					if(map.getCars().get(i).getPath().size()==0)
						{	
							map.destroyCar(map.getCars().get(i));
							
							i--;
						}
						
				}
				//System.out.println(map);
			}	private static boolean haveAllMoved()
		{
			for(Car c: map.getCars())
			{
				if(!c.hasMoved)
					return false;
			}
			return true;
		}

		
			public static void Jbutton(final JFrame f) {
				JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
				JButton start = new JButton("Start");
				JButton stop = new JButton("Stop");
		        JButton step = new JButton("Step");

		        start.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						stopped = false;
					}
				});
		        stop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						stopped = true;
					}
				});
				
		        step.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						gui.removeAll();
						step(map);
						color(gui, map);
						f.pack();
						//run=true;
//						try {
//							run();
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					 }
				});
		        
		        vertical.add(start);
		        vertical.add(stop);
		        vertical.add(step);
//		        add(label, BorderLayout.WEST);
		        f.add(vertical, BorderLayout.WEST); 
		        f.pack();
			
			}


			public static void addMenus(final JFrame frame) {
				JMenuBar menubar = new JMenuBar();
				JMenu fileMenu = new JMenu("File");
				JMenu optimizations = new JMenu("Optimizations");
				menubar.add(fileMenu);

				JMenuItem save = new JMenuItem("Save");
				JMenuItem load = new JMenuItem("Load");
				fileMenu.add(save);
				fileMenu.add(load);
				
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						String path = getFilePath(frame);
						MapIO.saveMap(map, path);
					}
				});
				load.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						String path = getFilePath(frame);
						map = MapIO.loadMap(path);
					}
				});
			    JMenu settingsMenu = new JMenu("Settings");
				JMenuItem StopItem = new JMenuItem("Stop");
				JMenuItem StartItem = new JMenuItem("Start (doesnt work yet)");
				JMenuItem minimizeTime = new JMenuItem("Time");
				JMenuItem minimzeDistance = new JMenuItem("Distance");
				JMenuItem base = new JMenuItem("Baseline");

				settingsMenu.add(optimizations);
				optimizations.add(base);
		      optimizations.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent ae) {
		            map.setPath(2);
		         }
		      });
		      optimizations.add(minimzeDistance);
		      optimizations.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent ae) {
		            map.setPath(1);
		         }
		      });
		      optimizations.add(minimizeTime);
		      optimizations.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent ae) {
		            map.setPath(0);
		         }
		      });
				
				menubar.add(settingsMenu);
				
				final JCheckBoxMenuItem tL = new JCheckBoxMenuItem("TrafficLight Optimizations", tLOptimized);
				tL.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						tLOptimized = !tLOptimized;
						tL.setState(tLOptimized);
					}
				});
				settingsMenu.add(tL);		
				frame.setJMenuBar(menubar);
				frame.pack();

			}
			public static String getFilePath(JFrame frame) {
				String s = (String)JOptionPane.showInputDialog(frame, "Enter a file path and name", "File Path", JOptionPane.PLAIN_MESSAGE, null, null, "maps/map1.map");
				return s;
			}
}