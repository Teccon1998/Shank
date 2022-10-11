import java.util.*;

public class IfNode extends Node{

    private BooleanNode boolNode;
    private ArrayList<StatementNode> StatementNodes;
    private IfNode ifelseNode;
    private ElseNode elseNode;

    //terminating else
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> StatementNodes, ElseNode elseNode)
    {
        this.boolNode = boolNode;
        this.StatementNodes = StatementNodes;
        this.elseNode = elseNode;
    }
    //for if else nodes
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> StatementNodes, IfNode ifelseNode)
    {
        this.boolNode = boolNode;
        this.StatementNodes = StatementNodes;
        this.ifelseNode = ifelseNode;
    }
    //solo if call
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> StatementNodes)
    {
        this.boolNode = boolNode;
        this.StatementNodes = StatementNodes;
    }

    public BooleanNode getBoolNode()
    {
        return this.boolNode;
    }

    public ArrayList<StatementNode> getStatementNodes()
    {
        return this.StatementNodes;
    }

    public IfNode getIfelseNode()
    {
        return this.ifelseNode;
    }
    public ElseNode getElseNode()
    {
        return this.elseNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IfNode(");
        sb.append(this.boolNode + ", ");
        sb.append("StatementNodes " + this.StatementNodes.toString());
        if(elseNode!= null)
        {
            sb.append("ElseNode" + this.elseNode + ")\n");
        }
        else
        {
            sb.append(")");
        }
        return sb.toString();
    }


    
}
