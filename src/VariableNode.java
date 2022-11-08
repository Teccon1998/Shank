public class VariableNode extends Node {

    private String VariableName;
    private boolean isConstant;
    private VariableNode.Type Type;
    private Node Node;

    public enum Type
    {
        INTEGER, REAL, BOOLEAN, STRING, CHAR
    };

    public VariableNode(VariableNode.Type Type,Boolean CONST, String VariableName, Node Node)
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

    public VariableNode.Type getType() {
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
