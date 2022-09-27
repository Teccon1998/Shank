public class VariableNode extends Node {

    private String VariableName;
    private boolean isConstant;
    private Type Type;
    private Node Node;

    public enum Type
    {
        INTEGER, REAL
    };

    public VariableNode(Type Type,Boolean CONST, String VariableName)
    {
        this.Type = Type;
        this.isConstant = CONST;
        this.VariableName = VariableName;
    }

    public VariableNode(Type Type,Boolean CONST, String VariableName, Node Node)
    {
        this.Type = Type;
        this.isConstant = CONST;
        this.VariableName = VariableName;
        this.Node = Node;
    }
    public boolean isConstant()
    {
        return this.isConstant;
    }
    public String getVariableName() {
        return this.VariableName;
    }

    public Type getType() {
        return this.Type;
    }
    
    public Node getNode()
    {
        return Node;
    }
    @Override
    public String toString() {
        return "VariableNode(Constant:" + this.isConstant + ",Type:" + this.Type + ",Node: " + this.Node + ")";
    }
    
}
