import java.util.ArrayList;

public class BuiltInFunctionNode extends CallableNode {

    private boolean isVariadic;

    
    public BuiltInFunctionNode(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) 
    {
        super(FunctionName, parameterVariableNodes);
        this.isVariadic = isVariadic;

    }
    
    public boolean isVariadic() {
        return this.isVariadic;
    }

    public void setVariadic(boolean isVariadic) {
        this.isVariadic = isVariadic;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
