/** This class represents the abstraction of a Player in the Uno game. Every player knows 
*   who the next and previous players are, and can access and adjust them. We implement Comparable 
*   so that I can use addSorted in the DoublyLinkedList class  
*   @author Daniel Vilinsky
*/
public class Player implements Comparable<Player> {
	private String name; 
	private Player nextPlayer=null;
	private Player prevPlayer=null;
	private SinglyLinkedList<UnoCard> hand; 
	
	/** Constructor for this class. 
	*   Running time: O(1)
	*   @param name the name of the player 
	*/
	public Player(String name){
		this.name = name;
		this.hand = new SinglyLinkedList<UnoCard>();
	}
	
	/** Like the signature suggests, adds a card to this player's hand 
	*   Running time: O(1)
	*   @param c the card to be added 
	*/
	public void addToHand(UnoCard c){
		this.hand.regularInsert(c);
	}
	
	/** Removes the card at the given index from the player's hand 
	*   Running time: O(n), where n is the length of the underlying list
	*   @param index the index of the card to remove
	*/
	public UnoCard removeFromHand(int index){
		return this.hand.removeAt(index);
	}
	
	/** Returns true when this player has won- that is, if their hand contains no elements 
	*   Running time: O(1)
	*   @return true if their hand contains no elements 
	*/
	public boolean winner(){
		return this.hand.isEmpty();
	}
	
	/** Running time: O(1)
	*   @return the player to the right of this player in the circle 
	*/
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	/** Running time: O(1)
	*   @param nextPlayer the next player in the circle 
	*/
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	/** Running time: O(1)
	*   @return the player to the left of this player in the cirlce 
	*/
	public Player getPrevPlayer() {
		return prevPlayer;
	}

	/** Running time: O(1)
	*   @param prevPlayer the player to the left of this player in the cirlce 
	*/
	public void setPrevPlayer(Player prevPlayer) {
		this.prevPlayer = prevPlayer;
	}
	
	/** Running time: O(1)
	*   @return this player's name 
	*/
	public String getName() {
		return this.name;
	}
	
	/** Running time: O(1)
	*   @return this players hand
	*/
	public SinglyLinkedList<UnoCard> getHand() {
		return this.hand;
	}
	
	/** One player is less than another if the name is lexicographically prior to the other 
	*   Running time: I'll guess O(n). To compare two strings, you might have to compare every character 
	*   @return 0 if equal, some negative if less than, some positive if greater than 
	*   @param other the Player to which to compare this player 
	*/
	public int compareTo(Player other) {
		return this.name.compareTo(other.getName());
	}
	
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player other = (Player) o;
			return this.getName().equals(other.getName());
		} else {
			return false;
		}
	}

	/** This method returns a string containing information about who the next and previous players are,
	*   their name, and the contents of their hand. 
	*   Running time: O(n), due to the call on this.hand.toString().
	*   @return string representation of this player
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("This player's name is ").append(this.name).append(". ");
		sb.append("Their hand is ").append(this.hand.toString());
		return sb.toString();
	}	
}
