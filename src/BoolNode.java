public class BoolNode extends Node{
    
    private boolean bool;


    public BoolNode(boolean bool)
    {
        this.bool = bool;
    }

    public boolean getBool()
    {
        return this.bool;
    }
    public void setBool(boolean bool)
    {
        this.bool = bool;
    }

    @Override
    public String toString()
    {
        return "BoolASTNode("+this.bool+")";
    }
}
