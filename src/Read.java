import java.util.ArrayList;
import java.util.Scanner;

public class Read extends BuiltInFunctionNode {

    public Read(String FunctionName, ArrayList<VariableNode> parameterVariableNodes) {
        super(FunctionName, parameterVariableNodes, true);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        
        Scanner sc = new Scanner(System.in);
        
        for(int i = 0; i< interpreterDataTypes.size(); i++)
        {
            interpreterDataTypes.get(i).fromString(sc.nextLine());
        }
        sc.close();
    }
    
}
