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
	
	/** This method returns the node that would come after this node if the nodes were listed by their 
	*   values in ascending order.  Running time: O(log(n)), where n is the number of nodes. In a balanced tree,
	*	the number of parents a Node can have is at most height - 1, and the height is proportional to the log of the 
	*   number of nodes. Returns null if this Node has no successor. 
	*/
	public Node<E> successor() {
		if (this.rightChild != null) {
			return findMin(this.rightChild);
		} else {
			Node<E> w = this.parent;
			Node<E> n = this;
			while (w != null && n == w.rightChild) {
				n = w;
				w = w.parent;
			}
			return w;
		}
	}
	
	//Helper method for successor above. Finds the minimum node in the tree rooted at the parameter, root,
	private Node<E> findMin(Node<E> root) {
		if (root.leftChild == null) {
			return root;
		} else {
			return findMin(root.leftChild);
		}
	}
	
	//really wish this was private, but couldn't call it outside the class if that were the case. 
	//Returns the number of immediate children of this node. 
	public int numChildren() {
		int children = 0;
		if (this.rightChild != null) {
			children++;
		}
		if (this.leftChild != null) {
			children++;
		}
		return children;
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
	
	/** Deletes the given value from this tree. Running time: O(log(n)), since findNode is O(log(n)) and after that 
	*   we only do a small number of constant time operations.
	*/
	public void delete(E value) {
		Node<E> toDelete = findNode(value);
		int numChildren = toDelete.numChildren();
		if (numChildren == 0) {
			//delete case with no kids. Set parent to null
			if (toDelete.isLeftChild()) {
				toDelete.parent.leftChild = null; 
			} else {
				toDelete.parent.rightChild = null;
			}
		} else if (numChildren == 1) {
			//"skip over" node to be deleted- basically like deleting in a linked list 
			deleteOneChild(toDelete);
		} else { 
			//case where node has 2 kids- swap with successor, then do case 0 or case 1 
			Node<E> successor = toDelete.successor();
			swap(toDelete, successor);
		}
	}
	
	//This method swaps the node to be deleted with its successor, so that we can then delete it
	private void swap(Node<E> toDelete, Node<E> successor) {
		
	}
	
	//helper method for the delete case where a node has one child 
	private void deleteOneChild(Node<E> toDelete) {
		int nodeCase = findCase(toDelete);
		switch (nodeCase) {
			case 1: 
				toDelete.parent.leftChild = toDelete.leftChild;
				toDelete.leftChild.parent = toDelete.parent;
				toDelete.leftChild = null;
				break;
			case 2:
				toDelete.parent.rightChild = toDelete.leftChild; 
				toDelete.leftChild.parent = toDelete.parent;
				toDelete.leftChild = null;
				break;
			case 3:
				toDelete.parent.rightChild = toDelete.rightChild; 
				toDelete.rightChild.parent = toDelete.parent;
				toDelete.rightChild = null;
				break;
			case 4:
				toDelete.parent.leftChild = toDelete.rightChild;
				toDelete.rightChild.parent = toDelete.parent;
				toDelete.rightChild = null;
				break;
		}
	}
	
	//When deleting a node with 1 child, their are four cases. This method returns either 1, 2, 3, or 4, depending 
	//on which case it is. 
	private int findCase(Node<E> v) {
		if (v.hasLeft() && v.isLeftChild()) {
			return 1;
		} else if (v.hasLeft() && v.isRightChild()) {
			return 2;
		} else if (v.hasRight() && v.isRightChild()) {
			return 3;
		} else { //v.hasRight() && v.isLeftChild()
			return 4;
		}
	}
	
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
	
	/** Returns the Node containing the maximum value in this tree. Running time: O(h), where h is the height of the tree. 
	*   Since this is a balance tree, h is proportionaly to log(n), where n is the number of nodes. So, running time 
	*   turns out to be O(log(n))
	*/
	public Node<E> findMax() {
		return findMax(topRoot);
	}
		
	//helper method for findMax above 
	private Node<E> findMax(Node<E> topRoot) {
		if (topRoot.rightChild == null) {
			return topRoot;
		} else {
			return findMax(topRoot.rightChild);
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
		return numLevels(topRoot) - 1;
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