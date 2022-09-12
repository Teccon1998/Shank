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
            if (type == tokenList.get(0).getTokenType()) {
                Token returnToken = tokenList.get(0);
                tokenList.remove(0);
                return returnToken;
            }
        }
        return null;

    }

    public Node Expression()
    {
        Node HeadNode = Term();
        
        return HeadNode;
    }

    public Node Term()
    {
        Node Term = Factor();
        return Term;

    }
    public Node Factor()
    {
        Token Value = MatchAndRemove(Token.Type.NUMBER);
        if(Value != null)
        {
            return new IntegerNode(Integer.parseInt(Value.getValue()));
        }
        Value = MatchAndRemove(Token.Type.DECIMAL);
        if(Value != null)
        {
            return new FloatNode(Float.parseFloat(Value.getValue()));
        }
        else
        {
            return null;
        }
    }


    public Node parseTokens()
    {
        Node HeadNode = Expression();
        return HeadNode;
    }

    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
