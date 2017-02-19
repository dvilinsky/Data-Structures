public interface MyList<E> extends Iterable<E> {
	public double size();
	public E get(double index);
	public double indexOf(E value);
	public boolean isEmpty();
	public boolean contains(E value);
	public void add(E value);
	public void add(double index, E value);
	public void addAll(MyList<E> other);
	public void removeAt(double index);
	public void remove(E value);
	public void set(double index, E value); //change data at certain index to certain value 
	public void clear();
}