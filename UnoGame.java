import java.util.*;

public class UnoGame {
	public static void main(String[] args) {
		PlayerCircle players = new PlayerCircle();
		Queue<Player> waiting = intialize();
		getPlayers(players, waiting);
		UnoDeck ud = new UnoDeck();
		createHands(players, ud);
		System.out.println(players);
		ud.discardCard(ud.drawCard()); //get the discard pile going 
		System.out.println("The top of the discard pile is " + ud.getLastDiscarded());
		play(players, ud);
	}
	
	public static Queue<Player> intialize() {
		Scanner console = new Scanner(System.in);
		System.out.println("Welcome to the game! How many players are you expecting?");
		int numPlayers = console.nextInt();
		return new Queue<Player>(numPlayers);
	}
	
	public static void getPlayers(PlayerCircle players, Queue<Player> waiting) {
		Scanner console = new Scanner(System.in);
		System.out.println("Thank you. Now, please enter the names of everyone playing. Type \"end\" to quit. ");
		String name = console.nextLine();
		while(!name.equals("end")) {
			if (players.getSize() < 7) {
				players.addToCircle(new Player(name));
			} else {
				waiting.enqueue(new Player(name));
			}
			name = console.nextLine();
		}
	}

	public static void createHands(PlayerCircle players, UnoDeck ud) {
		Iterator<Player> itr = players.iterator();
		while (itr.hasNext()) {
			Player p = itr.next();
			for (int i = 0; i < 7; i++) {
				p.addToHand(ud.drawCard());
			}
		}
	}
	
	public static void play(PlayerCircle players, UnoDeck ud) {
		MyIterator<Player> itr = players.endlessIterator();
		boolean direction = true; 
		while (itr.hasNext()) {
			UnoCard previous = ud.getLastDiscarded();
			Player player = getPlayer(itr, direction, previous);
			if (previous.isDrawTwo()) {
				add(player, ud, 2);
			} else if (previous.isWildDrawFour()) {
				add(player, ud, 4);
			}
			showCards(player);
			if (showValids(player, ud, previous)) {
				choose(player, ud);
			} else {
				System.out.println("Sorry! No valid cards!");
				//draw from deck 
				//go to next player
			}
		}
	}
	
	private static void add(Player player, UnoDeck ud, int amount) {
		for (int i = 0; i < amount; i++) {
			player.addToHand(ud.drawCard());
		}
	}
	
	private static Player getPlayer(MyIterator<Player> itr, boolean direction, UnoCard previous) {
		Player p;
		if (previous.isReverse()) {
				direction = !direction; //switch the direction  
		} 
		p = itr.next(direction);
		if (previous.isSkip()) {
			p = itr.next(direction); //go up one more place in the list 
		}
		return p;
	}
	
	private static void showCards(Player player) {
		System.out.println("Your hand is: ");
		System.out.println(player.getHand());
	}
	
	private static boolean showValids(Player player, UnoDeck ud, UnoCard previous) {
		Iterator<UnoCard> itr = player.getHand().iterator();
		boolean hasSomeValid = false;
		System.out.print("The cards you can play are: ");
		while (itr.hasNext()) {
			UnoCard card = itr.next();
			if (card.canBePlacedOn(previous)) {
				System.out.println(card);
				hasSomeValid = true;
			}
		}
		return hasSomeValid;
	}
}