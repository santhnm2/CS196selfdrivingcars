public class LinkList {

	/**
	 * Every new link will be added in front of the last added link.
	 */
	
	public Link first;
	
	//LinkList constructor
	public LinkList() {
		first = null;	
	}
	
	//Returns true if the list is empty
	public boolean isEmpty() {
		return first == null;
	}
	
	//Inserts a new Link at the first of the list
	public void insert(int d) {
		Link link = new Link(d);
		link.nextLink = first;
		first = link;
	}
	
	//Prints list data
	public void printList() {
		Link currentLink = first;
		System.out.print("List : ");
		while(currentLink != null) {
			currentLink.printLink();
			currentLink = currentLink.nextLink;
		}
		System.out.println();
	}
	
	public void print() {
		Link currentLink = first;
		String forward = "";
		String reverse="";
		while(currentLink != null) {
			reverse = reverse + "" + currentLink.digit;
			forward = "" + currentLink.digit + forward;
			currentLink = currentLink.nextLink;
		}
		System.out.println("Original Number : " + forward);
		System.out.println("Reverse Number : " + reverse);
	}
}