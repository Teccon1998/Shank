import java.util.*;

public class Lexer {
    
    
    private List<String> LOT;

    
    public Lexer(List<String> ListOfTokens)
    {
        this.LOT = ListOfTokens;
    }
    

    public List<Token> lex(String Input) throws Exception
    {
        HashMap<String,Token.Type> reservedWords = new HashMap<>();
        reservedWords.put("IDENTIFIER", Token.Type.IDENTIFIER);
        reservedWords.put("DEFINE",Token.Type.DEFINE);
        reservedWords.put("INTEGER",Token.Type.INTEGER);
        reservedWords.put("REAL",Token.Type.REAL);
        reservedWords.put("BEGIN",Token.Type.BEGIN);
        reservedWords.put("END",Token.Type.END);
        reservedWords.put("VARIABLES", Token.Type.VARIABLES);
        reservedWords.put("CONSTANTS",Token.Type.CONSTS);
        
        List<Token> tokenList = new ArrayList<Token>();
        if(Input.isEmpty())
        {
            tokenList.add(new Token(Token.Type.EndOfLine));
            return tokenList;
        }
        String valueHolderForToken = "";
        int State = 1;

        
        for (int i = 0; i < Input.length(); i++)//parse each character
        {

            char CurrentCharacter = Input.charAt(i);
            switch (State) {
                case 1:
                {
                    if (Character.isDigit(CurrentCharacter)) 
                    {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    } 
                    else if (CurrentCharacter == '+' || CurrentCharacter == '-') 
                    {
                        valueHolderForToken += CurrentCharacter;
                        State = 2;
                    }
                    else if (CurrentCharacter == '.') 
                    {
                        if (valueHolderForToken.contains(".")) {
                            throw new Exception("Incorrect formatting on Iteration " + i);
                        }
                        valueHolderForToken += CurrentCharacter;
                        State = 6;
                    } 
                    else if (CurrentCharacter == ' ') 
                    {
                        State = 1;
                    }
                    else if(Character.isLetter(CurrentCharacter))
                    {
                        State = 7;
                        valueHolderForToken += CurrentCharacter;
                    }
                    else if(CurrentCharacter == ';' || CurrentCharacter == ':' || CurrentCharacter == ',' || CurrentCharacter == '=')
                    {
                        switch (CurrentCharacter) {
                            case ';':
                                tokenList.add(new Token(Token.Type.SEMICOLON));
                                State = 1;
                                break;
                            case ':':
                                tokenList.add(new Token(Token.Type.COLON));
                                State = 1;
                                break;
                            case ',':
                                tokenList.add(new Token(Token.Type.COMMA));
                                State = 1;
                                break;
                            case '=':
                                tokenList.add(new Token(Token.Type.EQUALS));
                                State = 1;
                                break;
                        }
                    }
                    else if(CurrentCharacter == '(')
                    {
                        tokenList.add(new Token(Token.Type.LPAREN));
                    }
                    else 
                    {
                        throw new Exception("Incorrect formatting on Iteration " +  i);// I for iteration location for debugging
                    }
                    break;        
                }
                case 2:
                    if (CurrentCharacter == '.') 
                    {
                        if (valueHolderForToken.contains(".")) 
                        {
                            throw new Exception("Incorrect formatting on Iteration " + i);
                        }
                        valueHolderForToken += CurrentCharacter;
                        State = 6;
                    } 
                    else if (Character.isDigit(CurrentCharacter)) 
                    {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    }
                    else if(CurrentCharacter == '(')
                    {
                        if (!valueHolderForToken.isEmpty())
                        {
                            if (valueHolderForToken.contains("+")) 
                            {
                                tokenList.add(new Token(Token.Type.PLUS));
                                valueHolderForToken = "";
                            }
                            else if(valueHolderForToken.contains("-"))
                            {
                                tokenList.add(new Token(Token.Type.MINUS));
                                valueHolderForToken = "";
                            }
                        }
                        tokenList.add(new Token(Token.Type.LPAREN));
                    }
                    else 
                    {
                        throw new Exception("Incorrect formatting on Iteration " +  i);
                    }
                    break;
                case 3:
                    if (Character.isDigit(CurrentCharacter)) 
                    {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    } 
                    else if (CurrentCharacter == '+' || CurrentCharacter == '-' || CurrentCharacter == '/'|| CurrentCharacter == '*') 
                    {
                        
                        if (valueHolderForToken.contains(".")) {
                            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                            valueHolderForToken = "";
                        }
                        else if(!valueHolderForToken.isEmpty())
                        {
                            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                            valueHolderForToken = "";
                            
                        }
                        switch (CurrentCharacter) 
                            {
                                case '+':
                                    tokenList.add(new Token(Token.Type.PLUS));
                                    break;
                                case '-':
                                    tokenList.add(new Token(Token.Type.MINUS));
                                    break;
                                case '/':
                                    tokenList.add(new Token(Token.Type.DIVIDE));
                                    break;
                                case '*':
                                    tokenList.add(new Token(Token.Type.TIMES));
                                    break;
                            }
                        State = 1;
                    }
                    else if(CurrentCharacter == '(')
                    {
                        if (valueHolderForToken.isEmpty()) {
                            tokenList.add(new Token(Token.Type.LPAREN));
                        } else if (!valueHolderForToken.isEmpty()) {
                            if (tokenList.get(tokenList.size()).equals(new Token(Token.Type.NUMBER))
                                    || tokenList.get(tokenList.size()).equals(new Token(Token.Type.DECIMAL))) {
                                tokenList.add(new Token(Token.Type.TIMES));
                                tokenList.add(new Token(Token.Type.LPAREN));
                            }
                        }
                        State = 1;
                    }
                    else if(CurrentCharacter == ')')
                    {
                        if (valueHolderForToken.isEmpty()) 
                        {
                            tokenList.add(new Token(Token.Type.RPAREN));
                        } 
                        else if (!valueHolderForToken.isEmpty()) 
                        {
                            if (valueHolderForToken.contains(".")) 
                            {
                                tokenList.add(new Token(Token.Type.DECIMAL,valueHolderForToken));
                                tokenList.add(new Token(Token.Type.RPAREN));
                                valueHolderForToken = "";
                            }
                            else
                            {
                                tokenList.add(new Token(Token.Type.NUMBER,valueHolderForToken));
                                tokenList.add(new Token(Token.Type.RPAREN));
                                valueHolderForToken = "";
                            }
                        }
                    }
                    else if (CurrentCharacter == '.') 
                    {
                        if (valueHolderForToken.contains(".")) 
                        {
                            throw new Exception("Incorrect formatting on Iteration " + i);
                        } 
                        else 
                        {
                            valueHolderForToken += CurrentCharacter;
                            State = 4;
                        }
                    }
                    else if (CurrentCharacter == ' ')
                    {
                        if (valueHolderForToken.contains(".")) 
                        {
                            throw new Exception("Incorrect formatting on Iteration " +  i);
                        }
                        else if(valueHolderForToken.isEmpty())
                        {
                            State = 5;
                        }
                        else 
                        {
                            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 5;
                        }
                    }
                    else
                    {
                        throw new Exception("Incorrect formatting on Iteration " +  i);
                    }
                    break;
                case 4:
                    if (Character.isDigit(CurrentCharacter)) 
                    {
                        valueHolderForToken += CurrentCharacter;
                        State = 4;
                    }
                    else if(CurrentCharacter == '(')
                    {
                        if(valueHolderForToken.isEmpty())
                        {
                            tokenList.add(new Token(Token.Type.LPAREN));
                        }
                        else if(!valueHolderForToken.isEmpty())
                        {
                            if (valueHolderForToken.contains(".")) {
                                tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                                valueHolderForToken = "";
                            } else if (isNumeric(valueHolderForToken)) {
                                tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                                valueHolderForToken = "";
                            } 
                        }
                        else
                        {
                            tokenList.add(new Token(Token.Type.LPAREN));
                        }
                        if (tokenList.get(tokenList.size() - 1).getTokenType().equals(Token.Type.NUMBER)|| tokenList.get(tokenList.size() - 1).getTokenType().equals(Token.Type.DECIMAL)) 
                        {
                            tokenList.add(new Token(Token.Type.TIMES));
                            tokenList.add(new Token(Token.Type.LPAREN));
                        }
                        State = 1;
                    }
                    else if (CurrentCharacter == '+' || CurrentCharacter == '-' || CurrentCharacter == '/' || CurrentCharacter == '*') 
                    {
                        if (valueHolderForToken.contains(".")) 
                        {
                            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        } 
                        else 
                        {
                            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        }
                        switch (CurrentCharacter) 
                        {
                            case '+':
                                tokenList.add(new Token(Token.Type.PLUS));
                                break;
                            case '-':
                                tokenList.add(new Token(Token.Type.MINUS));
                                break;
                            case '/':
                                tokenList.add(new Token(Token.Type.DIVIDE));
                                break;
                            case '*':
                                tokenList.add(new Token(Token.Type.TIMES));
                                break;
                        }
                    } 
                    else if (CurrentCharacter == ' ') 
                    {
                        tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                        valueHolderForToken = "";
                        State = 5;
                    } 
                    else 
                    {
                        throw new Exception("Incorrect formatting on Iteration " +  i);// I for iteration location for debugging
                    }
                    break;
                case 5:
                    if (CurrentCharacter == ' ') 
                    {
                        State = 5;
                    }
                    else if(CurrentCharacter == ';' || CurrentCharacter == ':' || CurrentCharacter == ',' || CurrentCharacter == '=')
                    {
                        switch (CurrentCharacter) {
                            case ';':
                                tokenList.add(new Token(Token.Type.SEMICOLON));
                                State = 1;
                                break;
                            case ':':
                                tokenList.add(new Token(Token.Type.COLON));
                                State = 1;
                                break;
                            case ',':
                                tokenList.add(new Token(Token.Type.COMMA));
                                State = 1;
                                break;
                            case '=':
                                tokenList.add(new Token(Token.Type.EQUALS));
                                State = 1;
                                break;
                        }
                    }
                    else if(Character.isDigit(CurrentCharacter))
                    {
                        throw new Exception("Incorrect formatting on Iteration " + i);
                    }
                    else if (CurrentCharacter == '+' || CurrentCharacter == '-' || CurrentCharacter == '/'|| CurrentCharacter == '*') 
                    {
                        switch (CurrentCharacter) 
                        {
                            case '+':
                                tokenList.add(new Token(Token.Type.PLUS));
                                break;
                            case '-':
                                tokenList.add(new Token(Token.Type.MINUS));
                                break;
                            case '/':
                                tokenList.add(new Token(Token.Type.DIVIDE));
                                break;
                            case '*':
                                tokenList.add(new Token(Token.Type.TIMES));
                                break;
                        }
                        State = 1;
                    }
                    break;
                case 6:
                    if (Character.isDigit(CurrentCharacter)) {
                        valueHolderForToken += CurrentCharacter;
                        State = 4;
                    }
                    else
                    {
                        throw new Exception("Incorrect formatting on Iteration " +  i);
                    }
                    break;
                case 7:
                    if(Character.isLetter(CurrentCharacter))
                    {
                        State = 7;
                        valueHolderForToken += CurrentCharacter;
                    }
                    else if(Character.isSpaceChar(CurrentCharacter))
                    {
                        if(reservedWords.containsKey(valueHolderForToken.toUpperCase()))
                        {
                            Token.Type tokenTypeRetrival = reservedWords.get(valueHolderForToken.toUpperCase());
                            reservedWords.put(valueHolderForToken.toUpperCase(),tokenTypeRetrival);
                            tokenList.add(new Token(tokenTypeRetrival));
                            valueHolderForToken ="";
                            State = 1;
                        }
                        else
                        {
                            tokenList.add(new Token(Token.Type.IDENTIFIER,valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        }
                    }
                    else if(CurrentCharacter == ';' || CurrentCharacter == ':' || CurrentCharacter == ',' || CurrentCharacter == '=' || CurrentCharacter == ')')
                    {
                        if (!valueHolderForToken.isEmpty()) 
                        {
                            if (reservedWords.containsKey(valueHolderForToken.toUpperCase())) {
                                Token.Type tokenTypeRetrival = reservedWords.get(valueHolderForToken.toUpperCase());
                                reservedWords.put(valueHolderForToken.toUpperCase(), tokenTypeRetrival);
                                tokenList.add(new Token(tokenTypeRetrival));
                                valueHolderForToken = "";
                                State = 1;
                            } else {
                                tokenList.add(new Token(Token.Type.IDENTIFIER, valueHolderForToken));
                                valueHolderForToken = "";
                                State = 1;
                            }
                        }
                        switch (CurrentCharacter) {
                            case ';':
                                tokenList.add(new Token(Token.Type.SEMICOLON));
                                State = 1;
                                break;
                            case ':':
                                tokenList.add(new Token(Token.Type.COLON));
                                State = 1;
                                break;
                            case ',':
                                tokenList.add(new Token(Token.Type.COMMA));
                                State = 1;
                                break;
                            case '=':
                                tokenList.add(new Token(Token.Type.EQUALS));
                                State = 1;
                                break;
                            case ')':
                                tokenList.add(new Token(Token.Type.RPAREN));
                                State = 1;
                                break;
                        }
                    }
                    else
                    {
                        throw new Exception("Incorrect formatting on Iteration " + i);
                    }
            }
        }
        if (!valueHolderForToken.isEmpty() && valueHolderForToken.contains("."))
        {
            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
        }
        else if(!valueHolderForToken.isEmpty() && reservedWords.containsKey(valueHolderForToken.toUpperCase()))
        {
            if(reservedWords.containsKey(valueHolderForToken.toUpperCase()))
            {
                Token.Type tokenTypeRetrival = reservedWords.get(valueHolderForToken.toUpperCase());
                reservedWords.put(valueHolderForToken.toUpperCase(),tokenTypeRetrival);
                tokenList.add(new Token(tokenTypeRetrival));
                valueHolderForToken ="";
            }
            else
            {
                tokenList.add(new Token(Token.Type.IDENTIFIER,valueHolderForToken));
                valueHolderForToken = "";
            }
        }
        else if (!valueHolderForToken.isEmpty())
        {

            if(isNumeric(valueHolderForToken))
            {
                tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
            }
            else
            {
                tokenList.add(new Token(Token.Type.IDENTIFIER, valueHolderForToken));
            }
            
        }
        tokenList.add(new Token(Token.Type.EndOfLine));
        return tokenList;
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
    

    public List<String> getLOT() 
    {
        return this.LOT;
    }

    public void setLOT(List<String> LOT) 
    {
        this.LOT = LOT;
    }
}