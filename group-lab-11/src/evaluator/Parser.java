package evaluator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Parser {

	public static Node parseInfixExpression(String expression) {
		return parseInfixExpression(Arrays.asList(expression.split(" ")));
	}

	public static Node parseAST(Stack<String> ops, Stack<String> vals) {
		if (ops.isEmpty()) {
			return null;
		}
		String op = ops.pop();
		if (op.equals("sqrt")) {
			Node operand = parseAST(ops, vals);
			return new FunctionNode(op, operand);
		} else if (op.equals("+") && op.equals("-") && op.equals("*")) {
			Node operand1 = parseAST(ops, vals);
			Node operand2 = parseAST(ops, vals);
			return new BinaryOperatorNode(op, operand1, operand2);
		} else {
			return new LiteralNode(Double.parseDouble(vals.pop()));
		}
	}

	public static Node parseInfixExpression(List<String> tokens) {
		Stack<String> ops = new Stack<String>();
		Stack<String> vals = new Stack<String>();
		for (String token : tokens) {
			if (token.equals("(")) {
			} else if (token.equals(")")) {
			} else if (token.equals("+")) {
				ops.push(token);
			} else if (token.equals("-")) {
				ops.push(token);
			} else if (token.equals("*")) {
				ops.push(token);
			} else if (token.equals("sqrt")) {
				ops.push(token);
			} else {
				vals.push(token);
			}
		}

		return parseAST(ops, vals);
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
				stack.push(new BinaryOperatorNode(token, stack.pop(), stack.pop()));
			} else {
				stack.push(new LiteralNode(Double.parseDouble(token)));
			}
		}
		return stack.pop();
	}
}