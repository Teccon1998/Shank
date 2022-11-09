import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemanticAnalysis {
    
    private ArrayList<String> ErrorStack;

    public ArrayList<String> getErrorStack() {
        return this.ErrorStack;
    }

    public SemanticAnalysis()
    {
        this.ErrorStack = new ArrayList<>();
    }
    public boolean CheckFunctions(ArrayList<FunctionDefinitionNode> functionDefinitions) throws Exception
    {
        HashMap<String, Boolean> functionCheck = new HashMap<>();
        //Check each function
        for (FunctionDefinitionNode functionDefinitionNode : functionDefinitions) {
            //Check each statement in that function
            for(StatementNode statementNode : functionDefinitionNode.getStatementList())
            {
                if (CheckStatements(statementNode, functionDefinitionNode)) {
                    functionCheck.put(functionDefinitionNode.getFunctionName(), true);
                } else {
                    functionCheck.put(functionDefinitionNode.getFunctionName(), false);
                }
            }
        }
        
        for (Map.Entry<String, Boolean> KeyValuePair : functionCheck.entrySet())
        {
            if (KeyValuePair.getValue() == true) {
                continue;
            } else {
                ErrorStack.add("Error in function: "+KeyValuePair.getValue());
            }
        }
        // Collections.reverse(ErrorStack);
        if (this.ErrorStack.size() == 0)
        {
            return true;
        }
        return false;
    }

    private boolean CheckStatements(StatementNode statementNode,FunctionDefinitionNode functionDefinitionNode) throws Exception
    {


        HashMap<String, Boolean> statementCheck = new HashMap<>();
        //If the statement is assignment node check its assignments
        if (statementNode.getStatement() instanceof AssignmentNode) 
        {
            AssignmentNode assignmentNode = (AssignmentNode) statementNode.getStatement();
            if (CheckAssignment(assignmentNode, functionDefinitionNode)) {
                statementCheck.put(assignmentNode.getASTNODE().toString(), true);
            } else {
                statementCheck.put(assignmentNode.getASTNODE().toString(), false);
            }
        }
        //If its a while node we cast the statement to a whileNode and then recurse and check each statement.
        //Same for everything that's not an assignment node.
        else if (statementNode.getStatement() instanceof WhileNode) 
        {
            WhileNode whileNode = ((WhileNode) statementNode.getStatement());
            for(StatementNode WhileNodeStatement : whileNode.getStatements())
            {
                CheckStatements(WhileNodeStatement, functionDefinitionNode);
            }
        } 
        else if (statementNode.getStatement() instanceof IfNode) 
        {
            //do stuff THEN check else node. Might contain an elsif
            IfNode ifNode = ((IfNode) statementNode.getStatement());
            for (StatementNode IfNodeStatement : ifNode.getStatements())
            {
                CheckStatements(IfNodeStatement, functionDefinitionNode);
            }
            //if the ifnode contains an else
            if (ifNode.getElseNode() != null) 
            {
                //if its an elsif itll be another ifNode in the elseNode
                if(ifNode.getElseNode() instanceof IfNode)
                {
                    //cast ifnode's elseNode to an IfNode because its an elsifNode and then get its statements
                    for(StatementNode elsifStatementNode : (((IfNode)ifNode.getElseNode()).getStatements()))
                    {
                        CheckStatements(elsifStatementNode, functionDefinitionNode);
                    }
                }
                //if its an else itll be an elseNode in the elseNode
                else if(ifNode.getElseNode() instanceof ElseNode)
                {
                    for(StatementNode ElseStatementNode : ((ElseNode)ifNode.getElseNode()).getStatements())
                    {
                        CheckStatements(ElseStatementNode, functionDefinitionNode);
                    }
                }
            }
        } 
        else if (statementNode.getStatement() instanceof RepeatNode) 
        {
            RepeatNode repeatNode = ((RepeatNode) statementNode.getStatement());
            for(StatementNode RepeatStatementNode : repeatNode.getStatements())
            {
                CheckStatements(RepeatStatementNode, functionDefinitionNode);
            }
        } 
        else if (statementNode.getStatement() instanceof ForNode) 
        {
            ForNode forNode = ((ForNode) statementNode.getStatement());
            for(StatementNode forStatementNode : forNode.getStatements())
            {
                CheckStatements(forStatementNode, functionDefinitionNode);
            }
        } 
        else if (statementNode.getStatement() instanceof FunctionCallNode) 
        {
            //Something?
        }

        for (Map.Entry<String, Boolean> KeyValuePair : statementCheck.entrySet())
        {
            if (KeyValuePair.getValue() == true) {
                continue;
            } else {
                ErrorStack.add("Error in statement: "+KeyValuePair.getValue());
            }
        }
        // Collections.reverse(ErrorStack);
        if (this.ErrorStack.size() == 0)
        {
            return true;
        }
        return false;
    }

    private boolean CheckAssignment(AssignmentNode assignmentNode,FunctionDefinitionNode functionDefinitionNode) throws Exception
    {
        if (assignmentNode.getASTNODE() instanceof MathOpNode) {
            assignmentNode.setASTNODE(Operate(assignmentNode,functionDefinitionNode));
        }
        String AssignmentVariableName = assignmentNode.getVariableReferenceNode().getVariableName();
        for (VariableNode functionsVariableNode : functionDefinitionNode.getLocalVariablesList()) {
            if (functionsVariableNode.getVariableName().equals(AssignmentVariableName)) {
                if (functionsVariableNode.getType().equals(VariableNode.Type.INTEGER)) {
                    if (!(assignmentNode.getASTNODE() instanceof IntegerNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: INTEGER");
                        return false;
                    }
                } else if (functionsVariableNode.getType().equals(VariableNode.Type.REAL)) {
                    if (!(assignmentNode.getASTNODE() instanceof FloatNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: FLOAT");
                        return false;
                    }
                } else if (functionsVariableNode.getType().equals(VariableNode.Type.BOOLEAN)) {
                    if (!(assignmentNode.getASTNODE() instanceof BoolNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: BOOLEAN");
                        return false;
                    }
                } else if (functionsVariableNode.getType().equals(VariableNode.Type.STRING)) {
                    if (!(assignmentNode.getASTNODE() instanceof StringNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: STRING");
                        return false;
                    }
                } else if (functionsVariableNode.getType().equals(VariableNode.Type.CHAR)) {
                    if (!(assignmentNode.getASTNODE() instanceof CharNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: CHAR");
                        return false;
                    }
                }

            }
        }
        for (VariableNode functionsParameterNode : functionDefinitionNode.getParameterVariableNodes()) {
            if (functionsParameterNode.getVariableName().equals(AssignmentVariableName)) {
                if (functionsParameterNode.getType().equals(VariableNode.Type.INTEGER)) {
                    if (!(assignmentNode.getASTNODE() instanceof IntegerNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: INTEGER");
                        return false;
                    }
                } else if (functionsParameterNode.getType().equals(VariableNode.Type.REAL)) {
                    if (!(assignmentNode.getASTNODE() instanceof FloatNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: FLOAT");
                        return false;
                    }
                } else if (functionsParameterNode.getType().equals(VariableNode.Type.BOOLEAN)) {
                    if (!(assignmentNode.getASTNODE() instanceof BoolNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: BOOLEAN");
                        return false;
                    }
                } else if (functionsParameterNode.getType().equals(VariableNode.Type.STRING)) {
                    if (!(assignmentNode.getASTNODE() instanceof StringNode || assignmentNode.getASTNODE() instanceof CharNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: STRING OR CHAR");
                        return false;
                    }
                } else if (functionsParameterNode.getType().equals(VariableNode.Type.CHAR)) {
                    if (!(assignmentNode.getASTNODE() instanceof CharNode)) {
                        this.ErrorStack
                                .add("Error with: " + AssignmentVariableName + ". Its Node is not of type: CHAR");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private Node Operate(Node node,FunctionDefinitionNode functionDefinitionNode) throws Exception
    {
        if(node instanceof IntegerNode)
        {
            return (IntegerNode) node;
        }
        else if (node instanceof FloatNode) 
        {
            return ((FloatNode) node);
        }
        else if(node instanceof AssignmentNode)
        {
            return Operate(((MathOpNode) ((AssignmentNode) node).getASTNODE()), functionDefinitionNode);
        }
        else if (node instanceof MathOpNode)
        {
            MathOpNode mathOpNode = (MathOpNode) node;
            Node node1 = Operate(mathOpNode.getNodeOne(), functionDefinitionNode);
            Node node2 = Operate(mathOpNode.getNodeTwo(), functionDefinitionNode);
            MathOpNode.Operator operatorHolder = mathOpNode.getOperator();
            //Recursive search for value

            if (node1 instanceof FloatNode && node2 instanceof FloatNode) {
                float val1 = ((FloatNode) node1).getNumber();
                float val2 = ((FloatNode) node2).getNumber();
                if (operatorHolder.equals(MathOpNode.Operator.ADD)) {
                    return new FloatNode(val1 + val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.SUBTRACT)) {
                    return new FloatNode(val1 - val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.DIVIDE)) {
                    return new FloatNode(val1 / val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.TIMES)) {
                    return new FloatNode(val1 * val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.MODULO)) {
                    return new FloatNode(val1 % val2);
                }
            } else if (node1 instanceof IntegerNode && node2 instanceof IntegerNode) {
                int val1 = ((IntegerNode) node1).getNumber();
                int val2 = ((IntegerNode) node2).getNumber();

                if (operatorHolder.equals(MathOpNode.Operator.ADD)) {
                    return new IntegerNode(val1 + val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.SUBTRACT)) {
                    return new IntegerNode(val1 - val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.DIVIDE)) {
                    return new IntegerNode(val1 / val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.TIMES)) {
                    return new IntegerNode(val1 * val2);
                } else if (operatorHolder.equals(MathOpNode.Operator.MODULO)) {
                    return new IntegerNode(val1 % val2);
                }
            }
            else if(node1 instanceof StringNode && (node2 instanceof StringNode || node2 instanceof CharNode))
            {
                String str1 = ((StringNode) node1).getString();
                String str2 = "";
                if(node2 instanceof StringNode)
                {
                    str2 = ((StringNode) node2).getString();
                }
                else if(node2 instanceof CharNode)
                {
                    char c = ((CharNode) node2).getChar();
                    str2 = Character.toString(c);
                }
                if(operatorHolder.equals(MathOpNode.Operator.ADD))
                {
                    return new StringNode(str1 + str2);
                }
                else
                {
                    throw new Exception("Cannot perform other actions on strings except add.");
                }
            } 
            else 
            {
                throw new Exception("No implicit type casting. Both values must be of same type to resolve.");
            }
        }
        else if (node instanceof VariableReferenceNode)
        {
            VariableReferenceNode varRefNode = (VariableReferenceNode) node;
            String varRefString = varRefNode.getVariableName();
            for (VariableNode functionsVariableNode : functionDefinitionNode.getLocalVariablesList()) {
                if (functionsVariableNode.getVariableName().equals(varRefString)) {
                    if (functionsVariableNode.getType().equals(VariableNode.Type.INTEGER)) {
                        return new IntegerNode(0);
                    } else if (functionsVariableNode.getType().equals(VariableNode.Type.REAL)) {
                        return new FloatNode(0);
                    } else if (functionsVariableNode.getType().equals(VariableNode.Type.STRING)) {
                        return new StringNode("");
                    }
                }
            }
            for(VariableNode functionsVariableNode : functionDefinitionNode.getParameterVariableNodes())
            {
                if(functionsVariableNode.getVariableName().equals(varRefString))
                {
                    if (functionsVariableNode.getType().equals(VariableNode.Type.INTEGER)) {
                        return new IntegerNode(0);
                    } else if (functionsVariableNode.getType().equals(VariableNode.Type.REAL)) {
                        return new FloatNode(0);
                    } else if (functionsVariableNode.getType().equals(VariableNode.Type.STRING)) {
                        return new StringNode("");
                    }
                }
            }
        }
        else if (node instanceof StringNode)
        {
            String str = ((StringNode) node).getString();
            return new StringNode(str);
        }
        else if (node instanceof CharNode)
        {
            char c = ((CharNode) node).getChar();
            return new CharNode(c);
        }
        throw new Exception("Invalid Value");
    }
}
