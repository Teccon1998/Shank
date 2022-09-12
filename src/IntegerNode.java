public class IntegerNode extends Node {

    private int numContainer;

    public IntegerNode(int numContainer)
    {
        this.numContainer = numContainer;
    }

    public int getNumber()
    {
        return this.numContainer;
    }

    @Override
    public String toString() {
        return "IntegerNode(" + this.numContainer + ")";
    }
    

    
    
}
