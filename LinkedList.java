public class LinkedList {
	int size; // size of the linked list
	Node head; // reference to the start of the linked list

	// Default constructor setting the size of the list to 0
	public LinkedList() {
		this.size = 0;
		this.head = null;
	}

	// Constructor with a single element in the list 
	public LinkedList(Node node) {
		this.size = 1;
		this.head = node;
	}

	// Getters
	public int getSize() {
		return this.size;
	}

	public Node getHead() {
		return this.head;
	}

	// Adding Nodes
	// Add node to head
	public void addToHead(Node node) {
		if (this.head == null) {
			this.head = node;
		} else {
			node.next = this.head;
			this.head = node;
		}

		this.size += 1;
	}

	// Add node to tail
	public void addToTail(Node node) {
		if (this.head == null) {
			this.head = node;
		} else {
			Node curr = this.head;
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = node;
		}

		this.size += 1; 
	}

	// Removing Nodes
	
	// Remove head node
	public void removeHead() {
		if (this.head == null) {
			System.out.println("No head to remove.");
		} else {
			this.head = this.head.next;
			this.size -= 1;
		}
	}
	

	// TODO: Homework 12: Remove tail node




	// print method that will output all nodes in the list
	public void print() {
		if (this.head == null) {
			System.out.println("null");
		} else {
			Node curr = this.head;
			while (curr != null) {
				System.out.print(curr.data + " -> ");
				curr = curr.next;
			}
			System.out.println("null");
		}
	}

	// TODO: Implement
	public void addAtIndex(Node node, int index) {

  	}

  	// TODO: Implement as many methods as needed
}
