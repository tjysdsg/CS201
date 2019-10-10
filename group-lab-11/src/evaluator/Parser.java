package evaluator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Parser {

	public static Node parseInfixExpression(String expression) {
		return parseInfixExpression(Arrays.asList(expression.split(" ")));
	}

	public static Node parseInfixExpression(List<String> tokens) {
		Stack<String> ops = new Stack<String>();
		Stack<Node> values = new Stack<Node>();
		for (String token : tokens) {
			if (token.equals("("))
				;
			else if (token.equals("+"))
				ops.push(token);
			else if (token.equals("-"))
				ops.push(token);
			else if (token.equals("*"))
				ops.push(token);
			else if (token.equals("sqrt"))
				ops.push(token);
			else if (token.equals(")")) { // Pop, evaluate, and push result if token is ")".
				String op = ops.pop();
				Node v = values.pop();
				if (op.equals("sqrt"))
					values.push(new FunctionNode(op, v));
				else
					values.push(new BinaryOperatorNode(op, values.pop(), v));
			} else
				values.push(new LiteralNode(Double.parseDouble(token)));
		}
		return values.peek();
	}

	public static Node parsePostfixExpression(String expression) {
		return parsePostfixExpression(Arrays.asList(expression.split(" ")));
	}

	public static Node parsePostfixExpression(List<String> tokens) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		for (String token : tokens) {
			if (token.equals("sqrt")) {
				stack.push(new FunctionNode(token, stack.pop()));
			} else if (token.equals("+") || token.equals("-") || token.equals("*")) {
				Node v = stack.pop();
				stack.push(new BinaryOperatorNode(token, stack.pop(), v));
			} else {
				stack.push(new LiteralNode(Double.parseDouble(token)));
			}
		}
		return stack.pop();
	}
}