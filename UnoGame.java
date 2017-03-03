import java.util.*;

/** Main method of the program. Let's you play the game of Uno on the command line. 
*   It is fairly robust, but it doesn't check to make sure that the user is entering 
*   the proper type of input- e.g. a number when asked for a number. 
*/
public class UnoGame {
	public static final int MAX_PLAYERS = 5; //maximum number of players per game
	
	/** Main method of the program. Sets up the data structures I'll need, then 
	*   plays the game until the user doesn't want to play anymore. 
	*   Running time: It's tricky to describe the running time of this method because at 
	*   many steps, the program waits for user input- getting the number of players, getting
	*   their names, playing the game, etc. I don't know if it makes sense to say that as some 
	*   input gets large, the running is asymptotically bounded by some function. 
	*   @param args command line arguments.
	*/
	public static void main(String[] args) {
		PlayerCircle players = new PlayerCircle();
		Queue<Player> waiting = intialize();
		getPlayers(players, waiting);
		UnoDeck ud = new UnoDeck();
		createHands(players, ud);
		printHands(players);
		ud.discardCard(ud.drawCard()); //get the discard pile going 
		System.out.println("The top of the discard pile is " + ud.getLastDiscarded());
		String keepPlaying = "yes";
		Scanner console = new Scanner(System.in);
		while (keepPlaying.equals("yes")) {
			Player loser = play(players, ud);
			System.out.println("Sorry, " + loser.getName() + ", you lost! :(");
			System.out.println("Would you like to play another game? Type \"yes\" if you would.");
			keepPlaying = console.next();
			update(players, waiting, loser);
		}
	}
	
	/** Creates a queue of the necessary size. Does error checking to make sure 
	*   we have at least two players. 
	*   Running time: See discussion in the comments for the main method
	*   @return an empty queue of Player objects
	*/
	public static Queue<Player> intialize() {
		Scanner console = new Scanner(System.in);
		System.out.println("Welcome to the game! How many players are you expecting?");
		int numPlayers = console.nextInt();
		while (numPlayers <= 1) {
			System.out.println("You must have at least 2 players. Please enter another number.");
			numPlayers = console.nextInt(); //Let's pray that the user enters a number. 
		}
		if (numPlayers <= MAX_PLAYERS) {
			return new Queue<Player>(1); //if we never need the queue to hold people waiting to play
										 //it will only ever be of size 1- for the loser who gets 
										//taken out immediately
		} else {
			return new Queue<Player>(numPlayers - MAX_PLAYERS);
		}
	}
	
	/** Gets the names of the players from the users. If there are more than five, 
	*   it adds them to the queue. 
	*   Running time: See discussion in the comments for the main method
	*   @param players the PlayerCircle 
	*   @param waiting the queue of players waiting to play 
	*/
	public static void getPlayers(PlayerCircle players, Queue<Player> waiting) {
		Scanner console = new Scanner(System.in);
		System.out.println("Thank you. Now, please enter the names of everyone playing. Type \"end\" to quit. ");
		String name = console.nextLine();
		while(!name.equals("end")) { //assumes user won't enter "end" until done typing names
			if (players.getSize() < MAX_PLAYERS) {
				players.addToCircle(new Player(name));
			} else {
				waiting.enqueue(new Player(name));
			}
			name = console.nextLine();
		}
	}
	
	/** Adds seven cards to each player's hand. 
	*   Running time: O(n), where n is the number of players 
	*   @param players the circle of players
	*   @param ud the deck of cards
	*/
	public static void createHands(PlayerCircle players, UnoDeck ud) {
		Iterator<Player> itr = players.iterator();
		while (itr.hasNext()) {
			Player p = itr.next();
			for (int i = 0; i < 7; i++) {
				p.addToHand(ud.drawCard());
			}
		}
	}
	
