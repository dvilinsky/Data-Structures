import java.util.Arrays;

public class PostfixEvaluator {
	public static final String OPERATORS = "*+-/";
	
	public static void main(String[] args) {
		String expression = "4572+-*"; //pray for 45
		System.out.println(evaluate(expression));
	}
	
	/** This method takes a string that is a postfix expression and returns a string that is 
	*   the result of evaluating that expression. It doesn't do anything fancy like check before
	*   we enter the algorithm if the string is in postfix form.
	*/
	public static String evaluate(String expression) {
		String[] ex = toArray(expression);
		MyStack<String> s = new MyStack<String>();
		for (int i = 0; i < ex.length; i++) {
			if (!OPERATORS.contains(ex[i])) { //if we've hit a number 
				s.push(ex[i]);
			} else if (OPERATORS.contains(ex[i])) { 
				if (s.isEmpty()) {
					throw new IllegalStateException("Error: stack is empty");
				}
				String b = s.pop();
				if (s.isEmpty()) {
					throw new IllegalStateException("Error: stack is empty");
				}
				String a = s.pop();
				s.push(evaluate(ex[i], a, b));
			}
		}
		return s.size() == 1 ? s.pop() : "Error: You're missing an operator somewhere";
	}
	
	/** Here we want to do a-b, a+b, a*b, a/b instead of with the operands reversed
	*   Returns the result of applying a to b. 
	*/
	public static String evaluate(String operand, String a, String b) {
		double result = 0;
		if (operand.equals("-")) {
			result = Double.parseDouble(a) - Double.parseDouble(b);
		} else if (operand.equals("+")) {
			result = Double.parseDouble(a) + Double.parseDouble(b);
		} else if (operand.equals("/")) {
			result = Double.parseDouble(a) / Double.parseDouble(b);
		} else if (operand.equals("*")) {
			result = Double.parseDouble(a) * Double.parseDouble(b);
		}
		return Double.toString(result);
	}
	
	/** Helper method that takes a string and returns an array of strings of each character in  
	*   the string. This method exists so I don't have to work with arrays of characters
	*/
	public static String[] toArray(String s) {
		String[] result = new String[s.length()];
		for (int i = 0; i < result.length; i++) {
			result[i] = Character.toString(s.charAt(i));
		}
		return result;
	}
}