public class BooleanNode extends Node{

    private Node LeftNode;
    private Node RightNode;
    private Token Condition;

    public BooleanNode(){};
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
    public void setLeftNode(Node LeftNode)
    {
        this.LeftNode = LeftNode;
    }

    public Node getRightNode()
    {
        return this.RightNode;
    }
    public void setRightNode(Node RightNode)
    {
        this.RightNode = RightNode;
    }
    public Token getCondition()
    {
        return this.Condition;
    }
    public void setCondition(Token Condition)
    {
        this.Condition = Condition;
    }

    @Override
    public String toString() {
        return "BooleanNode("+ this.LeftNode + ", " + this.Condition + ", " + this.RightNode + ")";
    }
    
}
