import java.util.ArrayList;

public class FunctionCallNode extends StatementNode {
    

    private String FunctionName;
    private ArrayList<ParameterNode> ParameterNodes;

    public FunctionCallNode(String FunctionName, ArrayList<ParameterNode> PararameterNodes)
    {
        this.FunctionName = FunctionName;
        this.ParameterNodes = PararameterNodes;
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
