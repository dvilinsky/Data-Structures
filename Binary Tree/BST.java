import java.util.NoSuchElementException;

class Node<E extends Comparable<E>> implements Comparable<E> {
	public E data;
	public Node<E> leftChild, rightChild, parent;
	
	public Node() {
		this(null, null, null, null);
	}
	
	public Node(E data) {
		this(data, null, null, null);
	}
	
	public Node(E data, Node<E> leftChild, Node<E> rightChild, Node<E> parent) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parent = parent;
	}
	
	public boolean hasLeft() {
		return this.leftChild != null;
	}
	
	public boolean hasRight() {
		return this.rightChild != null;
	}
	
	public boolean isLeaf() {
		return this.rightChild == null && this.leftChild == null;
	}
	
	public boolean isRoot() {
		return this.parent == null;
	}
	
	public boolean isLeftChild() {
		if (!this.isRoot()) { //if a node is a root. a call on this.parent.data throws null pointer exception
			return this.data.compareTo(this.parent.data) < 0;	
		} else {
			return false;
		}
	}
	
	public boolean isRightChild() {
		if (!this.isRoot()) {
			return this.data.compareTo(this.parent.data) > 0;
		} else {
			return false;
		}
	}
	
	public boolean hasParent() {
		return this.parent != null;
	}
	
	public boolean isInternalNode() {
		return this.leftChild != null || this.rightChild != null;
	}
	
	public boolean isExternalNode() {
		return !this.isInternalNode();
	}
	
	/** This method returns the depth of a Node v, where the depth is the number of edges along the path
	*   from the node to the root. Running time: O(n) in a worst case, but I think it should be less
	*   because this tree is balanced. O(n) would happen in a linked-list-turned-on-its-side tree 
	*/
	public int depth() {
		return depth(this);
	}
	
	private int depth(Node<E> v) {
		if (v.isRoot()) {
			return 0;
		} else {
			return 1 + depth(v.parent);
		}
	}
	
	//again if this.parent is public, not really necessary
	//but I guess at least it does some error-checking
	public Node<E> getParent() {
		if (!this.isRoot()) {
			return this.parent;
		} else {
			throw new NoSuchElementException("Error: This node has no parent. It is the root");
		}
	}
	
	//not really necessary if this.data is public 
	public E getData() {
		return this.data;
	}
	
	public int compareTo(E other) {
		return this.data.compareTo(other);
	}
}

public class BST<E extends Comparable<E>> implements MyTree<E> {
	private Node<E> topRoot;
	private double size; //number of nodes in the tree 
						//using a double means that we don't have undefined behavior until the 
						// size reaches 2^63, instead of 2^31 if we used an int
	
	/** Constructs an empty Binary Search Tree. An empty tree is one where the 
	*	root Node is null. 
	*   Running time: O(1)
	*/
	public BST() {
		this.topRoot = null;
		this.size = 0;
	}
	
	/** Constructs a Binary Search Tree with a root node containing the given data. 
	*   Running time: O(1)
	*/
	public BST(E data) {
		this.topRoot = new Node<E>(data);
		this.size++;
	}
	
