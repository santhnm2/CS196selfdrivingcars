/**
 * Main class which will call all the functions to perform the operations on the numbers in the Link List
 */

import java.io.*;
public class LinkListOperation {
	
	public static void main(String[] args)throws IOException {
		LinkList list = new LinkList();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
		System.out.println("Menu");
		System.out.println("1. Enter number as a whole");
		System.out.println("2. Enter digits separately");
		System.out.println("3. Exit");
		System.out.println("Enter your choice");
		int choice = Integer.parseInt(br.readLine());
		boolean singleDigit = false;
		
		if(choice == 3) {
			System.out.println("Bye!");
			System.exit(0);
		}
		
		if(choice == 2) {
			singleDigit = true;
		}
		
		if(singleDigit) {
			System.out.println("Enter numbers to be added to Link List");
			int d = 0;
			String line = "";
			while(true) {
				line = br.readLine();
				if(line.length() == 0) {
					break;
				}
				d = Integer.parseInt(line);
				list.insert(d);
				list.rollingSum();
			}
		}
		else {
			System.out.println("Enter number");
			String line = br.readLine();
			int d = 0;
			for(int i = 0; i < line.length(); i++) {
				d = line.charAt(i) - 48;
				list.insert(d);
			}
		}
		
		list.printList();
		list.print();
		}

	}

}