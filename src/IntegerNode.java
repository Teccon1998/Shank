public class IntegerNode extends Node {

    private int numContainer;

    IntegerNode(String number) {
        super(Token.Type.NUMBER, number);
        this.numContainer = Integer.parseInt(number);
    }

    public int getNum() {
        return this.numContainer;
    }

    @Override
    public String toString() {
        return "IntegerNode("+this.numContainer+")";
    }

    @Override
    public Token.Type getType() {
        return Token.Type.NUMBER;
    }

    @Override
    public String getValue() {
        return String.valueOf(numContainer);
    }

    
    
}
