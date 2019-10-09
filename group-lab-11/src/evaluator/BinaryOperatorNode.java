
package evaluator;

public class BinaryOperatorNode implements Node {

    private String operator;
    private Node operand1;
    private Node operand2;

    public BinaryOperatorNode(String operator, Node operand1, Node operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public double evaluate() {
        double result = 0;
        switch (this.operator) {
        case "+":
            result = operand1.evaluate() + operand2.evaluate();
            break;
        case "-":
            result = operand1.evaluate() - operand2.evaluate();
            break;
        case "*":
            result = operand1.evaluate() * operand2.evaluate();
            break;
        default:
            System.err.println("Operator: " + this.operator + "is not supported currently");
            break;
        }
        return result;
    }

    @Override
    public String toPostfixString() {
        return "(" + this.operand1.toPostfixString() + " " + this.operand2.toPostfixString() + " " + this.operator
                + ")";
    }

    public String toString() {
        return "(" + this.operand1.toString() + " " + this.operator + " " + this.operand2.toString() + ")";
    }
}