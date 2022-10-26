import java.util.ArrayList;

public class getRandom extends BuiltInFunctionNode {

    public getRandom()
    {
        
    }
    public getRandom(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, false);
    }

    @Override
    public ArrayList<InterpreterDataType> Execute(ArrayList<InterpreterDataType> interpreterDataTypes) throws Exception {
        if (!(interpreterDataTypes.get(0) instanceof IntDataType))
        {
            throw new Exception("Not an Int Data Type");
        }
        interpreterDataTypes.set(0, new IntDataType((int) Math.random() * (Integer.MAX_VALUE + 1)));
        return interpreterDataTypes;
    }
    
    
}
