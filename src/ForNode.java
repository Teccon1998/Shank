import java.util.ArrayList;

public class ForNode extends Node {

    private VariableReferenceNode VariableReference;
    private Node StartNode;
    private Node EndNode;
    private ArrayList<StatementNode> Statements;
    
    public ForNode(VariableReferenceNode VariableReference,Node StartNode, Node EndNode, ArrayList<StatementNode> Statements)
    {
        this.VariableReference = VariableReference;
        this.StartNode = StartNode;
        this.EndNode = EndNode;
        this.Statements = Statements;
    }

    public VariableReferenceNode getVariableReference()
    {
        return this.VariableReference;
    }

    public Node getStartNode()
    {
        return this.StartNode;
    }

    public Node getEndNode()
    {
        return this.EndNode;
    }

    public ArrayList<StatementNode> getStatements()
    {
        return this.Statements;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ForNode(");
        sb.append("VARIABLEREFERENCE: "+ this.VariableReference + ", ");
        sb.append("StartNode: "+ this.StartNode + ", ");
        sb.append("EndNode: "+ this.EndNode + ", ");
        sb.append("StatementNodes: " + this.Statements.toString() + ")");
        return sb.toString();
    }
    
}
