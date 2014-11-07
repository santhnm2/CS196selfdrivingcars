package Optimization;

public class graphNode {
   private graphNode left;
   private graphNode right;
   private graphNode up;
   private graphNode down;
   private int x;
   private int y;
   private int index;

   public graphNode(int x, int y, int index) {
      this.x = x;
      this.y = y;
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
