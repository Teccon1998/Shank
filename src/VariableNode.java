public class VariableNode extends Node {

    private String VariableName;
    private boolean isConstant;
    private Token.Type Type;
    private Node Node;

    public enum Type
    {
        INTEGER, REAL
    };

    public VariableNode(Token.Type Type,Boolean CONST, String VariableName, Node Node)
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

    public Token.Type getType() {
        return this.Type;
    }
    
    public Node getNode()
    {
        return Node;
    }
    @Override
    public String toString() {
        return "VariableNode(Name:"+ this.VariableName + ", Constant:" + this.isConstant + ",Type:" + this.Type + ",Node: " + this.Node + ")";
    }
    
}
