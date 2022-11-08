public class CharNode extends Node {
    
    private char c;

    public CharNode(char c)
    {
        this.c = c;
    }

    public char getChar()
    {
        return this.c;
    }

    public void setChar(char c)
    {
        this.c=c;
    }

    public String toString()
    {
        return "CharNode("+this.c+")";
    }
}
