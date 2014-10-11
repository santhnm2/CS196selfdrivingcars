//An object which contains the method of adding characters to the list and
//contains the location of the head and tail of the list.
public class MyList {
   CharNode head = null;
   CharNode tail = null;
   public void add(char item)
   {
//sets the index1 given character as the 'head'
      if(head == null)
      {
         head = tail = new CharNode();
         head.item = item;
         head.next = tail;
         tail = head;
         tail.next = null;
      }
//assigns the next value to be the new tail and sets both the next and 
//previous references.
      else
      {
         tail.next = new CharNode();
         tail.next.previous = tail;
         tail = tail.next;
         tail.item = item;
      }
   }
   public static void Print(MyList list, Boolean forward)
   {
      if (forward)
      {
         for ( CharNode runner = list.head; runner != null; runner = runner.next)
         {
            System.out.print(runner.item);
         }
      }
      else
      {
         for ( CharNode runner = list.tail; runner != null; runner = runner.previous)
         {
            System.out.print(runner.item);
         }
      }
      System.out.println();
   }
   public static void InternalReversal(MyList list, int index1, int index2)
   {
      if (index1 > index2)
      {
         int temp = index1;
         index1 = index2;
         index2 = temp;
      }
      CharNode runner = list.head;
      for ( int i = 0; i < index1; i++)
      {
         System.out.print(runner.item);
         runner = runner.next;
         if (runner == null)
            return;
      }
      for ( int i = index1; i < index2; i++)
      {
         runner = runner.next;
         if (runner.next == null)
         {
            index2 = i+1;
         }
      }
      for ( int i = index1; i <= index2; i++)
      {
         System.out.print(runner.item);
         runner = runner.previous;
      }
      for ( int i = index1; i <= index2+1 && runner != null; i++)
      {
         runner = runner.next;
      }
      for ( ; runner != null; runner = runner.next)
      {
         System.out.print(runner.item);
      }
   }
}
