import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    
    private static HashMap<String, CallableNode> functionsHashmap;

    public Interpreter(HashMap<String, CallableNode> functionsHashmap)
    {
        Interpreter.functionsHashmap = functionsHashmap;
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
    
        //Loops through all the functionDefintionNodes
        for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
            //loops through each DefinitionNode to get its statements
            for (int i = 0; i < functionDefinitionNode.getStatementList().size(); i++) 
            {
                //checks that each of the statements are FunctionCallNodes
                if(functionDefinitionNode.getStatementList().get(i).getStatement() instanceof FunctionCallNode)
                {
                    //Creates a CallableNode to store and work with that node
                    CallableNode functionCallNode;
                    if((functionCallNode = functionHashMap.get(functionDefinitionNode.getFunctionName()))!=null)
                    {
                        //Checks the parametersize of that functionCallNode and checks to see if its equal to the functiondefinition parameter size
                        int functionCallNodeParameterSize = functionCallNode.getParameterVariableNodes().size();
                        if(functionDefinitionNode.getParameterVariableNodes().size() == functionCallNodeParameterSize)
                        {
                            //if its a built in functionNode cast it to a builtinfunctionnode and check if its variadic
                            if(functionCallNode instanceof BuiltInFunctionNode)
                            {
                                BuiltInFunctionNode builtInFunctionCallNode = (BuiltInFunctionNode) functionCallNode;
                                if (builtInFunctionCallNode.isVariadic()) {
                                    //if its built in and variadic then get the data types from its variables passed and call the function
                                    ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                                    dataTypes = getDataType(functionCallNode);
                                    //TODO Running Builtin functions?
                                }

                            }
                            //if its a regular function call then build the data types and pass it to interpreter
                            else if(functionCallNode instanceof FunctionDefinitionNode)
                            {
                                ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                                dataTypes = getDataType(functionCallNode);
                                //TODO: Interpret? How do I get the first function? What is the variable hashmap?
                                System.out.println(dataTypes);
                                
                                Interpreter interpreter = new Interpreter();
                                
                                Interpreter.InterpretFunction(functionDefinitionNode, dataTypes);
                            }  
                            else
                            {
                                throw new Exception("Not a recognized function");
                                
                            }
                            
                        }
                    }
                }
            }
        }
        
        HashMap<String, InterpreterDataType> VariableHashMap = new HashMap<>();
        
        for(int i = 0; i<functionDefinitionNode.getParameterVariableNodes().size(); i++)
        {
            VariableHashMap.put(functionDefinitionNode.getParameterVariableNodes().get(i).getVariableName(), dataTypes.get(i));
            
        }
        for(int i = 0; i< functionDefinitionNode.getLocalVariablesList().size(); i++)
        {   
            if(functionDefinitionNode.getLocalVariablesList().get(i).getType().equals(VariableNode.Type.INTEGER))
            {
                int value = ((IntegerNode) functionDefinitionNode.getLocalVariablesList().get(i).getNode()).getNumber();
                VariableHashMap.put(functionDefinitionNode.getLocalVariablesList().get(i).getVariableName(),new IntDataType(value));
            }
            else if(functionDefinitionNode.getLocalVariablesList().get(i).getType().equals(VariableNode.Type.REAL))
            {
                float value = ((FloatNode) functionDefinitionNode.getLocalVariablesList().get(i).getNode()).getNumber();
                VariableHashMap.put(functionDefinitionNode.getLocalVariablesList().get(i).getVariableName(),new FloatDataType(value));
            }
            
        }

        

    }

    public static void InterpretBlock(ArrayList<StatementNode> StatementList, HashMap<String,InterpreterDataType> VariableHashMap)
    {
        for (StatementNode statementNode : StatementList) {
            if(statementNode instanceof FunctionCallNode)
            {
                
            }
            else if(statementNode instanceof BuiltInFunctionNode)
            {

            }
            else
            {
                //Do nothing
            }
        }
    }


}
