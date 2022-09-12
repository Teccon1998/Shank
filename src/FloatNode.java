public class FloatNode extends Node {

    private float numContainer;

    public FloatNode(float numContainer)
    {
        this.numContainer = numContainer;
    }

    public float getNumber()
    {
        return this.numContainer;
    }

    @Override
    public String toString()
    {
        return "FloatNode(" + numContainer + ")";
    }

    
    
}
