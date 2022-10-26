public class ParameterNode extends Node {

    public VariableReferenceNode varRefNode;
    public Node ValueNode;


    public ParameterNode(Node ValueNode)
    {
        this.ValueNode = ValueNode;
    }

    public ParameterNode(VariableReferenceNode varRefNode)
    {
        this.varRefNode = varRefNode;
    }

    public ParameterNode(VariableReferenceNode varRefNode,Node ValueNode)
    {
        this.varRefNode = varRefNode;
        this.ValueNode = ValueNode;
    }

    public VariableReferenceNode getVarRefNode() {
        return this.varRefNode;
    }

    public void setVarRefNode(VariableReferenceNode varRefNode) {
        this.varRefNode = varRefNode;
    }

    public Node getValueNode() {
        return this.ValueNode;
    }

    public void setValueNode(Node ValueNode) {
        this.ValueNode = ValueNode;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ParameterNode(");
        if(this.ValueNode == null)
        {
            sb.append(varRefNode);
        }
        else if(this.varRefNode == null)
        {
            sb.append(ValueNode);
        }
        sb.append(")");
        return sb.toString();
    }
    
}