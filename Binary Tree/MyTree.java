/** Why does this interface exist? Basically so I can quickly reference all of the methods available 
*   to me when using my tree in a client program
*/
public interface MyTree<E extends Comparable<E>> {
	public void printPostorder(); 
	public void printInorder();  
	public void printPreorder();  
	public void printLevelOrder();
	public double size();
	public boolean isEmpty();
	public int numLevels();
	public int height();
	public double recursiveSize();
	public void add(E value) throws ElementAlreadyThereException;
	public void addRoot(E value) throws ElementAlreadyThereException;
	public void delete(E value);
	public Node<E> search(E value);
	public Node<E> findNode(E value);
	public boolean contains(E value);
	public Node<E> findMin();
	public Node<E> findMax();
}