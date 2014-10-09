/**
 * This is Assignment 1 where a linked list has to be created
 * which will perform a specific operation.
 * @author sheivin
 *
 */

public class Link {
	
	public int digit;
	public Link nextLink;
	
	//Link constructor
	public Link(int d){
		this.digit = d;
	}
	
	//Print Link data
	public void printLink() {
		System.out.print("{" + digit + "} ");
	}
	
}
