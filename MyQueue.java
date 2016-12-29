import java.util.NoSuchElementException;

class NodeQ<E>  {
	public E data;
	public NodeQ<E> next, previous;
	
	public NodeQ() {
		this(null, null, null);
	}
	
	public NodeQ(E data) {
		this(data, null, null);
	}
	
	public NodeQ(E data, NodeQ<E> next) {
		this(data, next, null);
	}
	
	public NodeQ(E data, NodeQ<E> next, NodeQ<E> previous) {
		this.data = data;
		this.next = next;
		this.previous = previous;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
}

/** This class provides a NodeQ-based implementation of a first-in, first-out Queue 
*   I chose not to use an array because this way we can have a queue as large as we want (theoretically)
*   I also didn't use the MyLinkedList class because I would have to make the "back" pointer actually work, 
*   and that class is already quite complex. I hope I am not reinventing the wheel though
*/
public class MyQueue<E> {
	private NodeQ<E> front, tail;
	private double size; //gives us a little higher "capacity"- there's no theoretical limit, but after max double, size is unpredictable
	
	public MyQueue() {
		front = null;
		tail = null;
		size = 0;
	}
	
	//Adds element to the rear of the queue, which in this case is the front of the
	//doubly-linked list which I'm basically implementing here
	//Running time: O(1)
	public void enqueue(E value) {
		if (isEmpty()) { //I may run into trouble doing this instead of if (front == null)
			front = new NodeQ<E>(value);
			tail = front; //tail will not move from here for the remainder of this method
		} else {
			NodeQ<E> temp = new NodeQ<E>(value);
			temp.next = front; 
			front.previous = temp;
			front = temp;
		}
		size++;
	}
	
	//Removes and returns an element from the end of our list, which in this case
	//is the front of the queue 
	//Running time O(1)
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Error: Cannot dequeue from empty queue");
		}
		NodeQ<E> temp = tail;
		tail = tail.previous;
		size--;
		return temp.data;
	}
	
	//Returns an element from the end of the list, which in this case is 
	//the front of the queue 
    //Running time: O(1)
	public E peek() {
		if (isEmpty()) {
			throw new NoSuchElementException("Error: No element to view");
		}
		return tail.data;
	}
	
	//Returns the size of the queue
	//Running time: O(1)
	public double size() {
		return size;
	}
	
    //Returns true if the queue is empty, false otherwise
	//Running time: O(1)
	public boolean isEmpty() {
		return size == 0;
	}
	
	//Returns a string of the form [elem1, elem2, ... elemN] where elem1 is the front of the queue 
	//and elemN is the rear. 
	//Running time: O(n), where n is the number of elements in the queue
	public String toString() {
		if (isEmpty()) { //I'm not sure if it's a good idea to be using size == 0 as my check on if the queue is empty
			return "[]";
		}
		NodeQ<E> current = tail;
		String queueString = "[";
		queueString += current.data;
		while (current.previous != null) {
			current = current.previous;
			queueString += ", " + current.data;
		}
		queueString += "]";
		return queueString;
	}	
}