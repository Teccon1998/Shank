import java.util.*;
public class FunctionDefintionNode extends Node {

    private String FunctionName;
    private List<VariableNode> ParamsList;
    private List<StatementNode> StatementList;
    private List<VariableNode> LocalsList;


    public void setParamsList(List<VariableNode> parameterList)
    {
        this.ParamsList = parameterList;
    }

    public List<VariableNode> getParamsList()
    {
        return this.ParamsList;
    }
    public String getFunctionName() {
        return this.FunctionName;
    }

    public void setFunctionName(String FunctionName) {
        this.FunctionName = FunctionName;
    }

    public List<StatementNode> getStatementList() {
        return this.StatementList;
    }

    public void setStatementList(List<StatementNode> StatementList) {
        this.StatementList = StatementList;
    }

    public List<VariableNode> getLocalsList() {
        return this.LocalsList;
    }

    public void setLocalsList(List<VariableNode> variableList) {
        this.LocalsList = variableList;
    }
    
    public FunctionDefintionNode(String FunctionName, List<StatementNode> StatementList,List<VariableNode> ParamsList, List<VariableNode> LocalsList)
    {
        this.FunctionName = FunctionName;
        this.StatementList = StatementList;
        this.ParamsList = ParamsList;
        this.LocalsList = LocalsList;
    }

    public FunctionDefintionNode()
    {

    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionNode(\nNAME:");
        sb.append(this.FunctionName);
        sb.append(" \nPARAMS:");
        if (this.ParamsList != null)
        {
            for (Node node : ParamsList) {
                sb.append("\n");
                sb.append(node);
            }

        }
        else
        {
            sb.append("NULL");
        }
        sb.append(" \nLOCALS:");
        if (this.LocalsList != null)
        {
            for (Node node : LocalsList) {
                sb.append("\n");
                sb.append(node);
            }

        }
        else
        {
            sb.append("NULL");
        }
        sb.append(" \nSTATEMENTS:");
        if (this.StatementList != null)
        {
            
            for (Node node : StatementList) {
                sb.append("\n");
                sb.append(node);
            }
        }
        else
        {
            sb.append("NULL");
        }
        sb.append("\n)");
        return sb.toString();
    }
    
}
