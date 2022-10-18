import java.util.ArrayList;

public class ElseNode extends Node{

    private ArrayList<StatementNode> StatementNodes;

    public ElseNode( ArrayList<StatementNode> StatementNodes) {
        this.StatementNodes = StatementNodes;
    }
    

    public ArrayList<StatementNode> getStatementNode()
    {
        return this.StatementNodes;
    }


    @Override
    public String toString() {
         StringBuilder sb = new StringBuilder();
        sb.append("ElseNode(");
        for(Node node : StatementNodes)
        {
            sb.append(node);
            if(StatementNodes.get(StatementNodes.size()-1).equals(node))
            {
                break;
            }
            else if(StatementNodes.size() > 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(")");
        return sb.toString();
    }
}
    

