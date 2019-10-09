package evaluator;

public class LiteralNode implements Node {
	private double value;

	public LiteralNode(double value) {
		this.value = value;
	}

	@Override
	public double evaluate() {
		return this.value;
	}

	@Override
	public String toPostfixString() {
		return "" + this.value;
	}

	public String toString() {
		return "" + this.value;
	}
}