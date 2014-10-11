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
      
      MyList.Print(list, true);//forward
      MyList.Print(list, false);//backwards
      
      System.out.print("first index: ");
      int first = sc.nextInt();
      
      System.out.print("second index: ");
      int second = sc.nextInt();
      
      MyList.InternalReversal(list, first, second);
   }
   
}
