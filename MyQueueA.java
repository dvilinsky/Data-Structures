import java.util.*;

public class MyQueueA<E> {
	private static final int _SIZE = 50;
	
	private E[] data;
	private int front, rear; //indices of front and rear of queue 
	private int size;
	
	public MyQueueA() {
		this(_SIZE);
	}
	
	public MyQueueA(int size) {
		this.front = 0;
		this.rear = 0;
		this.data = (E[]) new Object[size];
		this.size = 0;
	}
	
	public void enqueue(E value) {
		data[rear] = value;
		if (rear == data.length) {
			rear = 0;
		} else {
			rear++;
		}
		size++;
	}
	
	public E dequeue() {
		E temp = data[front];
		if (front == data.length) {
			front = 0;
		} else  {
			front++;
		}
		size--;
		return temp;
	}
	
	public E front() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("Error: Queue is empty");
		} else {
			return data[front];
		}
	}
	
	public E rear() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("Error: Queue is empty.");
		} else {
			if (rear == 0) {
				return data[rear];
			} else {
				return data[rear-1];
			}
		}
	}
	public boolean isEmpty() {
		return this.size == 0; //fuck that front == rear bullshit
	}
	
	public int size() {
		return this.size;
	}
}