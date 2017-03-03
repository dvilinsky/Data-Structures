/** This interface exists so that I can have an EndlessIterator over any 
*   generic type. The compiler didn't like it when I tried to define that class as 
*   private class EndlessIterator<E>. But it doesn't mind me doing this.
*/
public interface MyIterator<E> {
	public boolean hasNext();
	public E next();
	public void advance(boolean direction);
}