import java.util.*;

/** Implementation of a node for a doubly linked list. I have the extends Comparable<E> so that the 
*   addSorted method in the linked list class works. 
*   @author Daniel Vilinsky
*/
class Node<E extends Comparable<E>> {
	public E data;
	public Node<E> next, previous;
	
	/** Default constructor with no data, next and previous nodes set to null 
	*   Running time: O(1)
	*/
	public Node() {
		this(null, null, null);
	}
	
	/** Construct a node with the given data. 
	*   Running time: O(1)
	*   @param data the data the node contains 
	*/
	public Node(E data) {
		this(data, null, null);
	}
	
	/** Constructs a node with the given data and a next node. 
	*   Running time: O(1)
	*   @param data the data node contains 
	*   @param next the next node in the list 
	*/
	public Node(E data, Node<E> next) {
		this(data, next, null);
	}
	
	/** Constructs a node with given data and next and previous nodes 
	*   Running time: O(1)
	*   @param data the data of this node 
	*   @param next the next node in the list 
	*   @param previous the previous node in the list 
	*/
	public Node(E data, Node<E> next, Node<E> previous) {
		this.data = data;
		this.next = next; 
		this.previous = previous;
	}
	
	/** Sets the data field to a given value. Kind of useless if data is public. But good for 
	*   following convention. Running time: O(1)
	*   @param data the data to insert into this node. 
	*   @param data the data to insert into this node. 
	*/
	public void setData(E data) {
		this.data = data;
	}
	
}

/**This class is an implementation of a doubly-linked, circular list 
* It is circular in both directions
* @author Daniel Vilinsky
*/
public class DoublyLinkedList<E extends Comparable<E>> implements MyList<E> {
	private Node<E> front; 
	private int size; 
	
	/** Constructor for this class. 
	*   Running time: O(1)
	*/
	public DoublyLinkedList() {
		front = null;
		size = 0;
	}
	
	/** Appends a new Node<E> containing the given data to end of list 
	*   Running time: O(n), where n is the length of the list 
	*   @param data the data to insert 
	*/
	public void add(E data) {
		Node<E> current = front;
		if (front == null) {
			front = new Node<E>(data);
			front.next = front;
			front.previous = front; 
		} else {
			while (current.next != front) { //if current.next == front, we've reached the end of the list- it is looping back around 
				current = current.next;
			}
			current.next = new Node<E>(data);
			current.next.previous = current;
			current.next.next = front; //we've hit end so loop it around
			front.previous = current.next;
		}
		size++;
	}
	
