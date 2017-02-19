import java.util.*;

class Node<E extends Comparable<E>> {
	public E data;
	public Node<E> next, previous;
	
	public Node() {
		this(null, null, null);
	}
	
	public Node(E data) {
		this(data, null, null);
	}
	
	public Node(E data, Node<E> next) {
		this(data, next, null);
	}
	
	public Node(E data, Node<E> next, Node<E> previous) {
		this.data = data;
		this.next = next; 
		this.previous = previous;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
}

/**This class is an implementation of a doubly-linked, circular list 
* It is circular in both directions
*/
public class MyLinkedList<E extends Comparable<E>> implements MyList<E> {
	private Node<E> front, back; //maintain pointers to front and back of list 
	private double size; //we don't want to use an int here because one of the benefits
						//of a linked list is that it is not bounded in size like an array or arraylist
						//technically this still has an upper bound of something like 2^63, though.
						//this means that all indices used throughout have to be doubles 
	
	public MyLinkedList() {
		front = null;
		back = null;
		size = 0;
	}
	
	//appends a new Node<E> containing the given data to end of list 
	public void add(E data) {
		Node<E> current = front;
		if (front == null) {
			front = new Node<E>(data);
			front.next = front;
			front.previous = front; 
			back = front;
		} else {
			while (current.next != front) { //if current.next == front, we've reached the end of the list- it is looping back around 
				current = current.next;
			}
			current.next = new Node<E>(data);
			current.next.previous = current;
			current.next.next = front; //we've hit end so loop it around
			front.previous = current.next;
			back = current.next; 
		}
		size++;
	}
	
	//inserts a new Node<E> with the given data at the given index
	public void add(double index, E data) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0 && !isEmpty()) { //add to front of list 
			Node<E> temp = new Node<E>(data);
			temp.next = front; 
			temp.previous = front.previous;
			front.previous.next = temp;
			front.previous = temp;
			front = temp;
			size++;
		} else if ((index == 0 && isEmpty())) {
			add(data); //adding to the front of empty list is the same as adding to the end of it
		} else if (index == size()) {
			add(data); //add to end of list. Could I merge this and the 
						//previous if together? Yes. I think this makes it clearer, though 
		} else { //add somewhere in the middle 
			Node<E> current = front;
			for (double i = 0; i < index - 1; i++) {
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
	
	//assuming that the list is already sorted, insert a Node<E> containing
	//the given value such that the sorted order is maintained
	public void addSorted(E data1) {
		Node<E> current = front;
		double index = 0;
		if (front == null || data1.compareTo(front.data) < 0) {//add to front of list 
			add(0, data1);
			return; //this prevents me from adding numbers twice to the beginning of list;
		} else {
			while (current.next != front && current.next.data.compareTo(data1) < 0) {
				current = current.next;
				index++;
			}
		}
		if (index < size-1) { //add anywhere but the end of the list
			add(index+1,data1);
		} else { //add at end of the list 
			add(data1); 
		}
	}
	
	//TODO: IMPLEMENT ITERATOR FOR THIS CLASS
	//This method appends all of the values of List other to the end 
	//of this list 
	public void addAll(MyList<E> other) {
		Iterator itr = other.iterator();
		while (itr.hasNext()) {
			add((E)itr.next());
		}
	}
	
	public void set(double index, E data1) {
		if (this.isEmpty()) {
			add(data1);
			return;
		} 
		if (!(index >= 0 && index < size() -1)) {
			throw new IndexOutOfBoundsException("Error: Index cannot be greater than length of list");
		}
		Node<E> current = front;
		for (double i = 0; i < index  && current.next !=front; i++) {
			current = current.next;
		}
		current.data = data1;
	}

	public void remove(E value) {
		double where = this.indexOf(value);
		if (where != -1) {
			removeAt(where);
		} else {
			return;
		}
	}
	
	public void removeAt(double index) {
		Node<E> current = front;
		if (this.isEmpty()) {
			return;
		}
		if (index == 0) {
			if (size == 1) {
				clear();
				return; //clear reduced size to 0, so we don't want to decrement it again below 
			} else {
				front.next.previous = front.previous;
				front.previous.next = front.next;
				front.previous = null;
				front = front.next; //advance front one space
			}
		} else if (index > size() -1) {
			throw new IndexOutOfBoundsException("Error: Cannot remove at index greater than length of list");
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next.next.previous = current;
			current.next = current.next.next;
		}
		size--;
	}
	
	public double size() {
		return this.size;
	}
	
	//return the Node<E>'s data at a given index's value 
	public E get(double index) {
		Node<E> current = front;
		double i = 0;
		while (i < index) {
			current = current.next;
			i++;
		}
		if (i >= 0 && i < size()) {
			return current.data;
		} else {
			throw new IndexOutOfBoundsException();		
		}
	}
	
	//returns the index of the first Node<E> with the given value 
	public double indexOf(E data) {
		if (this.isEmpty()) { //I hope I don't have too much special case code here.
			return -1;
		}
		Node<E> current = front;
		double i = 0;
		while (!current.data.equals(data) && i < size()) {
			current = current.next;
			i++;
		}
		return i != size() ? i : -1; //if you didn't hit size, congrats you found it, else you didn't return -1
	}
	
	public E findMax() {
		Node<E> curr = this.front;
		E max = curr.data;
		while (curr.next != front) {
			curr = curr.next;
			if (curr.data.compareTo(max) > 0) {
				max = curr.data;
			}
		}
		return max;
	}
	
	public E findMaxR() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("Error: Empty list does not have max element");
		}
		E max = findMaxR(front);
		return max;
	}
	
	private E findMaxR(Node<E> n) {
		E max = n.data;
		if (n.next == front) {
			return max;
		} else {
			max = max(max, findMaxR(n.next));
			return max;
		}
	}
	
	private E max(E elem1, E elem2) {
		if (elem1.compareTo(elem2) > 0) {
			return elem1;
 		} else {
			return elem2;
		}
	}
	
	public boolean contains(E data) {
		return indexOf(data) != -1; //if the index is not -1, it's in the list 
	}
	
	public String toString() {
		Node<E> current = front;
		if (front == null) {
			return "[]";
		}
		String listString = "[" + current.data;
		while (current.next != front) {
			current = current.next;
			listString += ", " + current.data;
		}
		listString += "]";
		return listString;
	}
	
	public boolean isEmpty() {
		return this.front == null;
	}
	
	public void clear() {
		front = null;
		back = front;
		size = 0;
	}
	
	
	public Iterator<E> iterator() {
		return new MyLinkedListIterator();
	}
	
	private class MyLinkedListIterator implements Iterator<E> {
		private Node<E> current; //node to remove 
		private boolean removeOK;
		
		public MyLinkedListIterator() {
            current = front;
            removeOK = false;
        }
		
		public boolean hasNext() {
			return current.next != front;
		}
		
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} 
			E data = current.data;
			current = current.next;
			removeOK = true;
			return data;
		}
		
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			current.previous.next = current.next;
			current.next.previous = current.previous;
			current.previous = null;
			current.next = null; //this line and the last is to not leave any dangling pointers 
			size--;
			removeOK = false; 
		}
		
		
	}
}
