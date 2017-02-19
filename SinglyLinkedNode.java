public class SinglyLinkedNode<E> {
	//It is ok that these two fields are public because a client will never see this class anyway 
	public E data;
	public SinglyLinkedNode<E> next;
	
	/**	"Default" constructor for this class. Running time: O(1)
	*/
	public SinglyLinkedNode() {
		this(null, null);
	}
	
	/**
	* @param The data this node contains 
	* Running time: O(1)
	*/
	public SinglyLinkedNode(E data) {
		this(data, null);
	}
	
	/**
	* @param The data this node contains 
	* @param The next node in the list 
	* Running time: O(1)
	*/
	public SinglyLinkedNode(E data, SinglyLinkedNode<E> next) {
		this.data = data;
		this.next = next;
	}
	
	/** Really no point to this if data is a public field, but whatever 
	* @return the data this node contains 
	* Running time: O(1)
	*/
	public E getData() {
		return this.data;
	}
	
	/** Also no point to this if next is public 
	* @param Th next node in the list 
	* Running time: O(1)
	*/
	public void setNext(SinglyLinkedNode<E> next) {
		this.next = next;
	}
	
	/** @return the next node in the lsit 
	*   Running time: O(1)
	*/
	public SinglyLinkedNode<E> getNext() {
		return this.next;
	}
	
	/** @return the string representation of the data in the node.
	*   The running time is undefined. If the node contains an integer, for example,
	*   then the running time is O(1). However if the node contained an array, the running 
	*   time would be O(n). 
	*/
	public String toString() {
		try {
			return this.data.toString();
		} catch (NullPointerException e) {
			return "Error: Cannot call toString on a null object";
		}
	}
}