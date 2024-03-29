import java.util.ArrayList;

public abstract class CallableNode extends Node {
    
    private String FunctionName;
    private ArrayList<VariableNode> parameterVariableNodes;
    private ArrayList<StatementNode> StatementList;
    private ArrayList<VariableNode> LocalVariablesList;

   
    public CallableNode() {
    }

    public CallableNode(String FunctionName, ArrayList<VariableNode> parameterVariableNodes,ArrayList<StatementNode> StatementList, ArrayList<VariableNode> LocalVariablesList) {
        this.FunctionName = FunctionName;
        this.parameterVariableNodes = parameterVariableNodes;
        this.StatementList = StatementList;
        this.LocalVariablesList = LocalVariablesList;
    }
    public String getFunctionName() {
        return this.FunctionName;
    }

    public void setFunctionName(String FunctionName) {
        this.FunctionName = FunctionName;
    }

    public ArrayList<VariableNode> getParameterVariableNodes() {
        return this.parameterVariableNodes;
    }

    public void setParameterVariableNodes(ArrayList<VariableNode> parameterVariableNodes) {
        this.parameterVariableNodes = parameterVariableNodes;
    }
     public ArrayList<StatementNode> getStatementList() {
        return this.StatementList;
    }

    public void setStatementList(ArrayList<StatementNode> StatementList) {
        this.StatementList = StatementList;
    }

    public ArrayList<VariableNode> getLocalVariablesList() {
        return this.LocalVariablesList;
    }

    public void setLocalVariablesList(ArrayList<VariableNode> LocalVariablesList) {
        this.LocalVariablesList = LocalVariablesList;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionNode(\nNAME:");
        sb.append(this.FunctionName);
        sb.append(" \nPARAMS:");
        if (this.parameterVariableNodes != null)
        {
            for (Node node : parameterVariableNodes) {
                sb.append("\n");
                sb.append(node);
            }

        }
        else
        {
            sb.append("NULL");
        }
        sb.append(" \nLOCALS:");
        if (this.LocalVariablesList != null)
        {
            for (Node node : LocalVariablesList) {
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
