import java.util.*;

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
		this.size++;
	}
	
	/** Inserts a node containing the given data at a random spot in the list
	*   Running time: O(n), where n is the numbe of nodes. In the worst case, we might have to insert at the end of the list. 
	*   @param data to be inserted into the list
	*/
	public void randomInsert(E data) {
		Random r = new Random();
		if (this.size == 0) {
			addAt(0, data);
	   } else {
		   addAt(r.nextInt(this.size), data);
	   }
	}
	
	//Helper method for randomInsert. Inserts a node at the given index containing the given data 
	private void addAt(int index, E data) {
		if (index == 0) {
			SinglyLinkedNode<E> toInsert = new SinglyLinkedNode(data);
			if (this.isEmpty()) {
				tail = toInsert;
			}
			toInsert.next = head;
			head = toInsert; 
			this.size++;
		} else if (index == this.size - 1) {
			this.regularInsert(data);
		} else {
			SinglyLinkedNode<E> previous = nodeAt(index - 1);
			SinglyLinkedNode<E> toInsert = new SinglyLinkedNode(data);
			toInsert.next = previous.next;
			previous.next = toInsert;
			this.size++;
		}
	}
	
	/** Removes the first node containing "data" from the list 
	*  @param data you want to remove from the list 
	*  @return the data that the node that was removed contains. Kind of useless, really 
	*  Running time: Since we have to find where (if anywhere) in the list the data is, 
	*  the running time is O(n).
	*/
	public E remove(E data) {
		this.size--;
		if (this.head.data.equals(data)) {
			SinglyLinkedNode<E> temp = head;
			head = head.next;
			return temp.data;
		}
		SinglyLinkedNode<E> previous = findPrevious(data); //returns the node previous to the one containing the data 
		if (previous != null) {
			if (previous.next.data.equals(this.tail.data)) {
				tail = previous; //if we remove the last node, we have to fix the tail pointer
			}
			SinglyLinkedNode<E> temp = previous.next;
			previous.next = previous.next.next;
			return temp.data;
		} else {
			return null; //if previous was null, then "data" isn't in the list. So we return null
		}
	}
	
	/** Removes the node at the given index. 
	*   Running time: O(n), where n is the size of the list 
	*   @param index of the node to remove 
	*   @return the data at that index
	*   @exception IndexOutOfBoundsException if index is >= size 
	*/
	public E removeAt(int index) {
		return remove(nodeAt(index).data);
	}
	
	//Helper method for removeAt. Returns the node at the given index. 
	private SinglyLinkedNode<E> nodeAt(int index) {
		if (index >= this.size) {
			throw new IndexOutOfBoundsException("Error: Index must be less than the size of the list");
		}
		SinglyLinkedNode<E> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}
	
	//Helper method for remove. Returns the node previous to the one containing the data,
	//or null if the data isn't in the list 
	private SinglyLinkedNode<E> findPrevious(E data) {
		SinglyLinkedNode<E> current = head;
		while (current.next != null) {
			if (current.next.data.equals(data)) {
				return current;
			}
			current = current.next;
		}
		return null;
	}
	
	/** Returns the last element in this list without removing it 
	*   @return the rear of the list 
	*/
	public SinglyLinkedNode<E> peek() {
		return this.tail;
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
	
	/** Empties the list. 
	*   Running time: O(1)
	*/
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	/** Creates a string representation of the form [elem1, elem2...elemN], where elem1 is
	*   at the head, elemN at the tail.
	*   @return string representation of the list
	*   Running time: O(n) because append() is O(1), not O(n) like string concatenation with 
	*   the + operator is. 
	*/
	public String toString() {
		SinglyLinkedNode<E> current = head;
		if (current == null) { //list is empty
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[" + current.data);
		while (current.next != null) {
			current = current.next;
			sb.append(", ").append(current.data);
		}
		sb.append("]");
		return sb.toString();
	}
	
	/** Gives you an iterator over this list. 
	*   Running time: O(1)
	*   @return an Iterator<E> over this list
	*/
	public Iterator<E> iterator() {
		return new SLLIterator();
	}
	
	/** Implementation of the Iterator interface for this class. I'm writing this so the 
	*   drawCard method of UnoDeck.java works. Much of this has been adapted from Chapter 16 of
	*   "Building Java Programs," 3rd Edition by Reges and Stepp.
	*/
	private class SLLIterator implements Iterator<E> {
		private SinglyLinkedNode<E> current, previous;
		private boolean removeOK;
		
		/** Constructor for this class
		*   Running time: O(1)
		*/
		public SLLIterator() {
			current = head;
			previous = head;
			removeOK = false;
		}
		
		/** Running time: O(1)
		*   @return true if there is a next element in this iterator, false otherwise 
		*/
		public boolean hasNext() {
			return current != null; //when current is null, we can't call next anymore 
		}
		
		/** Returns the data of current without removing it
		*   Running time: O(1)
		*   @return the data of current
		*/
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E result = current.data;
			previous = current;
			current = current.next;
			removeOK = true;
			return result;
		}
		
		/** Removes the element that next() just returned. Assumes user will call next 
		*   before calling this method. 
	    *   Running time: O(1)
		*/
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			previous = current;
			removeOK = false;
		}
	}
}