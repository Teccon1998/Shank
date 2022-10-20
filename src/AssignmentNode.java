public class AssignmentNode extends StatementNode {
    
    private VariableReferenceNode variableReferenceNode;
    private Node ASTNODE;

    

    public AssignmentNode() {

    }

    public AssignmentNode(VariableReferenceNode variableReferenceNode, Node ASTNODE)
    {
        this.variableReferenceNode = variableReferenceNode;
        this.ASTNODE = ASTNODE;
    }

    public VariableReferenceNode getVariableReferenceNode() {
        return this.variableReferenceNode;
    }

    public void setVariableReferenceNode(VariableReferenceNode variableReferenceNode) {
        this.variableReferenceNode = variableReferenceNode;
    }

    public Node getASTNODE() {
        return this.ASTNODE;
    }

    public void setASTNODE(Node ASTNODE) {
        this.ASTNODE = ASTNODE;
    }


    
    @Override
    public String toString()
    {
        return "AssignmentNode(VARREF:" + this.variableReferenceNode + "= ASTNODE: " + this.ASTNODE + ")";
    }
}