	/** Inserts a new Node<E> with the given data at the given index. A user will call this 
	*   method when saying to themself "I want a node with "data" to appear at "index"". That's 
	*   why you can write add(5, somevalue), in a list of length 5, even though 5 is technically 
	*   "out of bounds".
	*   Running time: O(n), where n is the length of the list 
	*   @param index the location in the list to add the data 
	*   @param data the datat to insert 
	*   @exception IndexOutOfBoundsException if index <0 or index > size of list 
	*/
	public void add(int index, E data) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Error: Index must be between 0 and size of list - 1");
		}
		if (index == 0 && !isEmpty()) { //add to front of list 
			Node<E> temp = new Node<E>(data);
			temp.next = front; 
			temp.previous = front.previous;
			front.previous.next = temp;
			front.previous = temp;
			front = temp;
			size++;
		} else if (isEmpty()) {
			add(data); //adding to the front of empty list is the same as adding to the end of it
		} else { //add somewhere in the middle 
			Node<E> current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			Node<E> temp = new Node<E>(data);
			temp.next = current.next;
			temp.previous = current;
			current.next.previous = temp;
			current.next = temp;
			size++;
		}
	}
	
	/** Assuming that the list is already sorted, insert a Node<E> containing
	*   the given value such that the sorted order is maintained.
	*   Running time: O(n)
	*   @param data1 the data to be inserted into the list 
	*/
	public void addSorted(E data1) {
		Node<E> current = front;
		int index = 0;
		if (front == null || data1.compareTo(front.data) < 0) {//add to front of list 
			add(0, data1);
			return; //this prevents me from adding numbers twice to the beginning of list;
		} else {
			while (current.next != front && current.next.data.compareTo(data1) < 0) {
				current = current.next;
				index++;
			}
		}
		add(index + 1, data1);
	}
	
	/** Set the node at a given index's value to data1 
	*   Running time: O(n), where n is the length of the list 
	*   @param index the location in the list of the node to change 
	*   @param data1 the new data value
	*   @exception IndexOutOfBoundsException if index < 0 || index >= size()
	*/
	public void set(int index, E data1) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Error: Index must be between 0 and length of list");
		}
		Node<E> current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		current.data = data1;
	}
	
	/** Removes the given value from the list if it is in the list. Does nothing otherwise 
	*   Running time: O(n)
	*   @param value the element to be removed 
	*/
	public void remove(E value) {
		int where = this.indexOf(value);
		if (where != -1) {
			removeAt(where);
		} else {
			return;
		}
	}
	
	/** A little convoluted, but as cleanly as possible removes node at given index from the list 
	*   Running time: O(n)
	*   @param index of node to remove 
	*   @exception IndexOutOfBoundsException if index < 0 || index >= size()
	*/
	public void removeAt(int index) {
		Node<E> current = front;
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Error: Can only remove between 0 and length of list");
		}
		if (index == 0) {
			if (size == 1) {
				clear();
				return; //clear() reduced size to 0, so we don't want to decrement it again below 
			} else {
				front.next.previous = front.previous;
				front.previous.next = front.next;
				front.previous = null;
				front = front.next; //advance front one space
			}
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next.next.previous = current;
			current.next = current.next.next;
		}
		size--;
	}
	
	/** Running time: O(1)
	*   @return the size of this list 
	*/
	public int size() {
		return this.size;
	}
	
	/** Running time: O(n)
	*   @param index the index of the node whose data you want 
	*   @return that node's data 
	*   @exception IndexOutOfBoundsException if index < 0 || index >= size()
	*/
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Error: Index must be between 0 and length of list");
		}
		Node<E> current = front;
		int i = 0;
		while (i < index) {
			current = current.next;
			i++;
		}
		return current.data;
	}
	
	/** Running time: O(n)
	*   @param data the element to be searched for 
	*   @return the index of data if it's in the list, -1 otherwise 
	*/
	public int indexOf(E data) {
		Node<E> current = front;
		int i = 0;
		while (!this.isEmpty() && !current.data.equals(data) && i < size()) {
			current = current.next;
			i++;
		}
		return i != size() ? i : -1; //if you didn't hit size, congrats you found it, else you didn't return -1
	}
	
	/** Running time: O(n)
	*   @param data the element to search for 
	*   @return true if that element is in the list 
	*/
	public boolean contains(E data) {
		return indexOf(data) != -1; //if the index is not -1, it's in the list 
	}
	
	/** Gives a string representation of this list of the form [e1, e2, ... eN], where e1 is 
	*   at the front of the list, and eN is at the end. 
	*   Running time: Undefined. StringBuilder.append(Object o) calls that object's toString method, 
	*   and this method doesn't know what the running time of that is. The object could be a 2D array, 
	*   which makes the running time of this method O(n^3), or it could be an integer, which makes our 
	*   running time O(n);
	*   @return A string representation of this list 
	*/
	public String toString() {
		Node<E> current = front;
		if (isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(current.data);
		while (current.next != front) {
			current = current.next;
			sb.append(", ").append(current.data);
		}
		sb.append("]");
		return sb.toString();
	}
	
	/** Running time: O(1)
	*   @return true if list is empty, false otherwise 
	*/
	public boolean isEmpty() {
		return this.front == null;
	}
	
	/** Empties the list 
	*   Running time: O(1)
	*/
	public void clear() {
		front = null;
		size = 0;
	}
	
	/** Returns an iterator over this list
    *   Running time: O(1)	
	*   @return Iterator<E> over the lsit 
	*/
	public Iterator<E> iterator() {
		return new DLLIterator();
	}
	
	/** Returns an endless iterator over this list 
	*   Running time: O(1)
	*   @return a MyIterator<E> over this list 
	*/
	public MyIterator<E> endlessIterator() {
		return new EndlessIterator();
	}
	
	/** This class provides an implementation of the Java Iterator interface for the DoublyLinkedList class
	*/
	private class DLLIterator implements Iterator<E> {
		private Node<E> current; //node to remove 
		private boolean removeOK;
		private int nodesVisited;
		
		/** Constructor for this class 
		*   Running time: O(1)
		*/
		public DLLIterator() {
            current = front;
            removeOK = false;
			nodesVisited = 0;
        }
		
		/** I am checking for a next value by seeing if we haven't already visited all the nodes 
		*   in the list. Since someone might want to use an instance of this class multiple times, 
		*   I reset the number of nodes visited if it reaches size 
		*   Running time: O(1)
		*   @return true if there is a next element, false otherwise 
		*/
		public boolean hasNext() {
			boolean reset = nodesVisited == size();
			if (reset) {
				nodesVisited = 0;
			}
			return !reset;
		}
		
		/** Returns the next element in the list 
		*   Running time: O(1)
		*   @return the data the next element contains 
		*   @exception NoSuchElementException if there is no next element 
		*/
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E result = current.data;
			current = current.next;
			removeOK = true;
			nodesVisited++;
			return result;
		}
		
		/** Since removing from a circular, doubly-linked list is a nightmare, I'm just using the removeAt()
		*   method that class provides. This sacrifices speed at the altar of program succinctness. 
		*   Running time: O(n)
		*   @exception IllegalStateException if can't remove 
		*/
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			removeOK = false;
			removeAt(nodesVisited - 1);
			nodesVisited--;
		}
	}
	
	/** An endless interator is one that allows a client to keep calling next() as 
	*   long as the list is not empty. 
	*/
	private class EndlessIterator implements MyIterator<E> {
		private Node<E> current;
		
		/** Constructor. Not much else to say. 
		*   Running time: O(1)
		*/
		public EndlessIterator() {
			current = front;
		}
		
		/** Running time: O(1)
		*   @retunr true if the list is not empty, false otherwise
		*/
		public boolean hasNext() {
			return !isEmpty();
		}
		
		/** Gives the caller whatever "current" is when the method is called. 
		*   Running time: O(1)
		*   @return the data of the current element 
		*   @exception NoSuchElementException
		*/
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E result = current.data;
			return result;
		}
		
		/** Moves the iterator one step forward or back, depending on direction 
		*   being true or false, respectively. 
		*   Running time: O(1)
		*   @param direction the direction to go- forward or back. 
		*/
		public void advance(boolean direction) {
			if (direction) {
				current = current.next;
			} else {
				current = current.previous;
			}
		}
	}
}
