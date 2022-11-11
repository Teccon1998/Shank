public class BooleanDataType extends InterpreterDataType {


    private boolean booleanValue;

    

    public BooleanDataType(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    

    public boolean getBooleanValue() {
        return this.booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public String toString() {
        if(booleanValue)
        {
            return "TRUE";
        }
        else
        {
            return "FALSE";
        }
    }

    @Override
    public void fromString(String input) throws Exception {
        if(input.equalsIgnoreCase("true"))
        {
            this.booleanValue = true;
        }
        else if(input.equalsIgnoreCase("false"))
        {
            this.booleanValue = false;
        }
        else
        {
            throw new Exception("Cannot set a boolean to anything other than true or false.");
        }
        
    }
    
}
