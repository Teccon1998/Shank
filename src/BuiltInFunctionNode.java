import java.util.ArrayList;

public abstract class BuiltInFunctionNode extends CallableNode {

    private boolean isVariadic = false;

    public BuiltInFunctionNode(){}
    public BuiltInFunctionNode(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) 
    {
        super(FunctionName, parameterVariableNodes,null, null);
        this.isVariadic = isVariadic;

    }
    
    public boolean isVariadic() {
        return this.isVariadic;
    }

    public void setVariadic(boolean isVariadic) {
        this.isVariadic = isVariadic;
    }

    public abstract void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) throws Exception;

    @Override
    public String toString() {
        return "BuiltInFunctionNode(IsVariatic:"+this.isVariadic+")";
    }
    
}
