import java.util.ArrayList;

public class squareRoot extends BuiltInFunctionNode {

    private float value;

    public squareRoot(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic, float value) {
        super(FunctionName, parameterVariableNodes, isVariadic);
        this.value = value;
    }

    @Override
    public InterpreterDataType Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        FloatDataType floatDataType = new FloatDataType((float) Math.sqrt(this.value));
        return floatDataType;
    }
    
}
