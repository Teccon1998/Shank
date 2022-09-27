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
        if(MatchAndRemove(Token.Type.DEFINE)!= null)
        {
            FunctionDefinition();
        }
        Node HeadNode = Expression();
        if (HeadNode == null) {
            System.out.println("Expression has returned null");
        }
        return HeadNode;
    }
    
    public List<Node> FunctionDefinition() throws Exception
    {
        Token Value = MatchAndRemove(Token.Type.IDENTIFIER);
        String IdentifierName = Value.getValue();
        MatchAndRemove(Token.Type.LPAREN);
        String VariableName;
        List<Node> VariableList = new ArrayList<Node>();
        if((VariableName = MatchAndRemove(Token.Type.IDENTIFIER).getValue()) != null)
        {
            MatchAndRemove(Token.Type.COLON);
            VariableNode.Type TypeHolder = null;
            if((MatchAndRemove(Token.Type.INTEGER))!= null)
            {
                TypeHolder = VariableNode.Type.INTEGER;
            }
            else if((MatchAndRemove(Token.Type.REAL))!= null)
            {
                TypeHolder = VariableNode.Type.REAL;
            }
            if(MatchAndRemove(Token.Type.SEMICOLON)!= null)
            {
                if (TypeHolder != null)
                {
                    VariableList.add(new VariableNode(TypeHolder, false, VariableName));
                }
                else
                {
                    throw new Exception("No TYPE in PARSER");
                }
            }
            else if(MatchAndRemove(Token.Type.RPAREN)!= null)
            {

            }
        }
        
        
        return VariableList;
    }

    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
