public class MathOpNode extends Node {
    public enum Operator {
        ADD, SUBTRACT, DIVIDE, TIMES;
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

    public FloatNode Operate(MathOpNode math)
    {
        Interpreter interpreter = new Interpreter();
        float Num1 = interpreter.Resolve(math.getNodeOne()).getNumber();
        float Num2 = interpreter.Resolve(math.getNodeTwo()).getNumber();
        float Result;
        switch (math.getOperator()) {
            case ADD:
                Result = Num1 + Num2;
                return new FloatNode(Result);
            case SUBTRACT:
                Result = Num1 - Num2;
                return new FloatNode(Result);
            case DIVIDE:
                Result = Num1 / Num2;
                return new FloatNode(Result);
            case TIMES:
                Result = Num1 * Num2;
                return new FloatNode(Result);
        }
        return null;
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