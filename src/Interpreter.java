import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {

    static HashMap<String, CallableNode> functionsHashmap;
    public static HashMap<String,InterpreterDataType> VariableHashMap = new HashMap<>();

    public Interpreter() {
    }

    public Interpreter(HashMap<String, CallableNode> functionsHashmap) {
        Interpreter.functionsHashmap = functionsHashmap;
    }

    
    public HashMap<String,CallableNode> getFunctionsHashMap()
    {
        return Interpreter.functionsHashmap;
    }

    public FloatNode Resolve(Node node) {
        if (node instanceof IntegerNode) {
            float floatCastHolder = ((IntegerNode) node).getNumber();
            return new FloatNode(floatCastHolder);
        } else if (node instanceof FloatNode) {
            return ((FloatNode) node);
        }
        return null;
    }

    public static void InterpretFunction(FunctionCallNode functionCallNode, ArrayList<InterpreterDataType> dataTypes) throws Exception
    {
        
        //Create map to hold variables
        /*
         * for loop checks the functionCall and checks to see what parameters it's given and builds
         * InterpreterDataTypes for us.
         */
        for (ParameterNode parameterNode : functionCallNode.getParameterNodes()) {
            
            if (parameterNode.getValueNode() instanceof IntegerNode) {
                VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                        new IntDataType(((IntegerNode) parameterNode.getValueNode()).getNumber()));
            } else if (parameterNode.getValueNode() instanceof FloatNode) {
                VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                        new FloatDataType(((FloatNode) parameterNode.getValueNode()).getNumber()));
            }

        }
        /*
         * Adds local variables to the Hashmap
         */
        for(VariableNode variableNode : functionsHashmap.get(functionCallNode.getFunctionName()).getLocalVariablesList())
        {
            if (variableNode.getNode() instanceof IntegerNode) 
            {
                VariableHashMap.put(variableNode.getVariableName(),new IntDataType(((IntegerNode) variableNode.getNode()).getNumber()));
            } 
            else if (variableNode.getNode() instanceof FloatNode) 
            {
                VariableHashMap.put(variableNode.getVariableName(),new FloatDataType(((FloatNode) variableNode.getNode()).getNumber()));
            }
        }


        //Works perfectly ^
        int i=0;
        for(ParameterNode parameterNode : functionCallNode.getParameterNodes())
        {
            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),dataTypes.get(i));
            i++;
        }
        /*
         * When a variable is returned from a function
         * it overwrites the local variables in the parent function.
         */
        
        Interpreter.InterpretBlock(functionsHashmap.get(functionCallNode.getFunctionName()).getStatementList(), VariableHashMap);
    }
    
    
    public static void InterpretBlock(ArrayList<StatementNode> StatementList, HashMap<String, InterpreterDataType> VariableHashMap) throws Exception {
        for (int i = 0; i < StatementList.size(); i++) 
        {
            StatementNode statementNode = StatementList.get(i);
            /*
             * Every statement checks if its a builtinfunctionNode
             * to change behavior. Builtin's need different code run
             * compared to User Defined functions.
             */
            if (statementNode.getStatement() instanceof FunctionCallNode)
            {
                FunctionCallNode calledNode = (FunctionCallNode) statementNode.getStatement();
                if(functionsHashmap.get(calledNode.getFunctionName())instanceof BuiltInFunctionNode)
                {
                    BuiltInFunctionNode builtin = (BuiltInFunctionNode) functionsHashmap
                            .get(calledNode.getFunctionName());
                    for (ParameterNode parameterNode : calledNode.getParameterNodes()) 
                    {
                        if (parameterNode.getValueNode() instanceof IntegerNode) {
                            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                                    new IntDataType(((IntegerNode) parameterNode.getValueNode()).getNumber()));
                        } else if (parameterNode.getValueNode() instanceof FloatNode) {
                            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                                    new FloatDataType(((FloatNode) parameterNode.getValueNode()).getNumber()));
                        }
                    }

                    for(int j = 0; j < calledNode.getParameterNodes().size();j++)
                    {
                        VariableReferenceNode ParamNodeVarNode = calledNode.getParameterNodes().get(j).getVarRefNode();
                        //Sets VarRefNode
                        ParameterNode parameterNode = new ParameterNode(ParamNodeVarNode);
                        if(VariableHashMap.get(calledNode.getParameterNodes().get(j).getVarRefNode().getVariableName())!= null)
                        {
                            if(VariableHashMap.get(calledNode.getParameterNodes().get(j).getVarRefNode().getVariableName()) instanceof IntDataType)
                            {
                                IntDataType integerDataType = (IntDataType) VariableHashMap.get(calledNode.getParameterNodes().get(j).getVarRefNode().getVariableName());
                                parameterNode.setValueNode(new IntegerNode(integerDataType.getIntValue()));
                            }
                            else if(VariableHashMap.get(calledNode.getParameterNodes().get(j).getVarRefNode().getVariableName()) instanceof FloatDataType)
                            {
                                FloatDataType floatDataType = (FloatDataType) VariableHashMap.get(calledNode.getParameterNodes().get(j).getVarRefNode().getVariableName());
                                parameterNode.setValueNode(new FloatNode(floatDataType.getFloatValue()));
                            }   
                        }
                        ArrayList<InterpreterDataType> datainputOrOutput = new ArrayList<>();
                        // FloatDataType floatDataType = new FloatDataType(0);
                        // IntDataType intDataType = new IntDataType(0);
                        if(parameterNode.getValueNode()!=null)
                        {
                            if(parameterNode.getValueNode()instanceof FloatNode)
                            {
                                FloatNode floatnode = (FloatNode) parameterNode.getValueNode();
                                FloatDataType floatDataType = new FloatDataType(floatnode.getNumber());
                                datainputOrOutput.add(floatDataType);
                            }
                            else if(parameterNode.getValueNode()instanceof IntegerNode)
                            {
                                IntegerNode intnode = (IntegerNode) parameterNode.getValueNode();
                                IntDataType intDataType = new IntDataType(intnode .getNumber());
                                datainputOrOutput.add(intDataType);

                            }
                        }
                        builtin.Execute(datainputOrOutput);
                        VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),datainputOrOutput.get(0));
                    }
                    
                }
                else
                {
                    FunctionDefinitionNode functionDefinitionNode = (FunctionDefinitionNode) functionsHashmap
                            .get(calledNode.getFunctionName());
                    int calledNodeSize = 0;
                    if(calledNode.getParameterNodes()!= null)
                    {
                        calledNodeSize = calledNode.getParameterNodes().size();
                    }        
                    
                    if (calledNodeSize == functionDefinitionNode.getParameterVariableNodes().size())
                    {
                        ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                        for (ParameterNode parameterNode : calledNode.getParameterNodes()) {
                            if (VariableHashMap.containsKey(parameterNode.getVarRefNode().getVariableName())) 
                            {
                                dataTypes.add(VariableHashMap.get(parameterNode.getVarRefNode().getVariableName()));
                                
                            } 
                            
                        }
                        Interpreter.InterpretFunction(calledNode, dataTypes);
                    }
                }
            }
            else
            {
                //Do nothing
            }
            
        }
        
    }

}
