package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Car.Car;
import Map.Map;
import Map.RandomMapGenerator;
import Map.Tile;
import Map.NonRoad.NonRoad;
import Map.Road.Road;
import Map.Road.TrafficLight;

public class Runner extends JFrame {
	static int iterations = 0;
	static JPanel gui = new JPanel(new BorderLayout());
	static JLabel [][] labels;

	public static void main(String[] args) throws InterruptedException {

		// the GUI as seen by the user (without frame)

		gui.setBorder(new EmptyBorder(2, 3, 2, 3));
		gui.setBackground(Color.WHITE.darker().darker());
		RandomMapGenerator generator = new RandomMapGenerator(30, 1);

		Map map = generator.generateMap();
		System.out.println(map);
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

		JFrame f = new JFrame("Demo");
		f.add(gui);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);
		f.pack();
		f.setVisible(true);

		for (int i = 0; i < 20; i++) {
			gui.removeAll();
			step(map);
			color(gui, map);
			f.pack();
			gui.updateUI();
			//gui.revalidate();
			Thread.sleep(500);
			}
	}

	private static void color(JPanel gui, Map map) {
		for (int i = 0; i < map.getLengthX(); i++) {
			for (int j = 0; j < map.getLengthY(); j++) {
				if (map.get(j, i) instanceof TrafficLight) {
					TrafficLight light = (TrafficLight) map.get(j, i);

					if (light.isRed()) {
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						JLabel myLabel = new JLabel(new ColorIcon(Color.RED, 16));
						gui.add(myLabel);
						myLabel.setLocation(i, j);

					}
					else
					{
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						JLabel myLabel = new JLabel(new ColorIcon(Color.GREEN, 16));
						gui.add(myLabel);
						myLabel.setLocation(i, j);
					}
				} else if (map.get(j, i) instanceof NonRoad) {
					//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
					JLabel myLabel = new JLabel(new ColorIcon(Color.WHITE, 16));
					gui.add(myLabel);
					myLabel.setLocation(i, j);
				} else if (map.get(j, i) instanceof Road) {
					Road car = (Road) map.get(j, i);
					if (car.getFilled() > 0)
					{
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						JLabel myLabel = new JLabel(new ColorIcon(Color.PINK, 16));
						gui.add(myLabel);
						System.out.printf("Drawing a car at (%d, %d)\n", i, j);
						myLabel.setLocation(i, j);
					}
					else
					{
						//labels[i][j] = new JLabel(new ColorIcon(Color.RED, 16));
						JLabel myLabel = new JLabel(new ColorIcon(Color.BLACK, 16));
						gui.add(myLabel);
						myLabel.setLocation(i, j);	
					}
				}
			}
		}

		// step();
	}
	
	static class ColorIcon implements Icon {

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
			g.fillRect(x, y, preferredSize, preferredSize);
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
}
