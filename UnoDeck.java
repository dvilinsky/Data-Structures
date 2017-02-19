/* 
 * Class for the Uno Deck
 * The cards have already been created for you in the constructor, you just have to add them
 * to your linked list that 
 */

import java.util.*;
public class UnoDeck {
	private static final String[] REGULAR_COLORS = {"red", "yellow", "blue", "green"};
	//uncomment once you make your deck, then add all the cards in the constructor to your deck
	//private SinglyLinkedList<UnoCard> deck; // initialize this in your constructor
	//private SinglyLinkedList<UnoCard> discard; // initialize this in your constructor
	private UnoCard lastDiscarded;
	
	//http://play-k.kaserver5.org/Uno.html
	// There are 108 cards in a Uno deck. 
	// There are four suits, Red, Green, Yellow and Blue, 
	// each consisting of one 0 card, two 1 cards, two 2s, 3s, 4s, 5s, 6s, 7s, 8s and 9s; 
	// two Draw Two cards; two Skip cards; and two Reverse cards. 
	// In addition there are four Wild cards and four Wild Draw Four cards.

	public UnoDeck(){
		// Initialized as having all 108 cards, as described above
		
		for (String color : REGULAR_COLORS){
			new UnoCard(color, 0); // add one of your color in zero
			for (int i = 0; i<2; i++){
				// add numbers 1-9
				for (int cardNumber = 1; cardNumber<=9; cardNumber++){
					new UnoCard(color, i) // Add this to deck
				}
				// add 2 of each of the special card for that color
				new UnoCard(color, true, false, false); // add to deck
				new UnoCard(color, false, true, false); // add to deck
				new UnoCard(color, false, false, true); // add to deck
			}
			
		}
		// add 4 wild cards, and 4 draw 4 wild cards
		for (int i = 0; i<4; i++){
			new UnoCard(false); // add to deck
			new UnoCard(true); // add to deck
		}
	}


}
