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
        return null;
    }

    public Node Term()
    {
        Token Value = MatchAndRemove(Token.Type.NUMBER);
        if(Value != null)
        {
            if (Value.getValue().contains(".")) {
                return new FloatNode(Float.parseFloat(Value.getValue()));
            } else {
                return new IntegerNode(Integer.parseInt(Value.getValue()));
            }
        }
        return null;
        
    
    }
    public Node Factor()
    {
        return null;
    }


    public Node parseTokens()
    {
        return null;
    }

    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
