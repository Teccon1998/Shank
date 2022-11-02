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

    public static boolean EvaluateBooleanExpression(BooleanNode booleanNode) throws Exception
    {

        FloatNode floatNode1 = Interpreter.Resolve(booleanNode.getLeftNode());
        FloatNode floatNode2 = Interpreter.Resolve(booleanNode.getRightNode());
        Token Condition = booleanNode.getCondition();

        Float float1 = floatNode1.getNumber();
        Float float2 = floatNode2.getNumber();

        switch(Condition.getTokenType())
        {
            case EQUALS:
                if (float1 == float2)
                {
                    return true;
                }
                break;
            case GREATER:
                if (float1 > float2)
                {
                    return true;
                }
                break;
            case GREATEREQUAL:
                if (float1 >= float2)
                {
                    return true;
                }
                break;
            case LESS:
                if (float1 < float2)
                {
                    return true;
                }
                break;
            case LESSEQUAL:
                if (float1 <= float2)
                {
                    return true;
                }
                break;
            case NOTEQUAL:
                if (float1 != float2)
                {
                    return true;
                }
                break;
            default:
                throw new Exception("Not a valid condition");  
        }
        return false;
    }

    public static FloatNode Resolve(Node node) throws Exception {
        if (node instanceof IntegerNode) 
        {
            float floatCastHolder = ((IntegerNode) node).getNumber();
            return new FloatNode(floatCastHolder);
        } 
        else if (node instanceof FloatNode) 
        {
            return ((FloatNode) node);
        }
        else if (node instanceof MathOpNode)
        {
            MathOpNode mathOpNode = (MathOpNode) node;
            FloatNode node1;
            FloatNode node2;
            MathOpNode.Operator operatorHolder = mathOpNode.getOperator();
            

            //Recursive search for value
            node1 = Interpreter.Resolve(mathOpNode.getNodeOne());
            node2 = Interpreter.Resolve(mathOpNode.getNodeTwo());

            if (operatorHolder.equals(MathOpNode.Operator.ADD)) {
                return new FloatNode(node1.getNumber() + node2.getNumber());
            } else if (operatorHolder.equals(MathOpNode.Operator.SUBTRACT)) {
                return new FloatNode(node1.getNumber() - node2.getNumber());
            } else if (operatorHolder.equals(MathOpNode.Operator.DIVIDE)) {
                return new FloatNode(node1.getNumber() / node2.getNumber());
            } else if (operatorHolder.equals(MathOpNode.Operator.TIMES)) {
                return new FloatNode(node1.getNumber() * node2.getNumber());
            } else if (operatorHolder.equals(MathOpNode.Operator.MODULO)) {
                return new FloatNode(node1.getNumber() % node2.getNumber());
            }

        }
        else if (node instanceof VariableNode)
        {
            VariableNode varNode = (VariableNode) node;
            return (FloatNode) varNode.getNode();
        }
        else if (node instanceof VariableReferenceNode)
        {
            VariableReferenceNode varRefNode = (VariableReferenceNode) node;
            InterpreterDataType interpretDataType = VariableHashMap.get(varRefNode.getVariableName());
            FloatNode floatNode = new FloatNode(((FloatDataType) interpretDataType).getFloatValue());
            return floatNode;
        }
        throw new Exception("Cannot find reference to value");
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
         * Adds local variables to the Hashmap so they can be called.5
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
            else
            {
                throw new Exception("Invalid variable type pass");
            }
        }


        int i=0;
        for(ParameterNode parameterNode : functionCallNode.getParameterNodes())
        {
            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),dataTypes.get(i));
            i++;
        }
        /*
         * When a variable is mutable in a function
         * it overwrites the local variables in the parent function, so long as the parent function's
         * definition allows that variable to be overriden.
         */
        
        Interpreter.InterpretBlock(functionsHashmap.get(functionCallNode.getFunctionName()).getStatementList(), VariableHashMap);
    }
    
    
    public static void InterpretBlock(ArrayList<StatementNode> StatementList, HashMap<String, InterpreterDataType> VariableHashMap) throws Exception {
        //interprets every statement.
        for (int i = 0; i < StatementList.size(); i++) 
        {
            StatementNode statementNode = StatementList.get(i);
            /*
             * Every statement checks if its a builtinfunctionNode
             * to change behavior. Builtin's need different code run
             * compared to User Defined functions.
             */
            //This checks if its a function call and if not its an assignment.
            if (statementNode.getStatement() instanceof FunctionCallNode)
            {
                FunctionCallNode calledNode = (FunctionCallNode) statementNode.getStatement();
                
                /*
                 * Checks if its a built in or a 
                 * user defined function node.
                 * Built in function nodes go directly to interpretblock 
                 * and user defined functions need to check the function definition
                 * to have their statements checked for either a function call, or an assignment or a boolean check.
                 */
                if(functionsHashmap.get(calledNode.getFunctionName())instanceof BuiltInFunctionNode)
                {
                    BuiltInFunctionNode builtin = (BuiltInFunctionNode) functionsHashmap.get(calledNode.getFunctionName());
                    
                    for (ParameterNode parameterNode : calledNode.getParameterNodes()) 
                    {
                        if (parameterNode.getValueNode() instanceof IntegerNode) 
                        {
                            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),new IntDataType(((IntegerNode) parameterNode.getValueNode()).getNumber()));
                        }
                        else if (parameterNode.getValueNode() instanceof FloatNode) 
                        {
                            VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),new FloatDataType(((FloatNode) parameterNode.getValueNode()).getNumber()));
                        }
                        
                    }
                    ArrayList<InterpreterDataType> datainputOrOutput = new ArrayList<>();
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
                            else
                            {
                                throw new Exception("Incorrect variable type pass");
                            } 
                        }
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
                            else
                            {
                                throw new Exception("Incorrect variable type pass");
                            } 
                        }
                        VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),datainputOrOutput.get(0));
                    }
                    builtin.Execute(datainputOrOutput);

                    for(int j = 0; j < calledNode.getParameterNodes().size();j++)
                    {
                        ParameterNode parameterNode = calledNode.getParameterNodes().get(j);
                        VariableHashMap.put(parameterNode.getVarRefNode().getVariableName(),datainputOrOutput.get(j));
                    }

                }
                else
                {
                    /*
                     * If not a built in that must mean its a user defined function
                     * First it gets the function and then if the function is not defined it throws an error.
                     * This is done to ensure that there's an actual function defined and its parameternodes match.
                     */
                    FunctionDefinitionNode functionDefinitionNode = (FunctionDefinitionNode) functionsHashmap
                            .get(calledNode.getFunctionName());
                    if(functionDefinitionNode == null)
                    {
                        throw new Exception("Function not found.");
                    }
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
            //interprets assignments
            else if(statementNode.getStatement() instanceof AssignmentNode)
            {
                AssignmentNode assignmentNode = (AssignmentNode) statementNode.getStatement();
                VariableReferenceNode refNodeOfAssignNode = assignmentNode.getVariableReferenceNode();
                String stringOfRefNode = refNodeOfAssignNode.getVariableName();

                if (VariableHashMap.containsKey(stringOfRefNode)) {
                    InterpreterDataType refNodDataType = VariableHashMap.get(stringOfRefNode);
                    if (refNodDataType instanceof IntDataType) {
                        IntDataType intRefNodeDataType = (IntDataType) refNodDataType;
                        Node node = assignmentNode.getASTNODE();
                        FloatNode floatNode = Interpreter.Resolve(node);
                        FloatDataType newFloatDataType = new FloatDataType(floatNode.getNumber());
                        VariableHashMap.replace(stringOfRefNode, intRefNodeDataType, newFloatDataType);
                    } else if (refNodDataType instanceof FloatDataType) {
                        FloatDataType floatRefNodeDataType = (FloatDataType) refNodDataType;
                        Node node = assignmentNode.getASTNODE();
                        FloatNode floatNode = Interpreter.Resolve(node);
                        FloatDataType newFloatDataType = new FloatDataType(floatNode.getNumber());
                        VariableHashMap.replace(stringOfRefNode, floatRefNodeDataType, newFloatDataType);
                    }
                } else {
                    throw new Exception("This variable does not exist in this scope.");
                }

            }
            else if(statementNode.getStatement() instanceof IfNode)
            {
                IfNode ifNode = (IfNode) statementNode.getStatement();
                if(ifNode.getBoolNode()!= null)
                {
                    BooleanNode booleanNode = ifNode.getBoolNode();
                    if (EvaluateBooleanExpression(booleanNode)) {
                        InterpretBlock(ifNode.getStatements(), VariableHashMap);
                    } else {
                        if (ifNode.getElseNode() != null) {

                            if (ifNode.getElseNode() instanceof IfNode) {
                                IfNode elseifNode = (IfNode) ifNode.getElseNode();
                                InterpretBlock(elseifNode.getStatements(), VariableHashMap);
                            } else if (ifNode.getElseNode() instanceof ElseNode) {
                                ElseNode elseNode = (ElseNode) ifNode.getElseNode();
                                InterpretBlock(elseNode.getStatements(), VariableHashMap);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                else
                {
                    throw new Exception("Not a valid boolean.");
                }
                
            }
            else if(statementNode.getStatement() instanceof ElseNode)
            {
                ElseNode elseNode = (ElseNode) statementNode.getStatement();
                InterpretBlock(elseNode.getStatements(), VariableHashMap);
            }
            else if(statementNode.getStatement() instanceof WhileNode)
            {
                WhileNode whileNode = (WhileNode) statementNode.getStatement();
                if(whileNode.getBoolNode() != null)
                {
                    BooleanNode booleanNode = whileNode.getBoolNode();
                    if (EvaluateBooleanExpression(booleanNode)) {
                        InterpretBlock(whileNode.getStatements(), VariableHashMap);
                    } else {
                        continue;
                    }
                }
                else
                {
                    throw new Exception("Not a valid boolean.");
                }
            }
            else if(statementNode.getStatement() instanceof RepeatNode)
            {
                RepeatNode repeatNode = (RepeatNode) statementNode.getStatement();
                if(repeatNode.getBooleanNode() != null)
                {
                    BooleanNode booleanNode = repeatNode.getBooleanNode();
                    if (EvaluateBooleanExpression(booleanNode)) {
                        InterpretBlock(repeatNode.getStatements(), VariableHashMap);
                    } else {
                        continue;
                    }
                }
                else
                {
                    throw new Exception("Not a valid boolean.");
                }
                
                
            }
            else if(statementNode.getStatement() instanceof ForNode)
            {
                ForNode forNode = (ForNode) statementNode.getStatement();
                if(forNode.getStartNode() != null && forNode.getEndNode() != null && forNode.getVariableReference() != null)
                {
                    if((forNode.getStartNode())instanceof IntegerNode)
                    {
                        if(forNode.getEndNode() instanceof IntegerNode)
                        {
                            //Checking for the variable's pre existance in the variable hashmap.
                            InterpreterDataType variable = VariableHashMap.get(forNode.getVariableReference().getVariableName());
                            //If it exists make sure its value isnt a float. If it is a float throw an exception because we cant iterate over a float.
                            if(variable!= null)
                            {
                                // Float variableValueHolder = ((FloatDataType) variable).getFloatValue();

                                // if(variableValueHolder % 1 == 0)
                                // {

                                // }
                                // else
                                // {
                                //     throw new Exception("The variable you are referencing is a float and cannot be iterated over.");
                                // }
                                throw new Exception("This value already exists and cannot be used in a for loop.");
                            }
                            else
                            {
                                IntDataType startInt = new IntDataType(((IntegerNode) forNode.getStartNode()).getNumber());
                                VariableHashMap.put(forNode.getVariableReference().getVariableName(), startInt);
                                //TODO: Ask phipps if for loops run from "1 to 10" where they write that variable should print 1 through 9 or 1 through 10
                                for(int startVal = ((IntegerNode) forNode.getStartNode()).getNumber(); startVal <= ((IntegerNode) forNode.getEndNode()).getNumber(); startVal++)
                                {
                                    VariableHashMap.replace(forNode.getVariableReference().getVariableName(),new IntDataType(startVal));
                                    Interpreter.InterpretBlock(forNode.getStatements(), VariableHashMap);
                                }
                            }
                        }
                        else
                        {
                            throw new Exception("EndNode is not a valid integer.");
                        }
                        /*
                         * TODO: Implement for node. This block is for if the start node 
                         * is an actual float, or if the float is divisible by 1 and remainder is 0.
                         * 
                         * TODO: Outside this if statement do the same for end node. Only if these two conditions are true
                         * then check if the variable reference node already exists in the variablehashmap. If it does check 
                         * that value and make sure its divisible by 1 as well. 
                         * 
                         *          If it does exist check its value in the hashmap and make sure that its not greater than
                         *          the end node. If the value of the variable in the hashmap is less than endNode then run
                         *          that shank for loop in a java for loop as many times as it is needed with interpret block.
                         *          At the end of the loop DONT delete the key value pair from the variablehashmap.
                         *          If the value of the variable in the hashmap is greater than the endNode then break out of this
                         *          instanceof by calling java "continue;"
                         *         
                         *          If it doesnt exist create that String and InterpreterDataType in the hashmap
                         *          and get the startNode number and the endNode number, store then both in int variables and
                         *          run a for loop executing its statements with interpretBlock. At the end of the loop delete the
                         *          key value pair of the variable reference node in the hashmap.
                         *          
                         */
                    }
                    else
                    {
                        throw new Exception("StartNode is not a valid integer.");
                    }
                    
                }
                else
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("ERROR IN FOR NODE: ");
                    //Checks var ref
                    if(forNode.getVariableReference()== null)
                    {
                        sb.append("Var Ref Node is null");
                    }
                    else
                    {
                        sb.append("Var Ref Node is: "+((VariableReferenceNode)forNode.getVariableReference()).getVariableName());
                    }
                    sb.append(", ");
                    //Checks Start Node
                    if(forNode.getStartNode()== null)
                    {
                        sb.append("Start Node is null");
                    }
                    else
                    {
                        sb.append("Start Node is: "+((FloatNode)forNode.getStartNode()).getNumber());
                    }
                    sb.append(", ");
                    //Check end Node
                    if(forNode.getEndNode()== null)
                    {
                        sb.append("End Node is null");
                    }
                    else
                    {
                        sb.append("End Node is: "+((FloatNode)forNode.getEndNode()).getNumber());
                    }
                    
                    sb.append("\n");
                    throw new Exception(sb.toString());
                }
            }
            
        }
        
    }

}
