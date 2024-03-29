import java.util.ArrayList;

public class RepeatNode extends Node{


    private BooleanNode BoolNode;
    private ArrayList<StatementNode> Statements;
    
    public RepeatNode(ArrayList<StatementNode> Statements)
    {
        this.Statements = Statements;
    }

    public void setBoolNode(BooleanNode BoolNode)
    {
        this.BoolNode = BoolNode;
    }

    public BooleanNode getBooleanNode()
    {
        return this.BoolNode;
    }

    public ArrayList<StatementNode> getStatements()
    {
        return this.Statements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RepeatNode(");
        sb.append(this.BoolNode + ", ");
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
        sb.append(")");
        return sb.toString();
    }
    
}
