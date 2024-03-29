public class Token {
    
    public enum Type {
        EndOfLine, NUMBER, MINUS, PLUS, TIMES, DIVIDE, DECIMAL, LPAREN, RPAREN, IDENTIFIER,DEFINE,INTEGER,REAL,BEGIN,END,SEMICOLON,COLON,EQUALS,COMMA,VARIABLES,ASSIGN,CONSTS,
        IF, THEN, ELSE, ELSIF, FOR, FROM, TO, WHILE, REPEAT, UNTIL, MOD, GREATER, LESS, GREATEREQUAL, LESSEQUAL,
        NOTEQUAL, VAR, TRUE, FALSE, SINGLEQUOTE, DOUBLEQUOTE,STRING, CHAR,BOOLEAN;
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
