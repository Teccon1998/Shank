public class BooleanNode extends Node{

    private Node LeftNode;
    private Node RightNode;
    private Token Condition;


    public BooleanNode(Node LeftNode, Token Condition, Node RightNode)
    {
        this.LeftNode = LeftNode;
        this.Condition = Condition;
        this.RightNode = RightNode;
    }

    public Node getLeftNode()
    {
        return this.LeftNode;
    }

    public Node getRightNode()
    {
        return this.RightNode;
    }
    public Token getCondition()
    {
        return this.Condition;
    }

    @Override
    public String toString() {
        return "BooleanNode("+ this.LeftNode + ", " + this.Condition + ", " + this.RightNode + ")";
    }
    
}
