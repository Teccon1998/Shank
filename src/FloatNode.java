public class FloatNode extends Node {

    private double numContainer;

    FloatNode(String number) {
        super(Token.Type.DECIMAL, number);
        this.numContainer = Double.parseDouble(number);
    }

    public double getNum() {
        return this.numContainer;
    }

    @Override
    public String toString() {
        return "FloatNode("+this.numContainer+")";
    }

    @Override
    public Token.Type getType() {
        return Token.Type.DECIMAL;
    }

    @Override
    public String getValue() {
        return String.valueOf(numContainer);
    }

    
    
}
