public class StringNode extends Node {
    
    private String str;

    public StringNode(String str)
    {
        this.str = str;
    }

    public void setString(String str)
    {
        this.str = str;
    }

    public String getString()
    {
        return this.str;
    }
    @Override
    public String toString()
    {
        return "StringNode(" + this.str + ")";
    }
}
