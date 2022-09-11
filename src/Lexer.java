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
        if(Input.isBlank())
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
                        } else {
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
                        valueHolderForToken += CurrentCharacter;
                        State = 5;
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
            //     switch (c) {
            //         case '+':
            //             tokenList.add(new Token(Token.Type.PLUS));
            //             continue;
            //         case '-':
            //             tokenList.add(new Token(Token.Type.MINUS));
            //             continue;
            //         case '*':
            //             tokenList.add(new Token(Token.Type.TIMES));
            //             continue;
            //         case '/':
            //             tokenList.add(new Token(Token.Type.DIVIDE));
            //             continue;
            //         case ' ':
            //             if (tokenList.get(tokenList.size()-1).getValue() != null &&tokenList.get(tokenList.size()-1).getValue().charAt(tokenList.get(tokenList.size()-1).getValue().length()-1) == '.')
            //             {
            //                 throw new Exception("Incorrect formatting.");
            //             }
            //             tokenList.add(new Token(Token.Type.SPACE));
            //             continue;
            //     }
            //     //ensure that there's no index out of bounds error
            //     if (i != 0)
            //     {
            //         if (c == '.')
            //         {
            //             //checking edge cases to ignore.
            //             if (tokenList.get(tokenList.size() - 1).getTokenType().equals(Token.Type.DECIMAL)) {
            //                 System.out.println("Illegal input");
            //                 break;
            //             }
            //             if ((i + 1 >= Input.length())) {
            //                 break;
            //             } else { //if all edge cases are clear then remove token and recreate it as a decimal token with the same value and a . added for the next number to be created.
            //                 Token tempToken = tokenList.get(tokenList.size() - 1);
            //                 tokenList.remove(tokenList.size() - 1);
            //                 tokenList.add(new Token(Token.Type.DECIMAL, tempToken.getValue() + "."));
            //             }

            //         }
            //         //add number after a decimal point
            //         else if(Character.isDigit(c) && tokenList.get(tokenList.size()-1).getTokenType().equals(Token.Type.DECIMAL))
            //         {
            //             Token tempToken = tokenList.get(tokenList.size() - 1);
            //             tokenList.remove(tokenList.size() - 1);
            //             tokenList.add(new Token(Token.Type.DECIMAL, tempToken.getValue() + Character.toString(c)));
            //             continue;
            //         }
            //         //greater than 1 digit number detector. This allows for multidigit number input
            //         if (Character.isDigit(c) && tokenList.get(tokenList.size() - 1).getTokenType().equals(Token.Type.NUMBER) &&
            //         tokenList.get(tokenList.size()-1).getTokenType()!= Token.Type.SPACE) 
            //         {
            //             Token tempToken = tokenList.get(tokenList.size() - 1);
            //             tokenList.remove(tokenList.size() - 1);
            //             tokenList.add(new Token(Token.Type.NUMBER, tempToken.getValue() + Character.toString(c)));
            //             continue;
            //         }
            //         else if(Character.isDigit(c) && !tokenList.get(tokenList.size()-1).getTokenType().equals(Token.Type.SPACE) &&
            //         tokenList.get(tokenList.size()-1).getTokenType().equals(Token.Type.MINUS) )
            //         {
            //             tokenList.remove(tokenList.size() - 1);
            //             tokenList.add(new Token(Token.Type.NUMBER, '-' + Character.toString(c)));
            //             continue;
            //         }
            //     }
            //     if(Character.isDigit(c))
            //     {
            //         tokenList.add(new Token(Token.Type.NUMBER, Character.toString(c)));
            //     }

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
