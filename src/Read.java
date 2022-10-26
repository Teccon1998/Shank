import java.util.ArrayList;
import java.util.Scanner;

public class Read extends BuiltInFunctionNode {

    public Read()
    {
        setVariadic(true);
    }
    public Read(String FunctionName, ArrayList<VariableNode> parameterVariableNodes,boolean isVariadic) {
        super(FunctionName, parameterVariableNodes, true);
    }

    @Override
    public ArrayList<InterpreterDataType> Execute(ArrayList<InterpreterDataType> interpreterDataTypes) {
        
        Scanner sc = new Scanner(System.in);
        
        for(int i = 0; i< interpreterDataTypes.size(); i++)
        {
            interpreterDataTypes.get(i).fromString(sc.nextLine());
        }
        sc.close();
        return interpreterDataTypes;
    }
    
}
