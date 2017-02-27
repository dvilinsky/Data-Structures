public class PlayerCircle {
	private DoublyLinkedList<Player> circle;
	
	public PlayerCircle() {
		this.circle = new DoublyLinkedList<Player>();
	}
	
	public void addToCircle(Player p) {
		this.circle.addSorted(p);
	}
	
	public Player getFirstPlayer() {
		return this.circle.get(0);
	}
	
	public int getSize() {
		return this.circle.size();
	}
	
	public String toString() {
		return circle.toString();
	}
}