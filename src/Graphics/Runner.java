package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import Car.Car;
import Map.Map;
import Map.RandomMapGenerator;
import Map.Tile;
import Map.NonRoad.NonRoad;
import Map.Road.Intersection;
import Map.Road.Road;
import Map.Road.TrafficLight;

public class Runner extends JFrame{
	static int iterations = 0;
	static JPanel gui = new JPanel(new BorderLayout());
	static JLabel [][] labels;
	static boolean run=true;
	// for ease of access during demo
	static int size=20;//size of the map
	static int cars=100;//number of cars
	static int sizeBox=14;//size of each box
	static Map map;
	static 	JFrame f ;
	static Thread mythread=new MyThread();
	public static void main(String[] args) throws InterruptedException {
		gui.setBorder(new EmptyBorder(2, 3, 2, 3));
		gui.setBackground(Color.WHITE.darker().darker());
		RandomMapGenerator generator = new RandomMapGenerator(size, cars);

		map = generator.generateMap();
		int w = map.getLengthX();
		int h = map.getLengthY();
		gui.setLayout(new GridLayout(h, w, 2, 2));
		labels = new JLabel[w][h];
		System.out.println(map.toString());
		for (int i = 0; i < w; i++) 
			for (int j = 0; j < h; j++)
			{
				labels[i][j] = new JLabel();
				gui.add(labels[i][j]);
			}
		f = new JFrame("Demo");
		f.add(gui);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);
		Jbutton(f);
		addMenus(f);
		f.pack();
		f.setVisible(true);
	}
		public static class MyThread extends Thread
		{	
			public void run(){
			color(gui, map);
			f.pack();
			while(run){
			gui.removeAll();
			
			sizeBox=f.getWidth()/size;
			color(gui, map);
			step(map);
			gui.updateUI();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			}
	}
	private static void run() throws InterruptedException{
		while(run){
			gui.removeAll();
			step(map);
			color(gui, map);
			f.pack();
			gui.updateUI();
			Thread.sleep(500);
		}
	}
	private static void color(JPanel gui, Map map) {
		for (int i = 0; i < map.getLengthX(); i++) {
			for (int j = 0; j < map.getLengthY(); j++) {
				
				if (map.get(j, i) instanceof TrafficLight) {
					TrafficLight light = (TrafficLight) map.get(j, i);

					if (light.isRed()) {
						Image image=null;
						try {
							 image = ImageIO.read(new File("red.jpg"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JLabel myLabel = new JLabel(new ColorIcon(image, sizeBox));
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						//JLabel myLabel = new JLabel(new ColorIcon(Color.RED, sizeBox));
						gui.add(myLabel);
						myLabel.setLocation(i, j);

					}
					else
					{Image image=null;
					try {
						 image = ImageIO.read(new File("green.jpg"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JLabel myLabel = new JLabel(new ColorIcon(image, sizeBox));
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
					//	JLabel myLabel = new JLabel(new ColorIcon(Color.GREEN, sizeBox));
						gui.add(myLabel);
						myLabel.setLocation(i, j);
					}
				} else if (map.get(j, i) instanceof NonRoad) {
					//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
					Image image=null;
					try {
						 image = ImageIO.read(new File("grass.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JLabel myLabel = new JLabel(new ColorIcon(image, sizeBox));
					//JLabel myLabel = new JLabel(new ColorIcon(Color.WHITE, sizeBox));
					gui.add(myLabel);
					myLabel.setLocation(j, i);
				} else if (map.get(j, i) instanceof Road) {
					Road car = (Road) map.get(j, i);
					if (car.getFilled() > 0)
					{	int fill=car.getFilled();
					Image image=null;
					try {
						 image = ImageIO.read(new File("cae.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JLabel myLabel = new JLabel(new ColorIcon(image, sizeBox));
						//JLabel myLabel = new JLabel(new ColorIcon(Color.PINK, sizeBox,fill));
						gui.add(myLabel);
						myLabel.setLocation(i, j);
					}
					else
					{
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						JLabel myLabel = new JLabel(new ColorIcon(Color.BLACK, sizeBox));
						gui.add(myLabel);
						myLabel.setLocation(i, j);	
					}
				}
			}
		}
	}
	
	static class ColorIcon implements Icon {
		Image image;
		Color color;
		int preferredSize = -1;
		int cars=0;
		private ColorIcon() {
		}
		public ColorIcon(Image image, int preferredSize){
			this.image=image;
			this.preferredSize=preferredSize;
		}
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
			//g.setColor(color);
			//g.fillRect(x, y,preferredSize ,preferredSize);
			g.drawImage(image, x, y, sizeBox,sizeBox,Color.black, null);
			if(this.cars>0){
				
				g.setColor(Color.black);
				for(int a=0;a<this.cars;a++)
					g.fillOval(x+a*sizeBox/3, y, sizeBox/3,sizeBox/3);
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

	public static void step(Map map) {
		//Print out state
		iterations++;
		if(iterations % 10 == 0) { //Toggle all lights every ten iterations
			for(int i = 0; i < map.getLengthX(); i++) {
				for (int j = 0; j < map.getLengthY(); j++) {
					Tile t = map.get(i, j);

					if(t instanceof TrafficLight) {
						TrafficLight l  = (TrafficLight) t;
						l.toggle();
					}
				}
			}
		}

		for (Car c : map.getCars()) {
			if (c != null && c.getPath().size() > 0)
				c.move();
		}

		//System.out.println(map);
	}
	public static void Jbutton(final JFrame f) {
		JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
		JButton stop = new JButton("Stop");
        JButton step = new JButton("Quick Pause(5000 mil)");
        JButton time = new JButton("Start");
        JButton dist = new JButton("Distance efficient");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mythread.stop();
			}
		});
		
        step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mythread.resume(); 
			}
		});
        time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mythread.start();
			}
		});
        dist.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent ae) {
    			for(int a=0;a<cars;a++)
    				map.getCars().get(a).setPath(1);
    			Thread mythread=new MyThread();
				mythread.start();
			
    			}
    			
    		});
           
        vertical.add(stop);
        vertical.add(step);
        vertical.add(time);
       // vertical.add(dist);
//        add(label, BorderLayout.WEST);
        f.add(vertical, BorderLayout.WEST
        		); 
        //f.pack();
	
	}


	public static void addMenus(final JFrame frame) {
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu Opt = new JMenu("Optimize");
		menubar.add(fileMenu);
		
		JMenuItem StopItem = new JMenuItem("Save Map");
		JMenuItem StartItem = new JMenuItem("Start (doesnt work yet)");

		final JTextField textField = new JTextField(20);
		
		fileMenu.add(StopItem);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String text = textField.getText();
				System.out.println(text);
			}
		});
		fileMenu.add(StartItem);
		StartItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
		menubar.add(Opt);
		JMenuItem time = new JMenuItem("Time efficient");
		JMenuItem dist = new JMenuItem("Distance efficient");
		Opt.add(time);
		Opt.add(dist);
		
        time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			for(int a=0;a<cars;a++)
				map.getCars().get(a).setPath(0);
				
				mythread.start();
			}
		});
        dist.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent ae) {
    			for(int a=0;a<cars;a++)
    				map.getCars().get(a).setPath(1);
    			Thread mythread=new MyThread();
				mythread.start();
			
    			} 
    			
    		});
           

		

		
		frame.setJMenuBar(menubar);
		//frame.pack();

	}

}
