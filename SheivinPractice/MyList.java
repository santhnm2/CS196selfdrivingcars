//An object which contains the method of adding characters to the list and
//contains the location of the head and tail of the list.
public class MyList {
   CharNode head = null;
   CharNode tail = null;
   public void add(char item)
   {
//sets the first given character as the 'head'
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
}
