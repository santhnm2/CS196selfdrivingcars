package Optimization;

public class PriorityQueue {
	   private int[][] heap = new int[1][3];
	   private int length = 0;
	   

	   //resizes the array to be the same size as the number of elements
	   public PriorityQueue()
	   {
	      heap[0][0] = Integer.MIN_VALUE;
	      heap[0][1] = -1;
	      heap[0][2] = -1;
	   }
	   public void resize() {
	      if (heap.length <=   length + 1){
	      int[][] temp = new int[length*2][3];
	      for (int i = 0; i < length; i++)
	    	  for (int j = 0; j<3; j++)
	    		  temp[i][j] = heap[i][j];
	      heap = temp;
	      }
	    		  
	   }
	   //returns the index of the parent, left child and right child
	   public int parent(int index) {
	      return index / 2;
	   }

	   public int getLeft(int index) {
	      return (2 * index);
	   }

	   public int getRight(int index) {
	      return (2 * index) + 1;
	   }
	   public boolean foundItem(int index, int weight, int parentIndex){
	      for (int i = 0; i < length; i++)
	         if (heap[i][1] == index){
	            if (heap[i][0] <= weight){
	               heap[i][0] = weight;
	               heap[i][2] = parentIndex;
	            }
	            return true;
	         }
	      
	      return false;
	   }

	   public void swap(int index1, int index2) {
	      int[] temp = new int[3];
	      for (int i = 0; i<3; i++)
	    	  temp[i] = heap[index1][i];
	      heap[index1] = heap[index2];
	      heap[index2] = temp;
	   }

	   //puts the item in the right spot
	   public void heap(int index) {
	      //checks that the item isn't on the end, w/o this getleft & getRight would have a lot of trouble.
	      if (!(index >= (length / 2))) {
	         //checks if the input is smaller than both its children
	         if (heap[index][0] > heap[getLeft(index)][0]
	               || heap[index][0] > heap[getRight(index)][0]) {
	            //switches the places of the item and its smaller child. then reruns the method.
	            if (heap[getLeft(index)][0] < heap[getRight(index)][0]) {
	               swap(index, getLeft(index));
	               heap(getLeft(index));
	            } else {
	               swap(index, getRight(index));
	               heap(getRight(index));
	            }
	         }
	      }
	   }
	      //method stops when item is on the end of the list or finds its spot.
	      public void add(int weight, int index, int parentIndex) {
	         //adds a number to correct place in list.
	         if (foundItem(index, weight, parentIndex))
	            return;
	         length++;
	         resize();
	         heap[length][0] = weight;			//heap[i][0] stores the weight of the edge in the graph
	         heap[length][1] = index;			//heap[i][1] stores the index of the node in the heap
	         heap[length][2] = parentIndex;	//heap[i][2] stores the index of the node's parent in the heap
	         int runner = length;
	      
	         while (heap[runner][0] < heap[parent(runner)][0]) {
	            swap(runner, parent(runner));
	            runner = parent(runner);
	      }
	   }
	   //checks every piece of array and places it in the correct place.
	   public void heapMyArray() {
	      for (int index = (length / 2); index >= 1; index--) {
	         heap(index);
	      }
	   }
	   //removes the leading item in the array.
	   public int[] remove() {
	      int[] removed = heap[1];
	      //sets first item to one of the largest in the array so as to completely reorganize the array.
	      heap[1] = heap[length];
	      length--;
	      resize();
	      heap(1);
	      return removed;
	   }

	}

