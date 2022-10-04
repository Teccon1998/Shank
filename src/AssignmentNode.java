public class AssignmentNode extends StatementNode {
    
    private String name;
    private Float floatValue;
    private Integer intValue;

    

    public AssignmentNode() {
        
    }

    public AssignmentNode(String name, Float floatValue)
    {
        this.floatValue = floatValue;
        this.name = name;
    }

    public AssignmentNode(String name, int intValue)
    {
        this.intValue = intValue;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
    

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("AssignmentNode(Name: ");
        sb.append(this.name);
        if(intValue % 1 ==0)
        {
            sb.append(", intValue: ");
            sb.append(this.intValue);
        }
        else
        {
            sb.append(", FloatValue: ");
            sb.append(this.floatValue);
        }
        sb.append(")");
        return sb.toString();
    }
}