	public Node<E> getTopRoot() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("Error: Tree is empty");
		} else {
			return this.topRoot;
		}
	}
	
	/** Adds a node with the given value to the binary tree, maintaining the binary search tree 
	*   property. Running time: O(log(n)), since this is a balanced tree. 
	*/
	public void add(E value) throws ElementAlreadyThereException {
		this.topRoot = add(value, topRoot, topRoot);
		this.size++;
	}
	
	//helper method for add() above.
	private Node<E> add(E value, Node<E> root, Node<E> lastPassed) throws ElementAlreadyThereException {
		boolean flag = (root == lastPassed);
		if (root == null) {
			root = new Node<E>(value);
		} else if (value.compareTo(root.data) < 0) {
			root.leftChild = add(value, root.leftChild, root);
		} else  if (value.compareTo(root.data) > 0) { 
			root.rightChild = add(value, root.rightChild, root);
		} else {
			//throw exception- we're not allowing duplicates
			throw new ElementAlreadyThereException(value);
		}
		//prevent root's parent from being itself
		if (!flag) {
			root.parent = lastPassed;
		}
		//todo todo todo: BALANCE THE TREE
		//BALANCE BALANCE BALANCE BALANCE BALANCE BALANCE 
		return root;
	}
	
	/** Adds a root to the tree if the tree is empty. Running time: O(1). 
	*/
	public void addRoot(E value) throws ElementAlreadyThereException    {
		if (this.isEmpty()) {
			add(value);
		} else {
			throw new ElementAlreadyThereException("Error: already has a root");
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////
	/*public void delete(E value) {
		Node<E> toDelete = this.findNode(value);
		if (toDelete == null) {
			System.out.println("No such node containing " + value.toString() + " in the tree.");
			return;
		}
		//Case 1: Node is a leaf 
		if (toDelete.isLeaf()) {
			if(toDelete.isLeftChild()) {
				toDelete.parent.leftChild = null;
			} else {
				toDelete.parent.rightChild = null;
			}
		} else if ((toDelete.hasLeft() && !toDelete.hasRight()) || (toDelete.hasRight() && !toDelete.hasLeft())) {
			//Case 2: Node has one child
			
		}
	} */
	///////////////////////////////////////////////////////////////////////////
	
	/** Prints the preorder traversal of the tree. Since there are N nodes in this tree, and 
	*   eacn node has to be visited once, the running time is O(N);
	*/
	public void printPreorder() {
		System.out.print("Preorder traversal: ");
		printPreorder(topRoot);
		System.out.println();
	}
	
	//helper method for printPreorder above 
	private void printPreorder(Node<E> topRoot) {
		if (topRoot != null) {
			System.out.print(" " + topRoot.data); //visit
			printPreorder(topRoot.leftChild); //go left 
			printPreorder(topRoot.rightChild); //go right 
		}
	}
	
	/** Prints the inorder traversal of the tree. Running time is O(N)- there are N nodes in this tree 
	*   and they each have to be visited once 
	*/
	public void printInorder() {
		System.out.print("Inorder traversal:");
		printInorder(topRoot);
		System.out.println();
	}
	
	//helper method of printInorder above 
	private void printInorder(Node<E> topRoot) {
		if (topRoot != null) {
			printInorder(topRoot.leftChild); //go left 
			System.out.print(" " + topRoot.data); //vist 
			printInorder(topRoot.rightChild); //go right 
		}
	}
	
	/** Prints the postorder traversal of this tree. Running time is O(N) because there are N nodes, 
	*   each of which needs to be visited once.
	*/
	public void printPostorder() {
		System.out.println("Postorder traversal: ");
		printPostorder(topRoot);
		System.out.println();
	}

	//helper method for printPostorder above
	private void printPostorder(Node<E> topRoot) {
		if (topRoot != null) {
			printPostorder(topRoot.leftChild); //go left 
			printPostorder(topRoot.rightChild); //go right 
			System.out.print(" " + topRoot.data); //visit 
		}
	}
	
	/** Prints the level order traversal of this tree. Since there are N nodes that need to be 
	*   printed, running time is O(n).
	*/
	public void printLevelOrder() {
		MyQueue<Node<E>> q = new MyQueue<Node<E>>();
		q.enqueue(topRoot);
		while (!q.isEmpty()) {
			Node<E> n = q.dequeue();
			System.out.print(n.data + " ");
			if (n.leftChild != null) {
				q.enqueue(n.leftChild);
			}
			if (n.rightChild != null) {
				q.enqueue(n.rightChild);
			}
		}
	}
	
	/** Returns the node containing given value, or null if no node contains that value. 
	*   I don't like this though- clients really shouldn't be interfacing with my Node class
	*   Since this is a balanced tree, running time is O(log(n))
	*/
	public Node<E> search(E value) {
		return search(value, topRoot);
	}
	
	//helper method for search(E value) above
	private Node<E> search(E value, Node<E> topRoot) {
		if (topRoot == null) {
			return null; //case where we didn't find the value 
		} else if (value.compareTo(topRoot.data) == 0) {
			return topRoot;
		} else if (value.compareTo(topRoot.data) < 0) {
			return search(value, topRoot.leftChild);
		} else {
			return search(value, topRoot.rightChild);
		}
	}
	
	/** A search(E value) by any other name would smell as sweet...
	*   So, the running time is the same as search(E value)
	*/
	public Node<E> findNode(E value) {
		return search(value);
	}
	
	/** Returns the Node containing the minimum value. It really shouldn't return the node, but rather 
	*   the value itself. But that's what the assignment says. Running time: O(log(n)) 
	*/
	public Node<E> findMin() {
		return findMin(topRoot);
	}
	
	//helper method for findMind above
	private Node<E> findMin(Node<E> topRoot) {
		if (topRoot.leftChild == null) {
			return topRoot;
		} else {
			return findMin(topRoot.leftChild);
		}
	}
	
	/** Returns true if there is a node in this tree that contains the given value, false otherwise 
	*   Running time: O(log(n)).
	*/
	public boolean contains(E value) {
		return findNode(value) != null;
	}
	
	/** Returns the size of the tree. Running time: O(1)
	*/
	public double size() {
		return this.size;
	}
	
	/** For whatever reason, we've been told to implement a size() function recursively. This makes no sense
	*   as it has turned getting the size, which should always be O(1), into an O(N) operation. 
	*/
	public double recursiveSize() {
		return recursiveSize(topRoot);
	}
	
	//helper method for recursiveSize above 
	private double recursiveSize(Node<E> topRoot) {
		if (topRoot == null) {
			return 0;
		} else {
			return 1 + recursiveSize(topRoot.leftChild) + recursiveSize(topRoot.rightChild);
		}
	}
	
	/** Returns true if the tree is empty, i.e. the root is null. False otherwise
	*   Running time: O(1)
	*/
	public boolean isEmpty() {
		return topRoot == null;
	}
	
	/** Returns the number of levels in a tree. The number of levels is equal to the level of the 
	*   deepest node- the one furthest from the root. A tree with just the topRoot has 1 level
	*   Running time: ???????????
	*/
	public int numLevels() {
		return numLevels(topRoot);
	}
	
	//helper method for numLevels above 
	private int numLevels(Node<E> topRoot) {
		if (topRoot == null) {
			return 0; //an empty tree has no levels 
		} else {
			//get height of left subtree from root, right subtree from root. Whichever 
			//one is greater, that's the number of levels + 1 (account for root)
			return Math.max(numLevels(topRoot.leftChild), numLevels(topRoot.rightChild)) + 1;
		}
	}
	
	/** Returns the height of the tree, where height is the number of edges along the longest path from the root to 
	*	a node. An empty tree, as well as a tree with one node, has a height of 0.
	*   Running time: same as numLevels()
	*/
	public int height() {
		return height(topRoot);
	}
	
	//helper method for height() above
	private int height(Node<E> topRoot) {
		if (topRoot == null) {
			return 0;
		}
		if (topRoot.isLeaf()) {
			return 0;
		} else {
			return 1 + Math.max(height(topRoot.leftChild), height(topRoot.rightChild));
		} 
	}
}