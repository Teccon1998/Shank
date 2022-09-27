import java.util.*;
public class FunctionNode extends Node {

    private String FunctionName;
    private List<Node> ParamsList;
    private List<Node> StatementList;
    private List<Node> LocalsList;


    public void setParamsList(List<Node> ParamsList)
    {
        this.ParamsList = ParamsList;
    }

    public List<Node> getParamsList()
    {
        return this.ParamsList;
    }
    public String getFunctionName() {
        return this.FunctionName;
    }

    public void setFunctionName(String FunctionName) {
        this.FunctionName = FunctionName;
    }

    public List<Node> getStatementList() {
        return this.StatementList;
    }

    public void setStatementList(List<Node> StatementList) {
        this.StatementList = StatementList;
    }

    public List<Node> getLocalsList() {
        return this.LocalsList;
    }

    public void setLocalsList(List<Node> LocalsList) {
        this.LocalsList = LocalsList;
    }
    

    public FunctionNode(String FunctionName, List<Node> StatementList)
    {
        this.FunctionName = FunctionName;
        this.StatementList = StatementList;
    }
    public FunctionNode(String FunctionName)
    {
        this.FunctionName = FunctionName;
    }

    public FunctionNode()
    {

    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionNode(NAME:");
        sb.append(this.FunctionName);
        sb.append(",PARAMS:");
        if (this.ParamsList != null)
        {
            for (Node node : ParamsList) {
                sb.append(node);
            }

        }
        else
        {
            sb.append("NULL");
        }
        sb.append(",LOCALS:");
        if (this.LocalsList != null)
        {
            for (Node node : LocalsList) {
                sb.append(node);
            }

        }
        else
        {
            sb.append("NULL");
        }
        sb.append(")");
        return sb.toString();
    }
    
}