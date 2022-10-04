public class IfNode extends Node{

    private BooleanNode boolNode;
    private StatementNode StatementNode;
    private IfNode ifelseNode;
    private ElseNode elseNode;

    //terminating else
    public IfNode(BooleanNode boolNode, StatementNode StatementNode, ElseNode elseNode)
    {
        this.boolNode = boolNode;
        this.StatementNode = StatementNode;
        this.elseNode = elseNode;
    }
    //for if else nodes
    public IfNode(BooleanNode boolNode, StatementNode StatementNode, IfNode ifelseNode)
    {
        this.boolNode = boolNode;
        this.StatementNode = StatementNode;
        this.ifelseNode = ifelseNode;
    }
    //solo if call
    public IfNode(BooleanNode boolNode, StatementNode StatementNode)
    {
        this.boolNode = boolNode;
        this.StatementNode = StatementNode;
    }

    public BooleanNode getBoolNode()
    {
        return this.boolNode;
    }

    public StatementNode getStatementNode()
    {
        return this.StatementNode;
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
        sb.append("StatementNodes " + this.StatementNode.toString());
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
