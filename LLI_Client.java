import java.util.*;

public class LLI_Client {
	public static void main(String[] args) {
		MyLinkedList<Integer> ll = new MyLinkedList<Integer>();
		ll.add(0, 50); ll.add(0, 25);
		ll.add(8); ll.add(100); ll.add(400); ll.add(101); ll.add(7); ll.add(0);
		System.out.println(ll);
		ll.removeAt(1);
		System.out.println(ll);
		Collections.sort(ll);
	}
}