import java.util.ArrayList;

public class Read extends BuiltInFunctionNode {

    public Read(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, true);
        //TODO Auto-generated constructor stub
    }

    @Override
    public InterpreterDataType Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        // TODO Auto-generated method stub
        return null;
        
    }
    
}
