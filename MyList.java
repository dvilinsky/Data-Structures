public interface MyList<E> extends Iterable<E> {
	public int size();
	public E get(int index);
	public int indexOf(E value);
	public boolean isEmpty();
	public boolean contains(E value);
	public void add(E value);
	public void add(int index, E value);
	public void removeAt(int index);
	public void remove(E value);
	public void set(int index, E value); //change data at certain index to certain value 
	public void clear();
}