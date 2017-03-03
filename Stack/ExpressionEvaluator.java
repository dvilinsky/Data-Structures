public class ExpressionEvaluator {
	public static void main(String[] args) {
		MyStack<String> symbols = new MyStack<String>();
		MyStack<Double> numbers = new MyStack<Double>();
		String line = "((4/2)+(7-4))";
		StringSplitter data = new StringSplitter(line);
		boolean error = false;
		while (!error && data.hasNext()) {
			String next = data.next();
			if (next.equals(")")) {
				if (symbols.size() < 2 || symbols.peek().equals("(")) {
					error = true;
				} else {
					String operator = symbols.pop();
					if (!symbols.peek().equals("(")) {
						error = true;
					} else {
						symbols.pop(); //get rid of useless left parenthesis
						double d2 = numbers.pop();
						double d1 = numbers.pop();
						double value = eval(d1, d2, operator);
						numbers.push(value);
					}
				}
			} else if ("(+-*/^".contains(next)) {
				symbols.push(next);
			} else {
				numbers.push(Double.parseDouble(next));
			}
		}
		if (error || numbers.size() != 1 || !symbols.isEmpty()) {
			System.out.println("Get fucked");
		} else {
			System.out.println(numbers.pop());
		}
	}
	
	public static double eval(double d1, double d2, String operator) {
		double result = 0;
		if (operator.equals("+")) {
			result = d1+d2;
		} else if (operator.equals("-")) {
			result = d1-d2;
		} else if (operator.equals("*")) {
			result = d1*d2;
		} else if (operator.equals("/")) {
			result = d1/d2;
		} else if (operator.equals("^")) {
			result = Math.pow(d1, d2);
		}
		return result;
	}
}