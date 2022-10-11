import java.util.*;

public class Parser {
    
    private List<Token> tokenList;

    public Parser(List<Token> tokenList)
    {
        this.tokenList = tokenList;
    }

    public Token MatchAndRemove(Token.Type type)
    {
        if (this.tokenList.size() > 0) {
            if (tokenList.get(0).getTokenType() == type) {
                Token returnToken = tokenList.get(0);
                tokenList.remove(0);
                return returnToken;
            }
        }
        return null;

    }

    public Node Expression() throws Exception
    {
        Node FirstNode = Term();
        Node SecondNode = null;
        
            if (MatchAndRemove(Token.Type.PLUS) != null) {
                SecondNode = Expression();
                return new MathOpNode(FirstNode, SecondNode, MathOpNode.Operator.ADD);
            } else if ((MatchAndRemove(Token.Type.MINUS) != null)) {
                SecondNode = Expression();
                return new MathOpNode(FirstNode, SecondNode, MathOpNode.Operator.SUBTRACT);
            } else if (FirstNode != null) {
                return FirstNode;
            } else {
                return null;
            }
    }

    public Node Term() throws Exception
    {
        Node FirstNode = Factor();
         if(MatchAndRemove(Token.Type.TIMES) != null)
        {
            return new MathOpNode(FirstNode, Term(), MathOpNode.Operator.TIMES);
        }
        else if((MatchAndRemove(Token.Type.DIVIDE) != null))
        {
            return new MathOpNode(FirstNode, Term(), MathOpNode.Operator.DIVIDE);
        }
        else if((MatchAndRemove(Token.Type.MOD)!= null))
        {
            return new MathOpNode(FirstNode, Term(), MathOpNode.Operator.MODULO);
        }
        else if(FirstNode != null)
        {
            return FirstNode;
        }
        else
        {
            return null;
        }
    }
    public Node Factor() throws Exception
    {
        
        Token Value;
        Node Factor;
        if ((Value = MatchAndRemove(Token.Type.NUMBER)) != null)
        {
            return new IntegerNode(Integer.parseInt(Value.getValue()));
        }
        else if ((Value = MatchAndRemove(Token.Type.DECIMAL)) != null)
        {
            return new FloatNode(Float.parseFloat(Value.getValue()));
        }
        else if((Value = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
        {

        }
        else if (MatchAndRemove(Token.Type.LPAREN) != null)
        {
            Factor = Expression();
            if (MatchAndRemove(Token.Type.RPAREN) == null) {
                throw new Exception("No Closing Paren");
            } else {
                return Factor;
            }
        }
        return null;
    }
    public BooleanNode BooleanExpression() throws Exception
    {
        BooleanNode booleanNode = new BooleanNode();
        Token booleanCondition;
        booleanNode.setLeftNode(Expression());
        if((booleanCondition = MatchAndRemove(Token.Type.LESS))!= null || (booleanCondition = MatchAndRemove(Token.Type.GREATER))!= null || (booleanCondition = MatchAndRemove(Token.Type.LESSEQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.GREATEREQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.NOTEQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.EQUALS))!= null)
        {
            booleanNode.setCondition(booleanCondition);
            booleanNode.setRightNode(Expression());
            MatchAndRemove(Token.Type.RPAREN);
            MatchAndRemove(Token.Type.EndOfLine);
            return booleanNode;
        }
        else
        {
            throw new Exception();
        }
        
    }
    


    public Node parseTokens() throws Exception
    {
        return FunctionDefinition();
    }
    private ArrayList<Node> parameters() throws Exception
    {
        ArrayList<Node> AllParameterNodes = new ArrayList<Node>();
        if(MatchAndRemove(Token.Type.RPAREN)!= null)
        {
            MatchAndRemove(Token.Type.EndOfLine);
            return AllParameterNodes;
        }
        ArrayList<Node> parameterNode = ParameterNodes();
        while(MatchAndRemove(Token.Type.SEMICOLON)!= null)
        {
            parameterNode.addAll(ParameterNodes());
            AllParameterNodes.addAll(parameterNode);
        }
        return AllParameterNodes;
    }
    private ArrayList<Node> ParameterNodes() throws Exception
    {
        ArrayList<Token> parameterTokens = new ArrayList<>();
        ArrayList<Node> parameterNodes = new ArrayList<>();
        parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
        while(MatchAndRemove(Token.Type.COMMA)!= null)
        {
            parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
        }

        if(MatchAndRemove(Token.Type.COLON)!= null)
        {
            if(MatchAndRemove(Token.Type.INTEGER)!=null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    IntegerNode integerNode = new IntegerNode(0);
                    parameterNodes.add(new VariableNode(Token.Type.INTEGER, false, parameterTokens.get(i).getValue(), integerNode));
                }
            }
            else if(MatchAndRemove(Token.Type.REAL)!= null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    FloatNode floatNode = new FloatNode(0);
                    parameterNodes.add(new VariableNode(Token.Type.DECIMAL, false, parameterTokens.get(i).getValue(), floatNode));
                }
            }
            MatchAndRemove(Token.Type.RPAREN);
            return parameterNodes;
        }
        throw new Exception("Not a valid function defintion");
    }
    
