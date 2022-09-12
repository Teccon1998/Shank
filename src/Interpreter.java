public class Interpreter {
    
    public Interpreter()
    {

    }

    public FloatNode Resolve(Node node)
    {
        if(node instanceof IntegerNode)
        {
            float floatCastHolder = ((IntegerNode) node).getNumber();
            return new FloatNode(floatCastHolder);
        }
        else if (node instanceof FloatNode)
        {
            return ((FloatNode)node);
        }
        return null;
    }
}
