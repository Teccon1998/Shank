import java.util.ArrayList;

public class squareRoot extends BuiltInFunctionNode {

    public squareRoot(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, isVariadic);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        FloatDataType fdt =  ((FloatDataType) interpreterDataTypes.get(0));
        Float floatValue = (float) Math.sqrt(fdt.getFloatValue());
        interpreterDataTypes.set(1, new FloatDataType(floatValue));
    }
    
}
