public class StringDataType extends InterpreterDataType {

    private String StringValue;

    

    public StringDataType(String String)
    {
        this.StringValue = String;
    }

    public String getStringValue() {
        return this.StringValue;
    }

    public void setStringValue(String StringValue) {
        this.StringValue = StringValue;
    }


    @Override
    public String toString() {
        return StringValue;
    }

    @Override
    public void fromString(String input) {
        this.StringValue = input;
    }
    
}
