import java.util.*;

/** This is how we represent the participants in the Uno game. It basically just provides
*   a layer of abstraction over the DoublyLinkedList class and only lets you access that 
*   class in certain ways. 
*   @author Daniel Vilinsky 
*/
public class PlayerCircle implements Iterable<Player> {
	private DoublyLinkedList<Player> circle;
	
	/** Constructor for this class. Initializes the underlying list
	*   Running time: O(1)
	*/
	public PlayerCircle() {
		this.circle = new DoublyLinkedList<Player>();
	}
	
	/** Adds p to the circle in sorted (alphabetical) order 
	*   Running time: O(n)- we may have to add to the end of the list 
	*   @param p the Player to add to the circle 
	*/
	public void addToCircle(Player p) {
		this.circle.addSorted(p);
	}
	
	/** Get's the first player in the circle. 
	*   Running time: O(1). While DoublyLinkedList::get(index) is O(n) in the worst case, 
	*   if this method only calls get(0), then that method runs in constant time. 
	*   @return the first player 
	*/
	public Player getFirstPlayer() {
		return this.circle.get(0);
	}
	
	/** Return size of the circle, which is just the size of the underlying list 
	*   Running time: O(1)
	*   @return size of the list 
	*/
	public int getSize() {
		return this.circle.size();
	}
	
	/** Ultimately, this will return something like this: [Player1.toString, Player2.toString...]. See documentation
	*   of Player.java to see what that looks like. 
	*   For a discussion of the running time, see DoublyLinkedList::toString()
	*   @return a string representation of this playercircle
	*/
	public String toString() {
		return circle.toString();
	}
	
	
	public Iterator<Player> iterator() {
		return circle.iterator();
	}
	
	public MyIterator<Player> endlessIterator() {
		return circle.endlessIterator();
	}
}