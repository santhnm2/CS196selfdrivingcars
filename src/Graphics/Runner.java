package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Map.Map;
import Map.RandomMapGenerator;
import Map.Tile;
import Map.NonRoad.NonRoad;
import Map.Road.Road;
import Map.Road.TrafficLight;
import Car.Car;

public class Runner extends JFrame {
	static int iterations = 0;
	static JPanel gui = new JPanel(new BorderLayout());

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

		color(gui, map);

		JFrame f = new JFrame("Demo");
		f.add(gui);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);
		f.pack();
		f.setVisible(true);

		for (int i = 0; i < 100; i++) {
			step(map);
			color(gui, map);
			gui.repaint();

			Thread.sleep(2000);
		}
	}

	private static void color(JPanel gui, Map map) {
		for (int i = 0; i < map.getLengthX(); i++) {
			for (int j = 0; j < map.getLengthY(); j++) {
				if (map.get(j, i) instanceof TrafficLight) {
					TrafficLight light = (TrafficLight) map.get(j, i);

					if (light.isRed())
						gui.add(new JLabel(new ColorIcon(Color.RED, 16)));
					else
						gui.add(new JLabel(new ColorIcon(Color.GREEN, 16)));
				} else if (map.get(j, i) instanceof NonRoad) {
					gui.add(new JLabel(new ColorIcon(Color.WHITE, 16)));
				} else if (map.get(j, i) instanceof Road) {
					Road car = (Road) map.get(j, i);
					if (car.getFilled() > 0)
						gui.add(new JLabel(new ColorIcon(Color.PINK, 16)));
					else
						gui.add(new JLabel(new ColorIcon(Color.BLACK, 16)));
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

		System.out.println(map);
	}
}
