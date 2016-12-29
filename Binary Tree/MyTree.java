public interface MyTree<E extends Comparable<E>> {
	public void printPostorder(); //prints post order traversal of tree
	public void printInorder();  //prints inorder traversal of tree
	public void printPreorder(); //prints preorder traversal of tree 
	public void printLevelOrder();
	public double size();
	public boolean isEmpty();
	public int numLevels();
	public int height();
	public double recursiveSize();
	public void add(E value) throws ElementAlreadyThereException;
	public void addRoot(E value) throws ElementAlreadyThereException;
	public Node<E> search(E value);
	public Node<E> findNode(E value);
	public boolean contains(E value);
	public Node<E> findMin();
}