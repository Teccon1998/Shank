import java.util.ArrayList;

public class ElseNode extends IfNode{

    private BooleanNode boolnode;
    private ArrayList<StatementNode> StatementNodes;

    public ElseNode(BooleanNode boolNode, ArrayList<StatementNode> StatementNodes) {
        super(boolNode, StatementNodes);
    }
    public BooleanNode getBooleanNode()
    {
        return this.boolnode;
    }

    public ArrayList<StatementNode> getStatementNode()
    {
        return this.StatementNodes;
    }
    
}
