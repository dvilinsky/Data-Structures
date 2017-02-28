import java.util.EmptyStackException;

public class MyStack<E extends Comparable<E>> { 
	private MyLinkedList<E> stack;
	private double size; //making it a double so that the "capacity" is greater than if it were int.
	
	//initializes the underlying linked list and sets the size to 0;
	//running time: O(1);
	public MyStack() {
		stack = new MyLinkedList();
		size = 0;
	}
	
	//adds a value to the front of the list. While add(double index) is technically an O(n) operation
	//since we are only going one place, this runs in O(1)
	public void push(E value) {
		stack.add(0, value);
		size++;
	}
	
	//Stores first element of underlying list into a temp variable, then removes that value from
	//the underling list. Running time: O(1)
	//Throws exception if the stack is empty;
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		E temp = stack.get(0);
		stack.remove(0);
		size--;
		return temp;
	}
	
	//Returns first element of underlying list. 
	//Running time: O(1)
	public E peek() {
		return stack.get(0);
	}
	
	public double size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0; //should I replace this with a call to the isEmpty() method of MyLinkedList?
	}
	
	//This prints the underlying list, with the first element being the top of the stack, and the 
	//last element being the bottom of the stack;
	//Running time: O(n)
	public String toString() {
		return stack.toString();
	}
}