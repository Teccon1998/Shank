import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    
    private ArrayList<FunctionDefinitionNode> FunctionDefinitionNodes;
    
    public Interpreter()
    {
        
    }

    public Interpreter(ArrayList<FunctionDefinitionNode> FunctionDefinitionNodes)
    {
        this.FunctionDefinitionNodes = FunctionDefinitionNodes;
    }

    public void setFunctionDefinitionNodes(ArrayList<FunctionDefinitionNode> FunctionDefinitionNodes)
    {
        this.FunctionDefinitionNodes = FunctionDefinitionNodes;
    }

    public ArrayList<FunctionDefinitionNode> getFunctionDefinitionNodes()
    {
        return FunctionDefinitionNodes;
    }
     
    public FloatNode Resolve(Node node)
    {
        if(node instanceof IntegerNode)
        {
            float floatCastHolder = ((IntegerNode) node).getNumber();
            return new FloatNode(floatCastHolder);
        }
        else if (node instanceof FloatNode)
        {
            return ((FloatNode)node);
        }
        return null;
    }

    public static void InterpretFunction(FunctionCallNode functionCallNode, ArrayList<InterpreterDataType> dataTypes)
    {
        HashMap<String, InterpreterDataType> VariableHashMap = new HashMap<>();
        


    }




}
