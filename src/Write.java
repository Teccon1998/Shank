import java.util.ArrayList;

public class Write extends BuiltInFunctionNode {

    private String str;

    public Write(String FunctionName, ArrayList<VariableNode> parameterVariableNodes, boolean isVariadic, String str) throws Exception {
        super(FunctionName, parameterVariableNodes, true);
        this.str = str;
        
    }

    @Override
    public InterpreterDataType Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        System.out.println(this.str);
        return null;
    }
    
}
