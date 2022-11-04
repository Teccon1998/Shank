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
        
        for(int i = 0; i< interpreterDataTypes.size(); i++)
        {
            System.out.println("Data Stored: "+interpreterDataTypes.get(i).toString());
        }
    }
    
}
