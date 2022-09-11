public class Token {
    
    public enum Type {
        EndOfLine, NUMBER, MINUS, PLUS, TIMES, DIVIDE, DECIMAL,SPACE
    }
    private Type TokenType;
    private String value = null;

    public Token(Type TokenType) {
        this.TokenType = TokenType;
    }

    public Token(Type TokenType,String value)
    {
        this.TokenType = TokenType;
        this.value = value;
    }

    @Override
    public String toString() {

        if (this.value == null)
        {
            return "Token = " + getTokenType();
        }
        else
        {
            return "Token = " + getTokenType()+ '(' + getValue() + ')';
        }
        
    }

    public Type getTokenType() {
        return this.TokenType;
    }

    public void setToken(Type TokenType) {
        this.TokenType = TokenType;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
