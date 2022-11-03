import java.util.ArrayList;

public class integerToReal extends BuiltInFunctionNode {

    public integerToReal()
    {
        
    }
    public integerToReal(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, false);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) throws Exception {
        if(!(interpreterDataTypes.get(0) instanceof IntDataType))
        {
            throw new Exception("First value not a Int Data Type");
        }
        if(!(interpreterDataTypes.get(1) instanceof FloatDataType))
        {
            throw new Exception("Second value not an Float Data Type");
        }
        Float integerToRealValue = Float.parseFloat(interpreterDataTypes.get(0).toString());
        interpreterDataTypes.set(1, new FloatDataType(integerToRealValue));
    }
    
    
}
