import java.util.*;
public class FunctionNode extends Node {

    private String FunctionName;
    private List<Node> StatementList;
    
    public FunctionNode(String FunctionName)
    {
        this.FunctionName = FunctionName;
    }

    public FunctionNode(String FunctionName, List<Node> StatementList)
    {
        this.FunctionName = FunctionName;
        this.StatementList = StatementList;
    }

    public String getFunctionName()
    {
        return this.FunctionName;
    }

    public List<Node> getStatementNodes()
    {
        return this.StatementList;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
