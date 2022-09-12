import java.util.*;

public class Lexer {
    
    
    private List<String> LOT;

    
    public Lexer(List<String> ListOfTokens)
    {
        this.LOT = ListOfTokens;
    }

    public List<Token> lex(String Input) throws Exception
    {
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
                    if (Character.isDigit(CurrentCharacter)) {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    } else if (CurrentCharacter == '+' || CurrentCharacter == '-') {
                        valueHolderForToken += CurrentCharacter;
                        State = 2;
                    } else if (CurrentCharacter == '.') {
                        if (valueHolderForToken.contains(".")) {
                            throw new Exception("Incorrect formatting on Iteration " +  i);
                        }
                        valueHolderForToken += CurrentCharacter;
                        State = 6;
                    } else if (CurrentCharacter == ' ') {
                        State = 1;
                    } else {
                        throw new Exception("Incorrect formatting on Iteration " +  i);// I for iteration location for debugging
                    }
                    break;
                case 2:
                    if (CurrentCharacter == '.') {
                        if (valueHolderForToken.contains(".")) {
                            throw new Exception("Incorrect formatting on Iteration " +  i);
                        }
                        valueHolderForToken += CurrentCharacter;
                        State = 6;
                    } else if (Character.isDigit(CurrentCharacter)) {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    } else {
                        throw new Exception("Incorrect formatting on Iteration " +  i);
                    }
                    break;
                case 3:
                    if (Character.isDigit(CurrentCharacter)) {
                        valueHolderForToken += CurrentCharacter;
                        State = 3;
                    } else if (CurrentCharacter == '+' || CurrentCharacter == '-' || CurrentCharacter == '/'
                            || CurrentCharacter == '*') {
                        if (valueHolderForToken.contains(".")) {
                            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        } else {
                            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        }
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
                    } else if (CurrentCharacter == '.') {
                        if (valueHolderForToken.contains(".")) {
                            throw new Exception("Incorrect formatting on Iteration " +  i);
                        } else {
                            valueHolderForToken += CurrentCharacter;
                            State = 4;
                        }
                    }
                    else if (CurrentCharacter == ' ')
                    {
                        if (valueHolderForToken.contains(".")) {
                            throw new Exception("Incorrect formatting on Iteration " +  i);
                        }
                        else if(valueHolderForToken.isEmpty())
                        {
                            State = 5;
                        }
                        else {
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
                    if (Character.isDigit(CurrentCharacter)) {
                        valueHolderForToken += CurrentCharacter;
                        State = 4;
                    } else if (CurrentCharacter == '+' || CurrentCharacter == '-' || CurrentCharacter == '/'
                            || CurrentCharacter == '*') {
                        if (valueHolderForToken.contains(".")) {
                            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        } else {
                            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
                            valueHolderForToken = "";
                            State = 1;
                        }
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
                    } else if (CurrentCharacter == ' ') {
                        tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
                        valueHolderForToken = "";
                        State = 5;
                    } else {
                        throw new Exception("Incorrect formatting on Iteration " +  i);// I for iteration location for debugging
                    }
                    break;
                case 5:
                    if (CurrentCharacter == ' ') {

                        State = 5;
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
            }
        }
        if (valueHolderForToken != null && valueHolderForToken.contains("."))
        {
            tokenList.add(new Token(Token.Type.DECIMAL, valueHolderForToken));
        }
        else if (valueHolderForToken != null)
        {
            tokenList.add(new Token(Token.Type.NUMBER, valueHolderForToken));
        }
        tokenList.add(new Token(Token.Type.EndOfLine));
        // tokenList.removeIf(token -> (token.getTokenType().equals(Token.Type.SPACE))); // spaces detected to understand formatting and removed at this point
        return tokenList;
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