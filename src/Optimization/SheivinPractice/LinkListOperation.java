/**
 * Main class which will call all the functions to perform the operations on the numbers in the Link List
 */

import java.io.*;
public class LinkListOperation {
	
	public static void main(String[] args)throws IOException {
		LinkList list = new LinkList();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
		}
		list.printList();
		list.print();

	}

}