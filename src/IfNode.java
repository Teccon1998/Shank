import java.util.*;

public class IfNode extends Node{

    private BooleanNode boolNode;
    private ArrayList<StatementNode> Statements;
    private Node elseNode;

    


    //solo if call
    public IfNode(BooleanNode boolNode, ArrayList<StatementNode> Statements)
    {
        this.boolNode = boolNode;
        this.Statements = Statements;
    }

    public BooleanNode getBoolNode() {
        return this.boolNode;
    }

    public void setBoolNode(BooleanNode boolNode) {
        this.boolNode = boolNode;
    }

    public ArrayList<StatementNode> getStatements() {
        return this.Statements;
    }

    public void setStatements(ArrayList<StatementNode> Statements) {
        this.Statements = Statements;
    }


    public Node getElseNode() {
        return this.elseNode;
    }

    public void setElseNode(Node elseNode) {
        this.elseNode = elseNode;
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
            sb.append("ELSENODE: " + this.elseNode + ")\n");
        }
        else
        {
            sb.append(")");
        }
        return sb.toString();
    }


    
}
