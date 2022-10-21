import java.util.ArrayList;

public class integerToReal extends BuiltInFunctionNode {

    public integerToReal(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, false);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        Float integerToRealValue = Float.parseFloat(interpreterDataTypes.get(0).toString());
        interpreterDataTypes.set(1, new FloatDataType(integerToRealValue));
    }
    
    
}
