/** 
 * Class for the Uno Deck
 * The cards have already been created for you in the constructor, you just have to add them
 * to your linked list that 
 */

import java.util.*;
public class UnoDeck {
	private static final String[] REGULAR_COLORS = {"red", "yellow", "blue", "green"};
	private SinglyLinkedList<UnoCard> deck; //deck from which players draw cards 
	private SinglyLinkedList<UnoCard> discard; // discard pile
	private UnoCard lastDiscarded;
	
	/** http://play-k.kaserver5.org/Uno.html
	 *  There are 108 cards in a Uno deck. 
	 *  There are four suits, Red, Green, Yellow and Blue, 
	 *  each consisting of one 0 card, two 1 cards, two 2s, 3s, 4s, 5s, 6s, 7s, 8s and 9s; 
   	 *  two Draw Two cards; two Skip cards; and two Reverse cards. 
	 *  In addition there are four Wild cards and four Wild Draw Four cards.
	 *  This constructor initializes our deck to conform to that standard. The deck is also randomly ordered
	 *  Running time: Who knows?
	 */
	public UnoDeck(){
		this.deck = new SinglyLinkedList<UnoCard>();
		this.discard = new SinglyLinkedList<UnoCard>();
		this.lastDiscarded = null;
		for (String color : REGULAR_COLORS){
			deck.randomInsert(new UnoCard(color, 0)); // add one of your color in zero
			for (int i = 0; i<2; i++){
				// add numbers 1-9
				for (int cardNumber = 1; cardNumber<=9; cardNumber++){
					deck.randomInsert(new UnoCard(color, cardNumber)); 
				}
				// add 2 of each of the special card for that color
				deck.randomInsert(new UnoCard(color, true, false, false)); 
				deck.randomInsert(new UnoCard(color, false, true, false));
				deck.randomInsert(new UnoCard(color, false, false, true)); 
			}
			
		}
		// add 4 wild cards, and 4 draw 4 wild cards
		for (int i = 0; i<4; i++){
			deck.randomInsert(new UnoCard(false)); 
			deck.randomInsert(new UnoCard(true));
		}
	}
	
	/** Adds the card c to a random place in the discard pile 
	*   Running time: O(n)
    *   @param c, the card to discard 
	*   @exception IllegalArgumentException if the card is invalid
	*/
	public void discardCard(UnoCard c) {
		if (discard.isEmpty() || c.canBePlacedOn(this.lastDiscarded)) {
			discard.randomInsert(c);
			this.lastDiscarded = c;			
		} else {
			throw new IllegalArgumentException("Error: card " + c + " can't be placed on " + lastDiscarded);
		}
	}
	
	/** Gets the most recently discarded card. Since I've written amazing self-documenting code,
	*   that was evident from the method signature 
	*   Running time: O(1)
	*   @return top of the discard pile 
	*/
	public UnoCard getLastDiscarded() {
		return this.lastDiscarded;
	}
	
	/** Draws a card from the top of the deck. If the deck is empty, it moves all the cards from 
	*   the discard pile into the deck. 
	*   Running time: O(n)if the deck is empty, where n is the size of the discard pile. I claim that 
	*   it's otherwise O(1). removeAt(index) is technically an O(n) method. However, when we pass it 
	*   the index 0, the for loop in the nodeAt(index) helper method it uses never runs. So, it only 
	*   ends up doing a bunch of constant time operations. 
	*   @return: the UnoCard at the top of the deck 
	*/
	public UnoCard drawCard() {
		//WHAT IF THE DISCARD IS ALSO EMPTY?!???!?!?!?!?!
		if (this.deck.isEmpty()) {
			Iterator<UnoCard> i = this.discard.iterator();
			while (i.hasNext()) {
				UnoCard uc = i.next();
				this.deck.regularInsert(uc); //we don't insert randomly because the discard deck is already random
				i.remove();
			}
		}
		return this.deck.removeAt(0);
	}
	
	/** Returns the contents of the deck in this UnoDeck 
	*   Running time: O(n). The call to toString of the deck object runs in O(n). and since append()
	*   is a constant time operation, total running time is O(n)
	*   @return string representation of the deck object 
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("The deck is ").append(this.deck.toString());
		return sb.toString();
	}
	
	/** Returns the discard pile of this UnoDeck 
	*   Running time: Like above, is O(n)
	*   @return string representation of the discard pile 
	*   @param discardPile flag to print discardPile or deck 
	*/
	public String toString(boolean discardPile) {
		StringBuilder sb = new StringBuilder();
		sb.append("The discard pile is ").append(this.discard.toString());
		return sb.toString();
	}	
	
	public int size() {return deck.size();}
}
