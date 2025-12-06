public class Node {
	int data; // TODO: modify the type of data you want your node to store as needed
	Node next;
	// You may add more attributes if needed

	public Node() {}

	public Node(int data) {
		this.data = data;
	}

	public int getData() {
		return this.data;
	}

	public void setData(int data) {
		this.data = data;
	}
}