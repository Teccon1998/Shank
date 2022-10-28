public class MathOpNode extends Node {
    public enum Operator {
        ADD, SUBTRACT, DIVIDE, TIMES, MODULO;
    }

    private Node Operand1;
    private Node Operand2;
    private Operator operator;

    public MathOpNode(Node Operand1,Node Operand2, Operator operator)
    {
        this.Operand1 = Operand1;
        this.Operand2 = Operand2;
        this.operator = operator;
    }

    public Node getNodeOne()
    {
        return this.Operand1;
    }

    public Node getNodeTwo()
    {
        return this.Operand2;
    }

    public Operator getOperator()
    {
        return this.operator;
    }

    @Override
    public String toString() {
        return "MathOpNode(" + this.Operand1.toString() + ", " + this.Operand2.toString() + ", " + this.operator.toString() + ")";
    }
    
}