import java.util.ArrayList;

public class squareRoot extends BuiltInFunctionNode {

    public squareRoot()
    {
        
    }
    public squareRoot(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, isVariadic);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) throws Exception {
        if(!(interpreterDataTypes.get(0) instanceof FloatDataType))
        {
            throw new Exception("Not a float data type");
        }
        FloatDataType fdt =  ((FloatDataType) interpreterDataTypes.get(0));
        Float floatValue = (float) Math.sqrt(fdt.getFloatValue());
        interpreterDataTypes.set(1, new FloatDataType(floatValue));
    }
    
}
