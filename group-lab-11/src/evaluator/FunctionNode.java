package evaluator;

public class FunctionNode implements Node {
	private String functionName;
	private Node operand;

	public FunctionNode(String functionName, Node operand) {
		this.functionName = functionName;
		this.operand = operand;
	}

	@Override
	public double evaluate() {
		double result = -1;
		switch (this.functionName) {
		case "sqrt":
			result = Math.sqrt(operand.evaluate());
			break;
		default:
			System.err.println("Function: " + this.functionName + "is not supported currently");
			break;
		}
		return result;
	}

	@Override
	public String toPostfixString() {
		return "(" + this.operand.toPostfixString() + " " + this.functionName + ")";
	}

	public String toString() {
		return "(" + this.functionName + " " + this.operand.toString() + ")";
	}
}