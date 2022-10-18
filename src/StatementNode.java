public class StatementNode extends Node {

    private Node Statement;

    public StatementNode()
    {

    }
    public StatementNode(Node Statement)
    {
        this.Statement = Statement;
    }

    public Node getStatement()
    {
        return this.Statement;
    }
    public void setStatement(Node Statement)
    {
        this.Statement = Statement;
    }


    @Override
    public String toString() {
        return "Statement(" + this.Statement + ")";
    }
    
    
}
