/** This class provides an implementation of a non-circular singly linked list 
*  @author Daniel Vilinsky  
*/
public class SinglyLinkedList<E> {
	private SinglyLinkedNode<E> head, tail;
	private int size;
	
	/** Constructor. Sets the head and tail to null, and size to 0 
	*   Running time: O(1)
	*/
	public SinglyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	/** I don't like returning the node. SinglyLinkedNode is not a well-encapsulated class,
	*   which theoretically should be ok because a client should never touch it. But this 
	*   breaches that contract.
	*   @return head of this lsit 
	*   Running time: O(1)
	*/
	public SinglyLinkedNode<E> getHead() {
		return this.head;
	}
	
	/** Inserts a node containing the given data to the end of the list. 
	*   @param the data to be inserted 
	*   Running time: O(1)
	*/
	public void regularInsert(E data) {
		SinglyLinkedNode<E> toInsert = new SinglyLinkedNode(data);
		if (this.isEmpty()) {
			this.head = toInsert;
			this.tail = toInsert;
		} else {
			tail.next = toInsert;
			tail = toInsert;
		}
		size++;
	}
	
	/** Removes the node containing "data" from the list 
	*  @param data you want to remove from the list 
	*  Running time: Since we have to find where (if anywhere) in the list the data is, 
	*  the running time is O(n). However, this makes a call to .equals(). Depending on how that is 
	*  implemented, the running time could be worse. 
	*/
	public void remove(E data) {
		if (head != null && head.data.equals(data)) {
			head = head.next;
		} else {
			SinglyLinkedNode<E> toRemove = this.findPrevious(data);
			if (toRemove == null) {
				return;
			} 
			toRemove.next = toRemove.next.next;
		}
		this.size--;
	}
	
	//Helper method for remove. Returns the node previous to the one containing the data, if it exists.
	//Retuns null if it doesn't.
	private SinglyLinkedNode<E> findPrevious(E data) {
		SinglyLinkedNode<E> current = head;
		while (current != null) {
			if (current.next != null && current.next.data.equals(data)) {
				return current;
			}
			current = current.next;
		}
		return current;
	}

	/** @return True if list is empty, false otherwise 
	*   Running time: O(1)
	*/
	public boolean isEmpty() {
		return this.head == null && this.tail == null;
	}
	
	/** @return The size of the list 
	*   Running time: O(1)
	*/
	public int size() {
		return this.size;
	}
	
	/** Creates a string representation of the form [elem1, elem2...elemN], where elem1 is
	*   at the head, elemN at the tail.
	*   @return string representation of the list
	*   Running time: O(n^2), if string concatenation is O(n)
	*/
	public String toString() {
		SinglyLinkedNode<E> current = head;
		if (current == null) { //list is empty
			return "[]";
		}
		String s = "[" + current.data;
		while (current.next != null) {
			current = current.next;
			s += ", " + current.data;
		}
		return s + "]";
	}
}