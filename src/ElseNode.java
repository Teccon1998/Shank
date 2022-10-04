public class ElseNode extends IfNode{

    private BooleanNode boolnode;
    private StatementNode StatementNode;

    public ElseNode(BooleanNode boolNode, StatementNode StatementNode) {
        super(boolNode, StatementNode);
    }
    public BooleanNode getBooleanNode()
    {
        return this.boolnode;
    }

    public StatementNode getStatementNode()
    {
        return this.StatementNode;
    }
    
}
