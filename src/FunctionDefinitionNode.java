import java.util.*;
public class FunctionDefinitionNode extends CallableNode {

    private String FunctionName;
    private ArrayList<VariableNode> ParamsList;
    private ArrayList<VariableNode> LocalVariablesList;
    private ArrayList<StatementNode> StatementList;

    public ArrayList<StatementNode> getStatementList() {
        return this.StatementList;
    }

    public void setStatementList(ArrayList<StatementNode> StatementList) {
        this.StatementList = StatementList;
    }

    public ArrayList<VariableNode> getLocalVariablesList() {
        return this.LocalVariablesList;
    }

    public void setLocalVariablesList(ArrayList<VariableNode> variableList) {
        this.LocalVariablesList = variableList;
    }
    
    public FunctionDefinitionNode(String FunctionName, ArrayList<StatementNode> StatementList, ArrayList<VariableNode> ParamsList, ArrayList<VariableNode> LocalVariablesList)
    {
        super(FunctionName, ParamsList,StatementList,LocalVariablesList);
        this.FunctionName = FunctionName;
        this.ParamsList = ParamsList;
        this.StatementList = StatementList;
        this.LocalVariablesList = LocalVariablesList;
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