    public Node FunctionDefinition() throws Exception
    {
        List<Node> parameterList = new ArrayList<>();
        List<Node> variableList = new ArrayList<>();
        List<Node> statementsList = new ArrayList<>();
        if(MatchAndRemove(Token.Type.DEFINE)!= null)
        {
            Token TempToken = null;
            if((TempToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
            {
                if(MatchAndRemove(Token.Type.LPAREN)!= null)
                {
                    parameterList = parameters();    
                }
                while(!tokenList.isEmpty())
                {
                    MatchAndRemove(Token.Type.EndOfLine);
                    if(MatchAndRemove(Token.Type.CONSTS)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                        do {
                            Token IdentifierToken = MatchAndRemove(Token.Type.IDENTIFIER);
                            MatchAndRemove(Token.Type.COMMA);
                            if (IdentifierToken != null) {
                                if (MatchAndRemove(Token.Type.EQUALS) != null) {
                                    Token ValueToken;
                                    if ((ValueToken = MatchAndRemove(Token.Type.NUMBER)) != null) {
                                        IntegerNode intNode = new IntegerNode(Integer.parseInt(ValueToken.getValue()));
                                        variableList.add(new VariableNode(Token.Type.INTEGER, true,
                                                IdentifierToken.getValue(), intNode));
                                    } else if ((ValueToken = MatchAndRemove(Token.Type.DECIMAL)) != null) {
                                        FloatNode floatNode = new FloatNode(Float.parseFloat(ValueToken.getValue()));
                                        variableList.add(new VariableNode(Token.Type.DECIMAL, true,
                                                IdentifierToken.getValue(), floatNode));
                                    }
                                }
                            }
                        } while (MatchAndRemove(Token.Type.EndOfLine) != null);

                    }
                    if(MatchAndRemove(Token.Type.VARIABLES)!= null)
                    {
                        ArrayList<Token> variableTokens = new ArrayList<>();
                        MatchAndRemove(Token.Type.EndOfLine);
                        variableTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
                        while(MatchAndRemove(Token.Type.COMMA)!= null)
                        {
                            variableTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
                        }
                        if(MatchAndRemove(Token.Type.COLON)!= null)
                        {
                            if(MatchAndRemove(Token.Type.INTEGER)!=null)
                            {
                                for(int i = 0; i<variableTokens.size(); i++)
                                {
                                    IntegerNode integerNode = new IntegerNode(0);
                                    variableList.add(new VariableNode(Token.Type.INTEGER, false, variableTokens.get(i).getValue(), integerNode));
                                }
                            }
                            else if(MatchAndRemove(Token.Type.REAL)!= null)
                            {
                                for(int i = 0; i<variableTokens.size(); i++)
                                {
                                    FloatNode floatNode = new FloatNode(0);
                                    variableList.add(new VariableNode(Token.Type.DECIMAL, false, variableTokens.get(i).getValue(), floatNode));
                                }
                            }
                        }
                    }
                    if(MatchAndRemove(Token.Type.BEGIN)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                        statementsList.addAll(Statements());
                    }
            
                }
                FunctionNode functionNode = new FunctionNode();
                functionNode.setFunctionName(TempToken.getValue());
                functionNode.setLocalsList(variableList);
                functionNode.setParamsList(parameterList);
                functionNode.setFunctionName(TempToken.getValue());
                functionNode.setStatementList(statementsList);
                return functionNode;
            }
        }
        return null;
    }
    public ArrayList<StatementNode> Statements() throws Exception
    {
        ArrayList<StatementNode> StatementList = new ArrayList<>();
        while(MatchAndRemove(Token.Type.END)== null)
        {
            MatchAndRemove(Token.Type.BEGIN);
            MatchAndRemove(Token.Type.EndOfLine);
            StatementNode tempNode = statement();
            if(tempNode != null)
            {
                StatementList.add(tempNode);
            }   
        }
        return StatementList;
    }

    public StatementNode statement() throws Exception
    {
        StatementNode statementNode = new StatementNode();
        Token leftToken;
        if((leftToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null || (leftToken = MatchAndRemove(Token.Type.NUMBER)) != null || (leftToken = MatchAndRemove(Token.Type.DECIMAL))!= null)
        {
            Token assignToken;
            if((assignToken = MatchAndRemove(Token.Type.ASSIGN))!=null)
            {
                tokenList.add(0,assignToken);
                tokenList.add(0,leftToken);
                statementNode.setStatement(assignment());
                return statementNode;
            }
            Token booleanCondition;
            if((booleanCondition = MatchAndRemove(Token.Type.LESS))!= null || (booleanCondition = MatchAndRemove(Token.Type.GREATER))!= null || (booleanCondition = MatchAndRemove(Token.Type.LESSEQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.GREATEREQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.NOTEQUAL))!= null || (booleanCondition = MatchAndRemove(Token.Type.EQUALS))!= null)
            {
                Token rightToken;
                if((rightToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null || (rightToken = MatchAndRemove(Token.Type.NUMBER)) != null || (rightToken = MatchAndRemove(Token.Type.DECIMAL))!= null)
                {
                    tokenList.add(0,rightToken);
                    tokenList.add(0,booleanCondition);
                    tokenList.add(0,leftToken);
                    BooleanExpression();
                    
                }
            }
        }
        if(MatchAndRemove(Token.Type.WHILE)!= null)
        {
            MatchAndRemove(Token.Type.LPAREN);
            WhileNode whileNode = new WhileNode(BooleanExpression(), Statements());
            statementNode.setStatement(whileNode);
            return statementNode;
        }
        if(MatchAndRemove(Token.Type.REPEAT)!= null)
        {
            MatchAndRemove(Token.Type.LPAREN);
            RepeatNode repeatNode = new RepeatNode(BooleanExpression(), Statements());
            statementNode.setStatement(repeatNode);
            return statementNode;
        }
        if(MatchAndRemove(Token.Type.FOR)!= null)
        {
            //TODO
        }
        // if(MatchAndRemove(Token.Type.IF)!= null)
        // {
        //     //TODO
        //     MatchAndRemove(Token.Type.LPAREN);
        //     IfNode ifNode = new IfNode(BooleanExpression(), Statements());
        //     MatchAndRemove(Token.Type.RPAREN);
        //     MatchAndRemove(Token.Type.EndOfLine);
        //     statementNode.setStatement(ifNode);
        //     return statementNode;
        // }
        return null;
        
    }
    public Node assignment() throws Exception
    {
        AssignmentNode node = new AssignmentNode();
        Token AssignToken;
        if((AssignToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
        {
            if(MatchAndRemove(Token.Type.ASSIGN)!= null)
            {
                node.setName(AssignToken.getValue());
                Token tempToken;
                Node tempNode;
                if((tempToken = MatchAndRemove(Token.Type.NUMBER))!= null)
                {
                    tokenList.add(0,tempToken);
                    tempNode = Expression();
                    node.setNode(tempNode);
                    return node;
                }
                else if((tempToken = MatchAndRemove(Token.Type.DECIMAL))!= null)
                {
                    tokenList.add(0, tempToken);
                    tempNode = Expression();
                    node.setNode(tempNode);
                    return node;
                }
                else if((tempToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
                {
                    node.setNode(new VariableReferenceNode(tempToken.getValue()));
                    return node;

                }
                else if((tempToken = MatchAndRemove(Token.Type.ASSIGN))!= null)
                {
                    //TODO
                }
                else if((tempToken = MatchAndRemove(Token.Type.LPAREN))!= null)
                {
                    tokenList.add(0,tempToken);
                    tempNode = Expression();
                    node.setNode(tempNode);
                    return node;
                }
            }
            
        }
        return null;
    }
    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
