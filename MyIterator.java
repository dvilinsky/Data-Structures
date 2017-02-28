public interface MyIterator<E> {
	public boolean hasNext();
	public E next(boolean direction);
	public void remove();
}