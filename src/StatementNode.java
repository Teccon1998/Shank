import java.util.*;

public class StatementNode extends Node {

    private List<Node> Statements;

    public StatementNode()
    {

    }
    public StatementNode(List<Node> Statements)
    {
        this.Statements = Statements;
    }

    public List<Node> getStatements()
    {
        return this.Statements;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : Statements) {
            sb.append(node.toString());
        }
        return sb.toString();
    }
    
    
}
