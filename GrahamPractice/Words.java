//this is a program which reads a string and then stores it in my own 
//linked list and then prints it forward and backwards.
import java.util.Scanner;
public class Words {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      MyList list = new MyList();;
            System.out.println("please enter a word.");
      String word = sc.next();
// stores all of the characters into consecutive nodes.
      for(int i = 0; i<word.length();i++)
         list.add(word.charAt(i));
//prints forwards
      for ( CharNode runner = list.head; runner != null; runner = runner.next)
      {
         System.out.print(runner.item);
      }
      System.out.println();
//prints backwards      
      for ( CharNode runner = list.tail; runner != null; runner = runner.previous)
      {
         System.out.print(runner.item);
      }
   }
   

}
