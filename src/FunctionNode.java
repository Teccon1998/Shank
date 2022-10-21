import java.util.ArrayList;

public class FunctionNode extends StatementNode {

    private String FunctionName;
    private ArrayList<ParameterNode> ParameterNodes;

    public FunctionNode(String FunctionName, ArrayList<ParameterNode> ParameterNodes) {
        this.FunctionName = FunctionName;
        this.ParameterNodes = ParameterNodes;
    }

    public String getFunctionName() {
        return this.FunctionName;
    }

    public void setFunctionName(String FunctionName) {
        this.FunctionName = FunctionName;
    }

    public ArrayList<ParameterNode> getParameterNodes() {
        return this.ParameterNodes;
    }

    public void setParameterNodes(ArrayList<ParameterNode> ParameterNodes) {
        this.ParameterNodes = ParameterNodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("FunctionCallNode(" + this.FunctionName);
        if (ParameterNodes != null)
        {
            int i = 0;
            for(ParameterNode parNode : ParameterNodes)
            {
                if (i < ParameterNodes.size())
                {
                    sb.append(", ");
                }
                sb.append(parNode);
                i++;
            }
        }
        sb.append(")");

        return sb.toString();
    }
    
}
