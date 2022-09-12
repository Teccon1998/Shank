public abstract class Node {
    
    private Token.Type type;
    private String value;

    public Node(Token.Type type, String value)
    {
        this.type = type;
        this.value = value;

        System.out.println("Node generated");
    }
    
    public abstract Token.Type getType();
    public abstract String getValue();

    public abstract String toString();

    

}
