//this is a program which reads a string and stores it into the java
//built in linkedlist and then prints forwards and backwards
import java.util.LinkedList;
import java.util.Scanner;
public class BuiltInLL {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      LinkedList<Character> theList = new LinkedList<Character>();
      System.out.println("please enter a word.");
      String word = sc.next();
// stores all of the characters into consecutive nodes.
      for(int i = 0; i<word.length();i++)
         theList.add(word.charAt(i));
//prints forwards
      for ( int i = 0; i< theList.size(); i++)
      {
         System.out.print(theList.get(i));
      }
      System.out.println();
//prints backwards      
      for ( int i = theList.size()-1; i >=0; i--)
      {
         System.out.print(theList.get(i));
      }
   }
   

}
