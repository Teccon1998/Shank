import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {

    static HashMap<String, CallableNode> functionsHashmap;

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
        
        HashMap<String,InterpreterDataType> variables = new HashMap<>();
        for (ParameterNode parameterNode : functionCallNode.getParameterNodes()) {
            if (parameterNode.getValueNode() == null || parameterNode.getValueNode() == null) {
                throw new Exception("null value");
            }
            if (parameterNode.getValueNode() instanceof IntegerNode) {
                variables.put(parameterNode.getVarRefNode().getVariableName(),
                        new IntDataType(((IntegerNode) parameterNode.getValueNode()).getNumber()));
            } else if (parameterNode.getValueNode() instanceof FloatNode) {
                variables.put(parameterNode.getVarRefNode().getVariableName(),
                        new FloatDataType(((FloatNode) parameterNode.getValueNode()).getNumber()));
            }

        }
        for(VariableNode variableNode : functionsHashmap.get(functionCallNode.getFunctionName()).getLocalVariablesList())
        {
            if (variableNode.getNode() instanceof IntegerNode) {
                variables.put(variableNode.getVariableName(),
                        new IntDataType(((IntegerNode) variableNode.getNode()).getNumber()));
            } else if (variableNode.getNode() instanceof FloatNode) {
                variables.put(variableNode.getVariableName(),
                        new FloatDataType(((FloatNode) variableNode.getNode()).getNumber()));
            }
        }
        InterpretBlock(functionsHashmap.get(functionCallNode.getFunctionName()).getStatementList(), variables);
        
    }
    
    
    public static void InterpretBlock(ArrayList<StatementNode> StatementList, HashMap<String, InterpreterDataType> VariableHashMap) throws Exception {
        for (int i = 0; i < StatementList.size(); i++) 
        {
            StatementNode statementNode = StatementList.get(i);
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
                        ParameterNode parameterNode = calledNode.getParameterNodes().get(j);
                        ArrayList<InterpreterDataType> datainputOrOutput = new ArrayList<>();
                        FloatDataType floatDataType = new FloatDataType(0);
                        datainputOrOutput.add(j,floatDataType);
                        datainputOrOutput = builtin.Execute(datainputOrOutput);
                        VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),datainputOrOutput.get(j));
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
                            if (parameterNode.getValueNode() instanceof IntegerNode) {
                                VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                                        new IntDataType(((IntegerNode) parameterNode.getValueNode()).getNumber()));
                            } else if (parameterNode.getValueNode() instanceof FloatNode) {
                                VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),
                                        new FloatDataType(((FloatNode) parameterNode.getValueNode()).getNumber()));
                            }
                        }
                        InterpretFunction(calledNode, dataTypes);
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
