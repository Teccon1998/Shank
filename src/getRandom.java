import java.util.ArrayList;

public class getRandom extends BuiltInFunctionNode {

    public getRandom(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, isVariadic);
        //TODO Auto-generated constructor stub
    }

    @Override
    public InterpreterDataType Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        IntDataType intDataType = new IntDataType((int) Math.random() * (Integer.MAX_VALUE + 1));
        return intDataType;
    }
    
    
}
