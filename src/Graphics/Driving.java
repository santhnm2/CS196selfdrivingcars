import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;   
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import java.io.IOException;   
import java.lang.Thread;

public class Driving extends JPanel implements Runnable, ActionListener
{
   public static void main(String[] args)
   {
      Driving a = new Driving();
   }

   public Driving()
   {
   	JFrame my_frame = new JFrame("Self Driving Car ");
      my_frame.setVisible(true);
      my_frame.setSize(400,400);
     // my_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //when click on "close", the frame will be closed 
      JPanel my_panel = new JPanel();
      my_panel.setBackground(Color.WHITE);  
      JButton my_button = new JButton("Your Vechicle");
      JLabel label = new JLabel("Car !");   // Make a JLabel;
      my_frame.add(this);
   	  
      
      Thread thread = new Thread(this);
      thread.start();
   }
   public void run()
   {
      while(true) {
         try
         {
            Thread.sleep(300);
         }
         catch(InterruptedException e){

         }
         repaint();  
      }
   }         
   public void paintComponent(Graphics g)
   {
      File img = new File("car.png");
      File img1 = new File("house.png");
      BufferedImage buffImg = new BufferedImage(1,1,1);
      BufferedImage buffImg1 = new BufferedImage(1,1,1);

      try
      {
         buffImg = ImageIO.read(img);
         buffImg1 = ImageIO.read(img1);
      }
      catch (IOException e)
      {
         System.exit(0);
      }
      //buffImg = Thumbnails.of(buffImg).size(200,200).asBufferedImage();
      int curr_x = 0;
      int curr_y = 0;
            g.drawImage(buffImg1, 0, 0, 500,500,null); 

      while (curr_x < 2 && curr_y < 2) {
        //curr_x = getcurrposition_x();
        //curr_y = getcurrposition_y();
        curr_x+=50;
        curr_y+=50;
        g.drawImage(buffImg, curr_x, curr_y, 50, 50, null);

      }
       
      //g.dispose();  
      //g.drawImage(buffImg, 250, 250, null);



   }

   public void actionPerformed(ActionEvent e)
   {

   }
}