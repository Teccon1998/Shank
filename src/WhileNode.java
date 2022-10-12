import java.lang.Thread.State;
import java.util.ArrayList;

public class WhileNode extends Node{

    private BooleanNode BoolNode;
    private ArrayList<StatementNode> Statements;

    public WhileNode(BooleanNode BoolNode, ArrayList<StatementNode> Statements)
    {
        this.BoolNode = BoolNode;
        this.Statements = Statements;
    }

    public BooleanNode getBoolNode()
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
        sb.append("WhileNode(");
        sb.append(this.BoolNode+", ");
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
