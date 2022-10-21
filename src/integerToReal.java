import java.util.ArrayList;

public class integerToReal extends BuiltInFunctionNode {

    public integerToReal(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, isVariadic);
        //TODO Auto-generated constructor stub
    }

    @Override
    public InterpreterDataType Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        // FloatDataType floatDataType = new FloatDataType(Float.parseFloat(interpreterDataTypes.get(0)));
        return null;
    }
    
    
}
