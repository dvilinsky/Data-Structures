import java.util.*;

/** This class provides an array based implementation of a circular queue. 
*   @author Daniel Vilinsky
*/
public class Queue<E> {
	private static final int DEFAULT_SIZE = 50;
	
	private E[] data;
	private int front, rear; //indices of front and rear of queue 
	private int size;

	/** Default constructor. Constructs a queue with a default array buffer of 50; 
	*   Running time: O(1);
	*/
	public Queue() {
		this(DEFAULT_SIZE);
	}
	
	/** Constructs a queue of the given size. 
	*   Running time: O(1)
	*   @param the size of the queue 
	*/
	public Queue(int size) {
		this.front = 0;
		this.rear = 0;
		this.data = (E[]) new Object[size];
		this.size = 0;
	}
     
	/** Adds a value to the rear of the queue 
	*   Running time: O(1)
	*   @param value to be added 
	*/
	public void enqueue(E value) {
		if (this.isFull()) {
			throw new IllegalStateException("Error: Queue is full");
		}
		data[rear++] = value;
		if (rear == data.length) {
			rear = 0;
		}
		this.size++;
	}
	
	/** Removes and retuns the value at the front of the queue
	*   Running time: O(1)
	*   @return value at front of queue 
	*/
	public E dequeue() {
		E temp = data[front++];
		if (front == data.length) {
			front = 0;
		} 
		this.size--;
		return temp;
	}
	
	/** Running time: O(1)
	*   @return true if queue is empty, false otherwise
	*/
	public boolean isEmpty() {
		return this.size == 0; //I think this is a lot simpler than deciding based on where front and rear are 
	}
	
	/** Running time: O(1)
	*   @return true if queue is full, false otherwise
	*/
	public boolean isFull() {
		return this.data.length == this.size; //also easier than deciding if queue is full based on where front 
                                             //and rear are 		
	}
	
	/** Running time: O(1)
	*   @return the size of the queue
	*/
	public int getSize() {
		return this.size;
	}
	
	/** Returns a string representation of this queue of the form [e1, e2, ... , eN], where
	*   el is the front of the queue, eN is the rear.
	*   Running time: O(n), where n is the size of the queue
	*   @return string representation of this queue
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int tempFront = this.front, i = 1;
		sb.append("[").append(this.data[tempFront++]);
		while (i < this.size) {
			sb.append(", ").append(this.data[tempFront++]);
			if (tempFront == this.data.length) {
				tempFront = 0;
			} 
			i++;
		}	
		sb.append("]");
		return sb.toString();
	}
}