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
        {}
        else if ((Value = MatchAndRemove(Token.Type.ASSIGN))!= null)
        {
            tokenList.add(0,Value);
            return new VariableNode(Value.getTokenType(), false, Value.getName(),assignment());
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
    
    public BooleanNode BooleanExpression(Token ValOne, Token Condition, Token ValTwo)
    {
        Node NodeOne = null;
        Node NodeTwo = null;
    
        if(ValOne.getTokenType().equals(Token.Type.NUMBER))
        {
            NodeOne = new IntegerNode(Integer.parseInt(ValOne.getValue()));
        }
        else if(ValOne.getTokenType().equals(Token.Type.DECIMAL))
        {
            NodeOne = new FloatNode(Float.parseFloat(ValOne.getValue()));
        }
        else if(ValOne.getTokenType().equals(Token.Type.ASSIGN) || ValOne.getTokenType().equals(Token.Type.IDENTIFIER))
        {
            String name = ValOne.getName();
            if(ValOne.getName() == null)
            {
                name = ValOne.getValue();
            }
            NodeOne = new VariableNode(ValOne.getTokenType(), false, name, assignment());
        }
        if(ValTwo.getTokenType().equals(Token.Type.NUMBER))
        {
            NodeTwo = new IntegerNode(Integer.parseInt(ValTwo.getValue()));
        }
        else if(ValTwo.getTokenType().equals(Token.Type.DECIMAL))
        {
            NodeTwo = new FloatNode(Float.parseFloat(ValTwo.getValue()));
        }
        else if(ValTwo.getTokenType().equals(Token.Type.ASSIGN) || ValTwo.getTokenType().equals(Token.Type.IDENTIFIER))
        {
            String name = ValTwo.getName();
            if(ValTwo.getName() == null)
            {
                name = ValTwo.getValue();
            }
            NodeTwo = new VariableNode(ValTwo.getTokenType(), false, name , assignment());
        }
        BooleanNode boolNode = new BooleanNode(NodeOne, Condition, NodeTwo);
        return boolNode;
    }


    public Node parseTokens() throws Exception
    {
        Node returnNode;
        Token ValueOne = new Token(Token.Type.INTEGER, null);
        Token Condition = new Token(Token.Type.INTEGER, null);
        Token ValueTwo = new Token(Token.Type.INTEGER, null);

        if((ValueOne = MatchAndRemove(Token.Type.NUMBER))!= null || (ValueOne = MatchAndRemove(Token.Type.DECIMAL))!= null ||  (ValueOne = MatchAndRemove(Token.Type.ASSIGN))!= null )
        {
            if((Condition = MatchAndRemove(Token.Type.LESS)) !=null || (Condition = MatchAndRemove(Token.Type.GREATER)) != null|| (Condition = MatchAndRemove(Token.Type.LESSEQUAL)) != null|| (Condition = MatchAndRemove(Token.Type.GREATEREQUAL)) != null|| (Condition = MatchAndRemove(Token.Type.NOTEQUAL))!= null)
            {
                if((ValueTwo = MatchAndRemove(Token.Type.NUMBER))!= null || (ValueTwo = MatchAndRemove(Token.Type.DECIMAL))!= null ||  (ValueTwo = MatchAndRemove(Token.Type.IDENTIFIER))!= null )
                {
                    return BooleanExpression(ValueOne,Condition,ValueTwo);
                }
            }
        }
        
        try
        {
            if(ValueOne.getValue() != null)
            {
                tokenList.add(0, ValueOne);
            }
            if(Condition.getValue() != null)
            {
                tokenList.add(0,Condition);
            }
            if(ValueTwo.getValue() != null)
            {
                tokenList.add(0,ValueTwo);
            }
        }
        catch(Exception e)
        {
            //do nothing just continue.
        }
        if((returnNode =FunctionDefinition()) == null)
        {
            return Expression();

        } else {
            return returnNode;
        }        
    }

    private ArrayList<Node> parameters() throws Exception
    {
        ArrayList<Node> AllParameterNodes = new ArrayList<Node>();
        ArrayList<Node> parameterNode = ParameterNodes();
        while(MatchAndRemove(Token.Type.SEMICOLON)!= null)
        {
            parameterNode.addAll(ParameterNodes());
            AllParameterNodes.addAll(parameterNode);
        }
        if(MatchAndRemove(Token.Type.RPAREN)!= null)
        {
            MatchAndRemove(Token.Type.EndOfLine);
            return AllParameterNodes;
        }
        else return null;
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
                        while(MatchAndRemove(Token.Type.END)== null)
                        {
                            MatchAndRemove(Token.Type.EndOfLine);
                            Node tempNode = statement();
                            if(tempNode != null)
                            {
                                statementsList.add(tempNode);
                            }
                            
                        }

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

    public Node statement() throws Exception
    {
        return assignment();
    }
    public Node assignment()
    {
        AssignmentNode node = new AssignmentNode();
        Token AssignToken;
        if((AssignToken = MatchAndRemove(Token.Type.ASSIGN))!= null)
        {
            node.setName(AssignToken.getName());
            if (isNumeric(AssignToken.getValue())) {
                node.setIntValue(Integer.parseInt(AssignToken.getValue()));
                MatchAndRemove(Token.Type.EndOfLine);
                return node;
            } else {
                node.setFloatValue(Float.parseFloat(AssignToken.getValue()));
                MatchAndRemove(Token.Type.EndOfLine);
                return node;
            }
        }
        return null;
    }
    private boolean isNumeric(String input)
    {
        if (input == null) 
        {
            return false;
        }
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        
    }
    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
