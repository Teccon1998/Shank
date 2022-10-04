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
        reservedWords.put("IF",Token.Type.IF);
        reservedWords.put("THEN",Token.Type.THEN);
        reservedWords.put("ELSE",Token.Type.ELSE);
        reservedWords.put("ELSIF",Token.Type.ELSIF);
        reservedWords.put("FOR",Token.Type.FOR);
        reservedWords.put("FROM",Token.Type.FROM);
        reservedWords.put("TO", Token.Type.TO);
        reservedWords.put("WHILE",Token.Type.WHILE);
        reservedWords.put("REPEAT",Token.Type.REPEAT);
        reservedWords.put("UNTIL",Token.Type.UNTIL);
        reservedWords.put("MOD",Token.Type.MOD);
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
                    else if(CurrentCharacter == '=')
                    {
                        tokenList.add(new Token(Token.Type.EQUALS));
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
                    else if(CurrentCharacter == '*')
                    {
                        if(tokenList.get(tokenList.size()-1).getTokenType().equals(Token.Type.LPAREN))
                        {
                            tokenList.remove(tokenList.size() - 1);
                            valueHolderForToken += CurrentCharacter;
                            State = 8;
                        }
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
                            if (tokenList.get(tokenList.size() - 1).equals(new Token(Token.Type.NUMBER))
                                    || tokenList.get(tokenList.size() - 1).equals(new Token(Token.Type.DECIMAL))) {
                                tokenList.add(new Token(Token.Type.TIMES));
                                tokenList.add(new Token(Token.Type.LPAREN));
                            }
                        }
                        if (isNumeric(valueHolderForToken))
                        {
                            tokenList.add(new Token(Token.Type.LPAREN));
                            valueHolderForToken = "";
                        }
                        State = 1;
                        
                    }
                    else if(CurrentCharacter == '*')
                    {
                        if(tokenList.get(tokenList.size()-1).getTokenType().equals(Token.Type.LPAREN))
                        {
                            tokenList.remove(tokenList.size()-1);
                            State = 8;
                        }
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
                        switch (CurrentCharacter) {
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
                    else if (CurrentCharacter == '(')
                    {
                        State = 8;
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
                        break;
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
                        break;
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
                    else if(CurrentCharacter == '(')
                    {
                        State = 8;
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
                        break;
                    }
                    else
                    {
                        throw new Exception("Incorrect formatting on Iteration " + i);
                    }
                case 8:
                    if(CurrentCharacter == '*')
                    {
                        valueHolderForToken += '*';
                    }
                    if(CurrentCharacter == ')')
                    {
                        if(valueHolderForToken.indexOf("*")!= -1)
                        {
                            valueHolderForToken = valueHolderForToken.substring(0, valueHolderForToken.length()-2);
                            State =1;
                            if(!valueHolderForToken.isEmpty())
                            {
                                try
                                {
                                    if(isNumeric(valueHolderForToken))
                                    {
                                        Integer.parseInt(valueHolderForToken);
                                        tokenList.add(new Token(Token.Type.NUMBER,valueHolderForToken));
                                        valueHolderForToken = "";
                                    }
                                    else
                                    {
                                        Float.parseFloat(valueHolderForToken);
                                        tokenList.add(new Token(Token.Type.DECIMAL,valueHolderForToken));
                                        valueHolderForToken = "";
                                    }
                                }
                                catch(Exception e)
                                {
                                    tokenList.add(new Token(Token.Type.IDENTIFIER, valueHolderForToken));
                                    valueHolderForToken = "";
                                }
                                                                
                            }
                        }
                    }
                    break;

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
        try
        {
            for (int i = 0; i <tokenList.size(); i++) 
            {
                if(tokenList.get(i).getTokenType().equals(Token.Type.EQUALS))
                {
                    if(tokenList.get(i-2).getTokenType().equals(Token.Type.IDENTIFIER) && tokenList.get(i-1).getTokenType().equals(Token.Type.COLON) && (tokenList.get(i+1).getTokenType().equals(Token.Type.DECIMAL) || tokenList.get(i+1).getTokenType().equals(Token.Type.NUMBER) || tokenList.get(i+1).getTokenType().equals(Token.Type.IDENTIFIER)))
                    {
                        Token AssignToken = new Token(Token.Type.ASSIGN,tokenList.get(i+1).getValue(),tokenList.get(i-2).getValue());
                        
                        tokenList.remove(i);
                        tokenList.add(i, AssignToken);
                        tokenList.remove(i+1);
                        tokenList.remove(i-2);
                        tokenList.remove(i-2);
                    }
                }
            }
        }catch (Exception e)
        {
            //do nothing
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