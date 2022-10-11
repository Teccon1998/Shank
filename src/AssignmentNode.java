public class AssignmentNode extends StatementNode {
    
    private String name;
    private Node node;

    

    public AssignmentNode() {
        
    }

    public AssignmentNode(String name, Node node)
    {
        this.node = node;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getNode()
    {
        return this.node;
    }
    public void setNode(Node node)
    {
        this.node = node;
    }
    @Override
    public String toString()
    {
        return "AssignmentNode(NAME:" + this.name + ", " + this.node + ")";
    }
}
