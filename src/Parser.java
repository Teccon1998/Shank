import java.beans.Expression;
import java.util.*;

public class Parser {
    
    private List<Token> tokenList;

    public Parser(List<Token> tokenList)
    {
        this.tokenList = tokenList;
    }
    public ArrayList<FunctionDefinitionNode> parseTokens() throws Exception
    {
        //
        ArrayList<FunctionDefinitionNode> FunctionList = new ArrayList<>();
        while(tokenList.size() != 0)
        {
            FunctionDefinitionNode node = FunctionDefinition();
            if(node != null)
            {
                FunctionList.add(node);
            }
        }
        return FunctionList;
    }

    private Token MatchAndRemove(Token.Type type)
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
    /*--------------- */

    private Node Expression() throws Exception
    {
        BooleanNode booleanNode = new BooleanNode();
        Token Value;
        Node FirstNode = Term();
        Node SecondNode = null;
        
        if (MatchAndRemove(Token.Type.PLUS) != null) 
        {
            SecondNode = Expression();
            return new MathOpNode(FirstNode, SecondNode, MathOpNode.Operator.ADD);
        } 
        else if ((MatchAndRemove(Token.Type.MINUS) != null)) 
        {
            SecondNode = Expression();
            return new MathOpNode(FirstNode, SecondNode, MathOpNode.Operator.SUBTRACT);
        } 
        else if ((Value = MatchAndRemove(Token.Type.LESS)) != null
            || (Value = MatchAndRemove(Token.Type.GREATER)) != null
            || (Value = MatchAndRemove(Token.Type.LESSEQUAL)) != null
            || (Value = MatchAndRemove(Token.Type.GREATEREQUAL)) != null
            || (Value = MatchAndRemove(Token.Type.NOTEQUAL)) != null
                || (Value = MatchAndRemove(Token.Type.EQUALS)) != null) 
        {
            booleanNode.setLeftNode(FirstNode);
            booleanNode.setCondition(Value);
            booleanNode.setRightNode(Expression());
            return booleanNode;
        }
        else if (FirstNode != null) 
        {
            return FirstNode;
        } 
        else 
        {
            throw new Exception("Not an expression");
        }
    }
    private Node Term() throws Exception
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
    private Node Factor() throws Exception
    {
        Token Value;
        Node Factor;
        if ((Value = MatchAndRemove(Token.Type.NUMBER)) != null) {
            return new IntegerNode(Integer.parseInt(Value.getValue()));
        } else if ((Value = MatchAndRemove(Token.Type.DECIMAL)) != null) {
            return new FloatNode(Float.parseFloat(Value.getValue()));
        } else if ((Value = MatchAndRemove(Token.Type.IDENTIFIER)) != null) {
            return new VariableReferenceNode(Value.getValue());
        } else if (MatchAndRemove(Token.Type.LPAREN) != null) {
            Factor = Expression();
            if (MatchAndRemove(Token.Type.RPAREN) == null) {
                throw new Exception("No Closing Paren");
            } else {
                return Factor;
            }
        }
        else if(MatchAndRemove(Token.Type.TRUE)!=null)
        {
            return new BoolNode(true);
        }
        else if(MatchAndRemove(Token.Type.FALSE)!=null)
        {
            return new BoolNode(false);
        }
        else if(tokenList.get(0).getTokenType().equals(Token.Type.STRING))
        {
            return new StringNode(MatchAndRemove(Token.Type.STRING).getValue());
        }
        else if(tokenList.get(0).getTokenType().equals(Token.Type.CHAR))
        {
            String c = MatchAndRemove(Token.Type.CHAR).getValue();
            if(c.length() > 1)
            {
                throw new Exception("Character is longer than 1. Should never reach this exception.");
            }
            return new CharNode(c.charAt(0));
        }
        else
        {
            throw new Exception("NonValidDataType Exception");
        }
        
    }
/*---------------------- */
    private BooleanNode BooleanExpression() throws Exception
    {
        Node node = Expression();
        if(node instanceof BooleanNode)
        {
            return (BooleanNode) node;
        }
        throw new Exception("Boolean Exception");
    }
    

    
    private ArrayList<VariableNode> parameters() throws Exception
    {
        ArrayList<VariableNode> AllParameterNodes = new ArrayList<VariableNode>();
        if (MatchAndRemove(Token.Type.RPAREN) != null) {
            MatchAndRemove(Token.Type.EndOfLine);
            return AllParameterNodes;
        }
        ArrayList<VariableNode> parameterNode = ParameterNodes();
        while (MatchAndRemove(Token.Type.SEMICOLON) != null) {
            parameterNode.addAll(ParameterNodes());
            AllParameterNodes.addAll(parameterNode);
        }
        return AllParameterNodes;
    }
    
