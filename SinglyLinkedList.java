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
	}
	
	/** @return True if list is empty, false otherwise 
	*   Running time: O(1)
	*/
	public boolean isEmpty() {
		return this.head == null && this.tail == null;
	}
}