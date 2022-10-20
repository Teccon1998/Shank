import java.util.ArrayList;

public abstract class CallableNode extends Node {
    
    private String FunctionName;
    private ArrayList<VariableNode> parameterVariableNodes;

    public CallableNode(String FunctionName, ArrayList<VariableNode> parameterVariableNodes) {
        this.FunctionName = FunctionName;
        this.parameterVariableNodes = parameterVariableNodes;
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


}
