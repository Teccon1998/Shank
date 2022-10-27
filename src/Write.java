import java.util.ArrayList;

public class Write extends BuiltInFunctionNode {


    public Write()
    {
        setVariadic(false);
    }
    public Write(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic){
        super(FunctionName, parameterVariableNodes, true);
        
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        
        System.out.println(interpreterDataTypes.get(0).toString());
    }
    
}
