import java.util.ArrayList;

public class RepeatNode extends Node{


    private BooleanNode BoolNode;
    private ArrayList<StatementNode> Statements;
    
    public RepeatNode(BooleanNode BoolNode, ArrayList<StatementNode> Statements)
    {
        this.BoolNode = BoolNode;
        this.Statements = Statements;
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
        sb.append(this.BoolNode);
        for(Node node : Statements)
        {
            sb.append("STATEMENT:");
            sb.append(node+"\n");
        }
        sb.append(")\n");
        return sb.toString();
    }
    
}
