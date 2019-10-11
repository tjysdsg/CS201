package evaluator;

public class TestDriver {
	public static void main(String[] args) {
		Node node = Parser.parseInfixExpression("( sqrt ( 3 ) - ( 2 * 4 ) )");
		System.out.println(node + " = " + node.toPostfixString() + " = " + node.evaluate());

		Node node2 = Parser.parsePostfixExpression("3 sqrt 2 4 * -");
		System.out.println(node2 + " = " + node2.toPostfixString() + " = " + node2.evaluate());

		Node node3 = new BinaryOperatorNode("+", node, node2);
		System.out.println(node3 + " = " + node3.toPostfixString() + " = " + node3.evaluate());

		Node node4 = Parser.parseInfixExpression("( sqrt ( ( -3 * -6 ) ) - ( 2 * 4 ) )");
		System.out.println(node4 + " = " + node4.toPostfixString() + " = " + node4.evaluate());
	}
}