	/** Prints each player's hand 
	*   Running time: O(n), where n is the number of players
	*   @param players the circle of players
	*/
	public static void printHands(PlayerCircle players) {
		Iterator<Player> itr = players.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
	
	/** Engine for playing the game according to the rules described in the assignment 
	*   Running time: See discussion in the comments for the main method
	*   @param  players the circle of players
	*   @param ud the deck of cards
	*   @return the losing player 
	*/
	public static Player play(PlayerCircle players, UnoDeck ud) {
		MyIterator<Player> itr = players.endlessIterator();
		SinglyLinkedList<Boolean> streak = new SinglyLinkedList<Boolean>();
		boolean direction = true; 
	    int timesAround  = 0;
		while (itr.hasNext()) {
			UnoCard previous = ud.getLastDiscarded();
			direction = setDirection(direction, previous);
			Player player = getCurrentPlayer(itr, direction, previous);
			if (previous.isDrawTwo()) {
				add(player, ud, 2);
			} else if (previous.isWildDrawFour()) {
				add(player, ud, 4);
			}
			System.out.println("Hello " + player.getName());
			showCards(player);
			if (showValids(player, ud, previous)) {
				ud.discardCard(choose(player, ud));
			} else {
				System.out.println("Sorry! No valid cards!");
				player.addToHand(ud.drawCard());
			}
			if (player.winner()) {
				System.out.println("Congratulations, " + player.getName() + ",  You won!");
			}
			timesAround = calcTimesAround(streak, timesAround, direction, players.getSize());
		}
		System.out.println("You went around the circle " + timesAround + " times.");
		return findLoser(players);
	}
	
	/** When the game is over, this method removes the loser from the PlayerCircle,
	*   adds them to the waiting queue, and removes the next player in line 
	*   Running time: O(n), where n is the size of the PlayerCircle, because we have 
    *   to go searching for that player in the PlayerCircle. 
    *   @param players the circle of players	
	*   @param waiting the queue of players waiting to play 
	*   @param loser the losing player (one with the most cards)
	*/
	public static void update(PlayerCircle players, Queue<Player> waiting, Player loser) {
		players.removeFromCircle(loser);
		waiting.enqueue(loser);
		players.addToCircle(waiting.dequeue());
	}
	
	/** Adds "amount" number of cards to the given player's hand 
	*   Running time: O(n), where n is amount of cards 
	*   @param player the player to whose hand to add cards 
	*   @param ud the deck of cards 
	*   @param amount the number of cards to add 
	*/
	private static void add(Player player, UnoDeck ud, int amount) {
		for (int i = 0; i < amount; i++) {
			player.addToHand(ud.drawCard());
		}
	}
	
	/** If the previous card was a reverse card, this method switches my direction flag
	*   Running time: O(1)
	*   @param direction the direction around the circle 
	*   @param previous the previous card placed on the discard pile 
	*   @return the new direction to go in
	*/
	private static boolean setDirection(boolean direction, UnoCard previous) {
		return previous.isReverse() ? !direction : direction;
	}
	
	/** The order of turns in Uno typically depends on going to either the right 
	*   or the left of the dealer. Let's say I have a list like A -> B-> C-> D-> E-> F-> G
	*   which loops back to A. A is the dealer. To start the game he puts down a reverse card. 
	*   So it's G's turn. If he put down a red 3 to start, for example, we'd go to B. See 
	*   http://www.unorules.com, section "Game Play."
	*   Running time: O(1)
	*   @param itr a MyIterator over the PlayerCircle
	*   @param direction the direction around the circle we're going 
	*   @param previous the top of the discard pile 
	*   @return the player whose turn it is
	*/
	private static Player getCurrentPlayer(MyIterator<Player> itr, boolean direction, UnoCard previous) {
		itr.advance(direction);
		if (previous.isSkip()) {
			itr.advance(direction);
		}
		return itr.next();
	}

	/** Prints the player's hand. Running time: O(n), where n is 
	*   the number of cards in the player's hand.
	*   @param player the player whose hand we're printing
	*/
	private static void showCards(Player player) {
		System.out.println("Your hand is: ");
		System.out.println(player.getHand());
	}
	
	/** Prints what valid cards, if any, this player has. 
	*   Running time: O(n), where n is the number of cards in this player's hand 
	*   @param player the player whose hand we're checking to see if any cards are valid 
	*   @param ud the deck of cards 
	*   @param previous the top of the discard pile 
	*   @return true if this player has any valid cards
	*/
	private static boolean showValids(Player player, UnoDeck ud, UnoCard previous) {
		Iterator<UnoCard> itr = player.getHand().iterator();
		boolean hasSomeValid = false;
		System.out.print("The cards you can play are: ");
		while (itr.hasNext()) {
			UnoCard card = itr.next();
			if (card.canBePlacedOn(previous)) {
				System.out.print(card + ", ");
				hasSomeValid = true;
			}
		}
		System.out.println();
		return hasSomeValid;
	}
	
	/** Allows the player to pick a card to place down 
	*   validates that we chose a valid card, but doesn't valid index
	*   Running time: See discussion in the comments for the main method
	*   @param player the current player 
	*   @param ud the deck of cards 
	*   @return the card the player chose 
	*/
	private static UnoCard choose(Player player, UnoDeck ud) {
		//not super user friendly, but then again this isn't the crux of the assignment
		System.out.println("Please tell me the index of the card you'd like to play.");
		Scanner console = new Scanner(System.in);
		UnoCard c = player.removeFromHand(console.nextInt());
		while (!c.canBePlacedOn(ud.getLastDiscarded())) {
			System.out.println("Sorry, you can't place that card down. The cards you can place are: ");
			showValids(player, ud, ud.getLastDiscarded());
			c = player.removeFromHand(console.nextInt());
		}
		return c;
	}
	
	/** Finds the losing player- the one with the biggest hand 
	*   Running time: O(n), where n is the number of players 
	*   @param players the circle of players 
	*   @return the losing player 
	*/
	private static Player findLoser(PlayerCircle players) {
		Iterator<Player> itr = players.iterator();
		Player loser = itr.next();
		while (itr.hasNext()) {
			Player next = itr.next();
			if (next.getHand().size() > loser.getHand().size()) {
				loser = next;
			}
		}
		return loser;
	}
	
	/** I've decided that the number of times around the circle is equal to how 
	*   many streaks we had of hitting n - 1 players without changing direction 
	*   Running time: O(1)
	*   @param streak a SinglyLinkedList containing our current streak of turns 
	*   @param timesAround number of times around the circle so far 
	*   @param direction the direction around the circle 
	*   @param numPlayers the size of the PlayerCircle
	*   @return the updated total of times around the circle 
	*/
	private static int calcTimesAround(SinglyLinkedList<Boolean> streak, int timesAround, 
									   boolean direction, int numPlayers) {
		if (streak.isEmpty() || streak.peek().equals(direction)) {
			streak.regularInsert(direction);
		} else {
			streak.clear();
		}
		return streak.size() == numPlayers - 1 ? ++timesAround : timesAround;
	}
}