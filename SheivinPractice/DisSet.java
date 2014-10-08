
public class DisSet {
   static int[][] mySet = new int[2][];
   /**
    * @param args
    */
   public static void main(String[] args) {

      mySet[0] = new int[]{1,4,5,6,13};
      mySet[1] = new int[mySet[0].length];
      for (int i = 0; i<mySet[0].length;i++)
         mySet[1][i] = -1;
      mySet = Union(mySet, 4, 6);
      mySet = Union(mySet, 1, 5);
      mySet = Union(mySet,1, 4);
      System.out.println(Find(mySet, 13));
   }
   public static int[][] Union(int[][] array,int parent, int child) {
      child =Find(array,child);
      parent = Find(array,parent);
      int index = FindIndex(array, child);
      if (index != -1)
      {
         for (int i = 0; i< array[0].length;i++)
            if (array[0][i]== child)
               array[1][i] = parent;
         array[1][index] = parent;
      }
      return array;
   }
   public static int FindIndex(int[][] array, int num)
   {
      for (int i = 0; i<array[0].length;i++)
         if (array[0][i] == num)
            return i;
      return -1;
   }
   public static int Find(int[][] array, int num)
   {
      //determines the ultimate parent of a given number
    
      if (FindIndex(array, num) != -1)
      {
      while (array[1][FindIndex(array, num)] != -1)
         num = array[1][FindIndex(array, num)];
      }
      return num;
   }
}


