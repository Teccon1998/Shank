import java.util.*;

public class IfNode extends Node{

    private BooleanNode boolNode;
    private ArrayList<StatementNode> Statements;
    private IfNode ifelseNode;
    private ElseNode elseNode;

    //terminating else
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> Statements, ElseNode elseNode)
    {
        this.boolNode = boolNode;
        this.Statements = Statements;
        this.elseNode = elseNode;
    }
    //for if else nodes
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> Statements, IfNode ifelseNode)
    {
        this.boolNode = boolNode;
        this.Statements = Statements;
        this.ifelseNode = ifelseNode;
    }
    //solo if call
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> Statements)
    {
        this.boolNode = boolNode;
        this.Statements = Statements;
    }

    public BooleanNode getBoolNode()
    {
        return this.boolNode;
    }

    public ArrayList<StatementNode> getStatements()
    {
        return this.Statements;
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
        for(Node node : Statements)
        {
            sb.append(node);
            if(Statements.get(Statements.size()-1).equals(node))
            {
                break;
            }
            else if(Statements.size() > 1)
            {
                sb.append(", ");
            }
        }
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
