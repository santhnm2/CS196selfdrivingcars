package Optimization;
import Constants.Directions;

public class graphNode {
   private graphNode left;
   private graphNode right;
   private graphNode up;
   private graphNode down;
   private int dirHorizontal = Directions.NO_DIR;
   private int dirVertical = Directions.NO_DIR;
   private int speedHorizontal = 1;
   private int speedVertical = 1;
   private int x;
   private int y;
   private int index;

   public graphNode(int x, int y, int index) {
      this.x = x;
      this.y = y;
      this.index = index;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }
   public int getIndex() {
	      return this.index;
	   }

   public void setLeft(graphNode graph) {
      this.left = graph;
   }

   public void setRight(graphNode graph) {
      this.right = graph;
   }

   public void setUp(graphNode graph) {
      this.up = graph;
   }

   public void setDown(graphNode graph) {
      this.down = graph;
   }
   
   public void setHorizontal(int h) {
	   this.dirHorizontal = h;
   }
   
   public void setVertical(int v) {
	   this.dirVertical = v;
   }
   
   public int getHorizontal() {
	   return this.dirHorizontal;
   }
   
   public int getVertical() {
	   return this.dirVertical;
   }
    public void setHorizontalSpeed(int h) {
	   this.speedHorizontal = h;
   }
   
   public void setVerticalSpeed(int v) {
	   this.speedVertical = v;
   }
   
   public int getHorizontalSpeed() {
	   return this.speedHorizontal;
   }
   
   public int getVerticalSpeed() {
	   return this.speedVertical;
   }

   public graphNode getLeft() {
      return this.left;
   }

   public graphNode getRight() {
      return this.right;
   }

   public graphNode getUp() {
      return this.up;
   }

   public graphNode getDown() {
      return this.down;
   }
}
