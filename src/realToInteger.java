import java.util.ArrayList;

public class realToInteger extends BuiltInFunctionNode {

    public realToInteger()
    {
        
    }
    public realToInteger(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, false);
    }

    @Override
    public ArrayList<InterpreterDataType> Execute(ArrayList<InterpreterDataType> interpreterDataTypes) throws Exception {
        if(!(interpreterDataTypes.get(0) instanceof FloatDataType))
        {
            throw new Exception("First value not a Float Data Type");
        }
        if(!(interpreterDataTypes.get(1) instanceof IntDataType))
        {
            throw new Exception("Second value not an Int Data Type");
        }
        Integer FloatToRealValue = Integer.parseInt(interpreterDataTypes.get(0).toString());
        interpreterDataTypes.set(1, new IntDataType(FloatToRealValue));
        return interpreterDataTypes;
    }
    
    
}
