import java.util.*;

/** A simple class for splitting a string into tokens. This class is meant to work with strings 
*  that represent arithmetic expressions.
*/
public class StringSplitter {
	private MyQueue<Character> chars;
	private String token;
	
	public static final String SPECIAL_CHARS = "()+-*/^";
	
	/** Given a string, this construction initializes a queue of the characters 
	*  which compose that string, as wells as intializes a string containing the first token
	*/
	public StringSplitter(String line) {
		chars = new MyQueue<Character>();
		for (int i = 0; i < line.length(); i++) {
			chars.enqueue(line.charAt(i));
		}
		System.out.println(chars);
		findNextToken();
	}
	
	/** Searches the queue for the next token. A token in this case consists of an operand 
	*   and not an operator or whitespace- because we want to evaluate those 
	*/
	private void findNextToken() {
		while (!chars.isEmpty() && Character.isWhitespace(chars.peek())) {
			chars.dequeue();
		}
		if (chars.isEmpty()) {
			token = null;
		} else {
			token = "" + chars.dequeue();
			if (!SPECIAL_CHARS.contains(token)) {
				boolean done = false;
				while (!chars.isEmpty() && !done) {
					char ch = chars.peek();
					if (Character.isWhitespace(ch) || SPECIAL_CHARS.indexOf(ch) >= 0) {
						done = true;
					} else {
						token = token + chars.dequeue();
					}
				}
			}
		}
	}
	
	public boolean hasNext() {
		return token != null;
	}
	
	/** Returns the next token in our queue of characters and moves us to the following 
	*   token in the queue 
	*/
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		String result = token;
		findNextToken();
		return result;
	}

	public String peek() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return token;
	}
}