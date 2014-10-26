public class PriorityQueue {
   private int[] heap = new int[] { Integer.MIN_VALUE };
   private int length = 0;

   //resizes the array to be the same size as the number of elements
   public void resize() {
      int[] temp = new int[length + 1];
      for (int i = 0; i < heap.length && i < length; i++)
         temp[i] = heap[i];
      heap = temp;

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

   public void swap(int index1, int index2) {
      int temp;
      temp = heap[index1];
      heap[index1] = heap[index2];
      heap[index2] = temp;
   }

   //puts the item in the right spot
   public void heap(int index) {
      //checks that the item isn't on the end, w/o this getleft & getRight would have a lot of trouble.
      if (!(index >= (length / 2))) {
         //checks if the input is smaller than both its children
         if (heap[index] > heap[getLeft(index)]
               || heap[index] > heap[getRight(index)]) {
            //switches the places of the item and its smaller child. then reruns the method.
            if (heap[getLeft(index)] < heap[getRight(index)]) {
               swap(index, getLeft(index));
               heap(getLeft(index));
            } else {
               swap(index, getRight(index));
               heap(getRight(index));
            }
         }
      }
      //method stops when item is on the end of the list or finds its spot.
   }
   //adds a number to correct place in list.
   public void add(int value) {
      length++;
      resize();
      heap[length] = value;
      int runner = length;
      
      while (heap[runner] < heap[parent(runner)]) {
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
   public int remove() {
      int removed = heap[1];
      //sets first item to one of the largest in the array so as to completely reorganize the array.
      heap[1] = heap[length];
      length--;
      resize();
      heap(1);
      return removed;
   }

}
