public class VariableReferenceNode extends Node{

    private String VariableName;

    public VariableReferenceNode(){}
    public VariableReferenceNode(String VariableName)
    {
        this.VariableName = VariableName;
    }
    

    public String getVariableName()
    {
        return this.VariableName;
    }
  
    public void setVariableName(String VariableName)
    {
        this.VariableName = VariableName;
    }
    @Override
    public String toString() {
        return "VariableReferenceNode("+ this.VariableName + ")";
        
    }
    
}
