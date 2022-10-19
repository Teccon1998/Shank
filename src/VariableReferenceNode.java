public class VariableReferenceNode extends Node{

    private String VariableName;
    private Node node;

    public VariableReferenceNode(){}
    public VariableReferenceNode(String VariableName)
    {
        this.VariableName = VariableName;
    }
    public VariableReferenceNode(String VariableName, Node node)
    {
        this.VariableName = VariableName;
        this.node = node;
    }

    public String getVariableName()
    {
        return this.VariableName;
    }

    public Node getNode()
    {
        return this.node;
    }

    public void setNode(VariableNode node)
    {
        this.node = node;
    }
    public void setVariableName(String VariableName)
    {
        this.VariableName = VariableName;
    }
    @Override
    public String toString() {
        if(this.node == null)
        {
            return "VariableReferenceNode("+ this.VariableName + ")";
        }
        else
        {
            return "VariableReferenceNode("+ this.VariableName + ", " + this.node + ")";
        }
        
    }
    
}
