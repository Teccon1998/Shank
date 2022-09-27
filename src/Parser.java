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
    
    


    public Node parseTokens() throws Exception
    {
        Node returnNode;
        if((returnNode =FunctionDefinition()) == null)
        {
            returnNode = Expression();
        }
        else
        {
            return null;
        }
        return returnNode;
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
                    parameterNodes.add(new VariableNode(VariableNode.Type.INTEGER, false, parameterTokens.get(i).getValue(), integerNode));
                }
            }
            else if(MatchAndRemove(Token.Type.REAL)!= null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    FloatNode floatNode = new FloatNode(0);
                    parameterNodes.add(new VariableNode(VariableNode.Type.REAL, false, parameterTokens.get(i).getValue(), floatNode));
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
        List<Node> constantList = new ArrayList<>();
        List<Node> statementsList = new ArrayList<>();
        String FunctionName = "";
        if(MatchAndRemove(Token.Type.DEFINE)!= null)
        {
            Token TempToken = null;
            if((TempToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
            {
                if(MatchAndRemove(Token.Type.LPAREN)!= null)
                {
                    parameterList.addAll(parameters());
                }
                while(!tokenList.isEmpty())
                {
                    MatchAndRemove(Token.Type.EndOfLine);
                    if(MatchAndRemove(Token.Type.CONSTS)!= null)
                    {
                        
                        do
                        {
                            Token IdentifierToken = MatchAndRemove(Token.Type.IDENTIFIER);
                            MatchAndRemove(Token.Type.COMMA);
                            if(IdentifierToken!= null)
                            {
                                if(MatchAndRemove(Token.Type.EQUALS)!= null)
                                {
                                    Token ValueToken;
                                    if((ValueToken = MatchAndRemove(Token.Type.NUMBER))!=null)
                                    {
                                        IntegerNode intNode = new IntegerNode(Integer.parseInt(ValueToken.getValue()));
                                        constantList.add(new VariableNode(VariableNode.Type.INTEGER,true, IdentifierToken.getValue(), intNode));
                                    }
                                    else if((ValueToken = MatchAndRemove(Token.Type.DECIMAL))!= null)
                                    {
                                        FloatNode floatNode = new FloatNode(Float.parseFloat(ValueToken.getValue()));
                                        constantList.add(new VariableNode(VariableNode.Type.REAL, true,IdentifierToken.getValue() , floatNode));
                                    }
                                }
                            }
                        }
                        while(MatchAndRemove(Token.Type.EndOfLine)!= null);

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
                                    variableList.add(new VariableNode(VariableNode.Type.INTEGER, false, variableTokens.get(i).getValue(), integerNode));
                                }
                            }
                            else if(MatchAndRemove(Token.Type.REAL)!= null)
                            {
                                for(int i = 0; i<variableTokens.size(); i++)
                                {
                                    FloatNode floatNode = new FloatNode(0);
                                    variableList.add(new VariableNode(VariableNode.Type.REAL, false, variableTokens.get(i).getValue(), floatNode));
                                }
                            }
                        }
                    }
                    if(MatchAndRemove(Token.Type.BEGIN)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                    }
                    if(MatchAndRemove(Token.Type.END)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                    }
                }
                FunctionNode functionNode = new FunctionNode();
                functionNode.setFunctionName(TempToken.getValue());
                functionNode.setLocalsList(variableList);
                functionNode.setParamsList(parameterList);
                functionNode.setFunctionName(TempToken.getValue());
                return functionNode;
            }
            
            
            
        }
        return null;
        // FunctionNode holderFunctionNode = new FunctionNode();
        // List<Node> VariableList = new ArrayList<Node>();
        // while (true)
        // {
        //     Token TempToken;
        //     if (MatchAndRemove(Token.Type.DEFINE) != null) {
        //         Token Value = MatchAndRemove(Token.Type.IDENTIFIER);
        //         if (Value != null) {
        //             String IdentifierName = Value.getValue();
        //             if (MatchAndRemove(Token.Type.LPAREN) != null) {
        //                 String VariableName;
        //                 if ((VariableName = MatchAndRemove(Token.Type.IDENTIFIER).getValue()) != null) {
        //                     if (MatchAndRemove(Token.Type.COLON) != null) {
        //                         VariableNode.Type TypeHolder = null;
        //                         if ((MatchAndRemove(Token.Type.INTEGER)) != null) {
        //                             TypeHolder = VariableNode.Type.INTEGER;
        //                         } else if ((MatchAndRemove(Token.Type.REAL)) != null) {
        //                             TypeHolder = VariableNode.Type.REAL;
        //                         }
        //                         if (MatchAndRemove(Token.Type.SEMICOLON) != null) {
        //                             if (TypeHolder != null) {
        //                                 VariableList.add(new VariableNode(TypeHolder, false, VariableName));
        //                                 holderFunctionNode = tempFuncNode;
        //                             } else {
        //                                 throw new Exception("No TYPE in PARSER");
        //                             }
        //                         }
        //                     }
        //                 }
        //             }
        //         }
        //     } else if ((TempToken = MatchAndRemove(Token.Type.IDENTIFIER)) != null) {
        //         String Tokenname = TempToken.getValue();
        //         if (Tokenname != null) {

        //             if (MatchAndRemove(Token.Type.COLON) != null) {
        //                 VariableNode.Type TypeHolder = null;
        //                 if ((MatchAndRemove(Token.Type.INTEGER)) != null) {
        //                     TypeHolder = VariableNode.Type.INTEGER;
        //                 } else if ((MatchAndRemove(Token.Type.REAL)) != null) {
        //                     TypeHolder = VariableNode.Type.REAL;
        //                 }
        //                 if (MatchAndRemove(Token.Type.SEMICOLON) != null) {
        //                     if (TypeHolder != null) {
        //                         VariableList.add(new VariableNode(TypeHolder, false, Tokenname));
        //                     } else {
        //                         throw new Exception("No TYPE in PARSER");
        //                     }
        //                 } else if (MatchAndRemove(Token.Type.RPAREN) != null) {
        //                     VariableList.add(new VariableNode(TypeHolder, false, Tokenname));
        //                     holderFunctionNode.setParamsList(VariableList);
        //                     MatchAndRemove(Token.Type.EndOfLine);
        //                     return holderFunctionNode;
        //                 }
        //             }

        //         }

        //     }
        //     else
        //     {
        //         throw new Exception("Not parsable");
        //     }
            
        // }


        
        
    }

    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