    private ArrayList<VariableNode> ParameterNodes() throws Exception
    {
        ArrayList<VariableNode> parameterNodes = new ArrayList<>();
        ArrayList<Token> parameterTokens = new ArrayList<>();
        
        //Do this to check if the next value after a function open paren is not var.
        if(tokenList.get(0).getTokenType().equals(Token.Type.IDENTIFIER))
        {
            parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
        }
        else
        {
            parameterTokens.add(MatchAndRemove(Token.Type.VAR));
            parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
        }
        while(MatchAndRemove(Token.Type.COMMA)!= null)
        {
            if (MatchAndRemove(Token.Type.VAR) != null)
            {
                parameterTokens.add(new Token(Token.Type.VAR));
                parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
            }
            else
            {
                parameterTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
            }
            
                        
        }

        if(MatchAndRemove(Token.Type.COLON)!= null)
        {
            if(MatchAndRemove(Token.Type.INTEGER)!=null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    if(parameterTokens.get(i).getTokenType().equals(Token.Type.VAR))
                    {

                        IntegerNode integerNode = new IntegerNode(0);
                        if(i == parameterTokens.size())
                        {
                            break;
                        }
                        i++;
                        parameterNodes.add(new VariableNode(VariableNode.Type.INTEGER, false, parameterTokens.get(i).getValue(), integerNode));
                    }
                    else
                    {
                        IntegerNode integerNode = new IntegerNode(0);
                        parameterNodes.add(new VariableNode(VariableNode.Type.INTEGER, true, parameterTokens.get(i).getValue(), integerNode));
                    }
                }
            }
            else if(MatchAndRemove(Token.Type.REAL)!= null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    if(parameterTokens.get(i).getTokenType().equals(Token.Type.VAR))
                    {
                        if(i == parameterTokens.size())
                        {
                            break;
                        }
                        i++;
                        FloatNode floatNode = new FloatNode(0);
                        parameterNodes.add(new VariableNode(VariableNode.Type.REAL, false, parameterTokens.get(i).getValue(), floatNode));
                    }
                    else
                    {
                        FloatNode floatNode = new FloatNode(0);
                        parameterNodes.add(new VariableNode(VariableNode.Type.REAL, true, parameterTokens.get(i).getValue(), floatNode));
                    }
                }
            }
            else if(MatchAndRemove(Token.Type.BOOLEAN)!= null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    if(parameterTokens.get(i).getTokenType().equals(Token.Type.VAR))
                    {
                        if(i == parameterTokens.size())
                        {
                            break;
                        }
                        i++;
                        BoolNode boolNode = new BoolNode(false);
                        parameterNodes.add(new VariableNode(VariableNode.Type.BOOLEAN, false, parameterTokens.get(i).getValue(), boolNode));
                    }             
                    else
                    {
                        BoolNode boolNode = new BoolNode(false);
                        parameterNodes.add(new VariableNode(VariableNode.Type.BOOLEAN, true, parameterTokens.get(i).getValue(), boolNode));
                    }       
                }
            }
            else if(MatchAndRemove(Token.Type.STRING)!=null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    if(parameterTokens.get(i).getTokenType().equals(Token.Type.VAR))
                    {
                        if(i == parameterTokens.size())
                        {
                            break;
                        }
                        i++;
                        StringNode stringNode = new StringNode("");
                        parameterNodes.add(new VariableNode(VariableNode.Type.STRING, false,parameterTokens.get(i).getValue(), stringNode));
                    }
                    else
                    {
                        
                        StringNode stringNode = new StringNode("");
                        parameterNodes.add(new VariableNode(VariableNode.Type.STRING, true,parameterTokens.get(i).getValue(), stringNode));
                    }
                }
            }
            else if(MatchAndRemove(Token.Type.CHAR)!= null)
            {
                for(int i = 0; i<parameterTokens.size(); i++)
                {
                    if(parameterTokens.get(i).getTokenType().equals(Token.Type.VAR))
                    {
                        if(i == parameterTokens.size())
                        {
                            break;
                        }
                        CharNode charNode = new CharNode('\0');
                        parameterNodes.add(new VariableNode(VariableNode.Type.CHAR, false, parameterTokens.get(i).getValue(), charNode));
                    }
                    else
                    {
                        CharNode charNode = new CharNode('\0');
                        parameterNodes.add(new VariableNode(VariableNode.Type.CHAR, true, parameterTokens.get(i).getValue(), charNode));
                    }
                }
            }
        }
        return parameterNodes;
        
    }

    
    private FunctionDefinitionNode FunctionDefinition() throws Exception
    {
        ArrayList<VariableNode> parameterList = new ArrayList<>();
        ArrayList<VariableNode> variableList = new ArrayList<>();
        ArrayList<StatementNode> statementsList = new ArrayList<>();
        if(MatchAndRemove(Token.Type.DEFINE)!= null)
        {
            Token TempToken = null;
            if((TempToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
            {
                if(MatchAndRemove(Token.Type.LPAREN)!= null)
                {
                    parameterList = parameters();
                    MatchAndRemove(Token.Type.RPAREN);   
                }
                while(!tokenList.isEmpty())
                {
                    while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                    }
                    if(MatchAndRemove(Token.Type.CONSTS)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                        do {
                            Token IdentifierToken = MatchAndRemove(Token.Type.IDENTIFIER);
                            MatchAndRemove(Token.Type.COMMA);
                            if (IdentifierToken != null) {
                                if (MatchAndRemove(Token.Type.EQUALS) != null) {
                                    Token ValueToken;
                                    if ((ValueToken = MatchAndRemove(Token.Type.NUMBER)) != null) 
                                    {
                                        IntegerNode intNode = new IntegerNode(Integer.parseInt(ValueToken.getValue()));
                                        variableList.add(new VariableNode(VariableNode.Type.INTEGER, true,IdentifierToken.getValue(), intNode));
                                    } 
                                    else if ((ValueToken = MatchAndRemove(Token.Type.DECIMAL)) != null) 
                                    {
                                        FloatNode floatNode = new FloatNode(Float.parseFloat(ValueToken.getValue()));
                                        variableList.add(new VariableNode(VariableNode.Type.REAL, true,IdentifierToken.getValue(), floatNode));
                                    }
                                    else if((ValueToken = MatchAndRemove(Token.Type.STRING))!= null)
                                    {
                                        StringNode stringNode = new StringNode(ValueToken.getValue());
                                        variableList.add(new VariableNode(VariableNode.Type.STRING, true, IdentifierToken.getValue(), stringNode));
                                    }
                                    else if((ValueToken = MatchAndRemove(Token.Type.CHAR))!= null)
                                    {
                                        if(ValueToken.getValue().length() >1 )
                                        {
                                            throw new Exception("not a char value. Should never reach here.");
                                        }
                                        CharNode charNode = new CharNode(ValueToken.getValue().charAt(0));
                                        variableList.add(new VariableNode(VariableNode.Type.CHAR, true, IdentifierToken.getValue(), charNode));
                                    }
                                }
                            }
                            while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
                            {
                                MatchAndRemove(Token.Type.EndOfLine);
                            }
                            // if (tokenList.get(1).getTokenType().equals(Token.Type.IDENTIFIER))
                            // {
                            //     MatchAndRemove(Token.Type.EndOfLine);
                            //     tokenList.add(0,new Token(Token.Type.SEMICOLON));
                            // }
                        } while (MatchAndRemove(Token.Type.EndOfLine) != null);

                    }
                    if(MatchAndRemove(Token.Type.VARIABLES)!= null)
                    {
                        MatchAndRemove(Token.Type.EndOfLine);
                        if (MatchAndRemove(Token.Type.BEGIN) != null)
                        {
                            tokenList.add(0, new Token(Token.Type.BEGIN));
                            continue;
                        }

                        ArrayList<Token> TotalvariableTokens = new ArrayList<>();
                        do {
                            ArrayList<Token> variableTokens = new ArrayList<>();
                            variableTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
                            while (MatchAndRemove(Token.Type.COMMA) != null) {
                                variableTokens.add(MatchAndRemove(Token.Type.IDENTIFIER));
                            }
                            if (MatchAndRemove(Token.Type.COLON) != null) {
                                if (MatchAndRemove(Token.Type.INTEGER) != null) {
                                    for (int i = 0; i < variableTokens.size(); i++) {
                                        IntegerNode integerNode = new IntegerNode(0);
                                        variableList.add(new VariableNode(VariableNode.Type.INTEGER, false,
                                                variableTokens.get(i).getValue(), integerNode));
                                    }
                                } else if (MatchAndRemove(Token.Type.REAL) != null) {
                                    for (int i = 0; i < variableTokens.size(); i++) {
                                        FloatNode floatNode = new FloatNode(0);
                                        variableList.add(new VariableNode(VariableNode.Type.REAL, false,
                                                variableTokens.get(i).getValue(), floatNode));
                                    }
                                } else if (MatchAndRemove(Token.Type.BOOLEAN) != null) {
                                    for (int i = 0; i < variableTokens.size(); i++) {
                                        BoolNode boolNode = new BoolNode(false);
                                        variableList.add(new VariableNode(VariableNode.Type.BOOLEAN, false,
                                                variableTokens.get(i).getValue(), boolNode));
                                    }
                                } else if (MatchAndRemove(Token.Type.STRING) != null) {
                                    for (int i = 0; i < variableTokens.size(); i++) {
                                        StringNode stringNode = new StringNode("");
                                        variableList.add(new VariableNode(VariableNode.Type.STRING, false,
                                                variableTokens.get(i).getValue(), stringNode));
                                    }
                                } else if (MatchAndRemove(Token.Type.CHAR) != null) {
                                    for (int i = 0; i < variableTokens.size(); i++){
                                        CharNode charNode = new CharNode('\0');
                                        variableList.add(new VariableNode(VariableNode.Type.CHAR,false,
                                                variableTokens.get(i).getValue(), charNode));
                                    }
                                }
                            }
                            //checking if variables are on another line.
                            if (tokenList.get(1).getTokenType().equals(Token.Type.IDENTIFIER))
                            {
                                MatchAndRemove(Token.Type.EndOfLine);
                                tokenList.add(0, new Token(Token.Type.SEMICOLON));
                            }
                            TotalvariableTokens.addAll(variableTokens);
                        } while (MatchAndRemove(Token.Type.SEMICOLON) != null);
                        while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
                        {
                            MatchAndRemove(Token.Type.EndOfLine);
                        }
                    }
                    
                    if(MatchAndRemove(Token.Type.BEGIN)!= null)
                    {
                        statementsList.addAll(Statements());
                    }
                    Token DefineToken;
                    if((DefineToken = MatchAndRemove(Token.Type.DEFINE))!= null)
                    {
                        tokenList.add(0, DefineToken);
                        break;
                    }
                }
                FunctionDefinitionNode functionDefinitionNode = new FunctionDefinitionNode
                (
                    TempToken.getValue(), 
                    statementsList,
                    parameterList,
                    variableList
                );
                return functionDefinitionNode;
            }
        }
        return null;
    }
    private ArrayList<StatementNode> Statements() throws Exception
    {
        ArrayList<StatementNode> StatementList = new ArrayList<>();
        while(MatchAndRemove(Token.Type.END)== null)
        {
            MatchAndRemove(Token.Type.BEGIN);
            StatementNode tempNode = statement();
            if (tempNode != null) {
                StatementList.add(tempNode);
            } else if (tempNode == null) {
                //Do nothing part of normal flow
            }
        }

        while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
        {
            MatchAndRemove(Token.Type.EndOfLine);
            if(tokenList.isEmpty())
                break;
        }

        return StatementList;
    }




    private StatementNode statement() throws Exception
    {
        StatementNode statementNode = new StatementNode();
        while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
        {
            MatchAndRemove(Token.Type.EndOfLine);
        }
        Token leftToken;
        if((leftToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
        {
            Token assignToken;
            if((assignToken = MatchAndRemove(Token.Type.ASSIGN))!=null)
            {
                tokenList.add(0, assignToken);
                tokenList.add(0, leftToken);
                statementNode.setStatement(assignment());
                return statementNode;
            }
            if(MatchAndRemove(Token.Type.EndOfLine)!= null)
            {
                FunctionCallNode functionCallNode = new FunctionCallNode(leftToken.getValue(), null);
                statementNode.setStatement(functionCallNode);
                return statementNode;
            }
            else
            {
                ArrayList<ParameterNode> parameterFunctionNodes = new ArrayList<>();
                do
                {
                    Token HolderToken;
                    if((HolderToken = MatchAndRemove(Token.Type.NUMBER))!= null)
                    {
                        tokenList.add(0,HolderToken);
                        parameterFunctionNodes
                                .add(new ParameterNode(null,Expression()));
                    }
                    else if((HolderToken = MatchAndRemove(Token.Type.DECIMAL))!= null)
                    {
                        tokenList.add(0,HolderToken);
                        parameterFunctionNodes
                                .add(new ParameterNode(null,Expression()));
                    }
                    else if((HolderToken = MatchAndRemove(Token.Type.VAR))!= null)
                    {
                        Token VariableReferenceToken;
                        if((VariableReferenceToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
                        {
                            parameterFunctionNodes
                                .add(new ParameterNode(new VariableReferenceNode(VariableReferenceToken.getValue()),
                                        null));
                        }
                        else
                        {
                            throw new Exception("No value after VAR");
                        }
                        
                    }
                    else if((HolderToken = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
                    {
                        tokenList.add(0, HolderToken);
                        parameterFunctionNodes
                                .add(new ParameterNode(new VariableReferenceNode(HolderToken.getValue()), null));
                        MatchAndRemove(Token.Type.IDENTIFIER);
                    }
                }
                while (MatchAndRemove(Token.Type.COMMA) != null);
                FunctionCallNode functionCallNode = new FunctionCallNode(leftToken.getValue(), parameterFunctionNodes);
                statementNode.setStatement(functionCallNode);
                return statementNode;
            }
        }
        if(MatchAndRemove(Token.Type.WHILE)!= null)
        {
            WhileNode whileNode = new WhileNode(BooleanExpression(), Statements());
            statementNode.setStatement(whileNode);
            return statementNode;
        }
        if(MatchAndRemove(Token.Type.REPEAT)!= null)
        {
            RepeatNode repeatNode = new RepeatNode(BooleanExpression(), Statements());
            statementNode.setStatement(repeatNode);
            return statementNode;
        }
        if(MatchAndRemove(Token.Type.FOR)!= null)
        {
            Token IdentifierVariableReference;
            if((IdentifierVariableReference = MatchAndRemove(Token.Type.IDENTIFIER))!= null)
            {
                if(IdentifierVariableReference != null)
                {
                    VariableReferenceNode varRefNode = new VariableReferenceNode(IdentifierVariableReference.getValue());
                    if(MatchAndRemove(Token.Type.FROM)!= null)
                    {
                        Node StartNode = Expression();
                        if(StartNode != null)
                        {
                            if(MatchAndRemove(Token.Type.TO)!=null)
                            {
                                Node EndNode = Expression();
                                if(EndNode != null)
                                {
                                    MatchAndRemove(Token.Type.EndOfLine);
                                    ForNode forNode = new ForNode(varRefNode,StartNode, EndNode, Statements());
                                    statementNode.setStatement(forNode);
                                    return statementNode;
                                }
                            }
                        }
                    }
                    
                }
                
            }
            throw new Exception("Improper for node");
        }
        if( MatchAndRemove(Token.Type.IF)!= null)
        {
            IfNode ifNode = new IfNode(BooleanExpression(), null);
            MatchAndRemove(Token.Type.THEN);
            MatchAndRemove(Token.Type.EndOfLine);
            ifNode.setStatements(Statements());
            while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
            {
                MatchAndRemove(Token.Type.EndOfLine);
            }
            if (MatchAndRemove(Token.Type.ELSIF) != null) 
            {
                IfNode elsifNode = new IfNode((BooleanNode) Expression(), null);
                MatchAndRemove(Token.Type.THEN);
                MatchAndRemove(Token.Type.EndOfLine);
                elsifNode.setStatements(Statements());
                ifNode.setElseNode(elsifNode);
                while(tokenList.get(0).getTokenType() == Token.Type.EndOfLine)
                {
                    MatchAndRemove(Token.Type.EndOfLine);
                }
            }
            if (MatchAndRemove(Token.Type.ELSE) != null) 
            {
                MatchAndRemove(Token.Type.EndOfLine);
                ifNode.setElseNode(new ElseNode(Statements()));
                MatchAndRemove(Token.Type.EndOfLine);

            }
            statementNode.setStatement(ifNode);
            return statementNode;
        }
        return null;
    }
    private AssignmentNode assignment() throws Exception
    {
        Token AssignToken;
        if ((AssignToken = MatchAndRemove(Token.Type.IDENTIFIER)) != null) 
        {
            if (MatchAndRemove(Token.Type.ASSIGN) != null) 
            {
                Token ValueFindToken;
                VariableReferenceNode variableReferenceNode = new VariableReferenceNode(AssignToken.getValue());

                if ((ValueFindToken = MatchAndRemove(Token.Type.LPAREN))!= null 
                        ||(ValueFindToken = MatchAndRemove(Token.Type.NUMBER)) != null 
                        ||(ValueFindToken = MatchAndRemove(Token.Type.DECIMAL)) != null 
                        ||(ValueFindToken = MatchAndRemove(Token.Type.IDENTIFIER)) != null
                        ||(ValueFindToken = MatchAndRemove(Token.Type.TRUE)) != null
                        ||(ValueFindToken = MatchAndRemove(Token.Type.FALSE)) != null
                        ||(ValueFindToken = MatchAndRemove(Token.Type.STRING)) != null
                        ||(ValueFindToken = MatchAndRemove(Token.Type.CHAR))!= null) 
                {
                    tokenList.add(0, ValueFindToken);
                    AssignmentNode AssignmentNode = new AssignmentNode(variableReferenceNode, Expression());
                    return AssignmentNode;
                } 
            }
        }
        throw new Exception("Not a valid assignment");
    }
    
    
    
    
    
    
    
    
    
    
    
    public List<Token> getTokenList() {
        return this.tokenList;
    }
    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
