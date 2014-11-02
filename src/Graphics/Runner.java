//UIUC CS125 FALL 2014 MP. File: Photoscoop.java, CS125 Project: Challenge4-Photoscoop, Version: 2014-10-05T14:10:49-0500.839509640
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.imageio.*;

import Map.*;
/* * @author angrave
 */
public class Runner {
	static JFrame mainFrame; // the main window
	static JFileChooser chooser = null;
	static Map<JFrame, BufferedImage> frameMap = new HashMap<JFrame, BufferedImage>();
	static JFrame backgroundFrame;
	static int[][] sourcePixels;
	static JFrame lastSourceFrame;

	public static void main(String[] args) throws IOException {
		Runner r=new Runner();
	}
		public Runner()throws IOException{
		BufferedImage img;
		img = ImageIO.read(new File("C:\\Users\\Shim\\workspace\\CS196SelfDrivingCars\\src\\Graphics\\car.png"));
		String title="Cars";
		JFrame frame = new JFrame();
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
		menubar.add(fileMenu);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		addMenus(frame);
		
		//setImageForFrame(frame, img);
		frame.setJMenuBar(menubar);
		//frame.pack(); // calculate its size
		frame.setSize(100, 100);
		frame.setVisible(true); // display it
		}
	
		
			
	

	/**
	 * Opens a file and displays the image. This method is used by main and open
	 * item.
	 * 
	 * @param filename
	 *            the file to be opened
	 */
	/*
	private static void openImageFile(String filename) {
		// Delegate the problem of reading the filename to another method!
		BufferedImage img = ImageUtilities.loadImage(filename);
		if (img == null) {
			JOptionPane.showMessageDialog(null, filename + " could not be opened.");
		} else
			openImage(img, filename); // create the window with a title - see
		// below
	}

	/**
	 * Creates a window ('jframe') and performs the necessary voodoo to display
	 * the window and a menu too.
	 */
	public void paintComponent(Graphics g)
	   {
	      File img = new File("C:\\Users\\Shim\\workspace\\CS196SelfDrivingCars\\src\\Graphics\\car.png");
	      File img1 = new File("C:\\Users\\Shim\\workspace\\CS196SelfDrivingCars\\src\\Graphics\\car.jpg");
	      BufferedImage buffImg = new BufferedImage(1,1,1);
	      BufferedImage buffImg1 = new BufferedImage(1,1,1); 
	      try
	      {
	         buffImg = ImageIO.read(img);
	         buffImg1=ImageIO.read(img1);
	      }
	      catch (IOException e)
	      {
	         System.exit(0);
	      }
	      
	      //buffImg = Thumbnails.of(buffImg).size(200,200).asBufferedImage();
	    	  g.drawImage(buffImg, 0, 0, null);
	    	  
	      
	    
	   }


	/**
	 * Add drop-down menus to the JFrame
	 * 
	 * @param frame
	 */
	public static void addMenus(final JFrame frame) {
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu tools = new JMenu("Tools");
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(tools);
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveAsItem = new JMenuItem("SaveAs");
		JMenuItem quitItem = new JMenuItem("Exit");
		JMenuItem backgroundItem = new JMenuItem("Use as background");
		JMenuItem undoItem = new JMenuItem("Undo");
		JMenuItem copyItem = new JMenuItem("Copy");
		JMenuItem pasteItem = new JMenuItem("Paste");
		JMenuItem captureScreenItem = new JMenuItem("Capture Screen");

		fileMenu.add(openItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(quitItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(captureScreenItem);
		editMenu.add(undoItem);
		/*
		 * Inner anonymous classes hold the code to perform the operation.
		 */
		/*quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
		
	*/
}
}// end class

