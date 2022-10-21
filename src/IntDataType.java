public class IntDataType extends InterpreterDataType {

    private int IntValue;


    public IntDataType(int IntValue)
    {
        this.IntValue = IntValue;
    }

    public int getIntValue()
    {
        return this.IntValue;
    }

    public void setIntValue(int IntValue)
    {
        this.IntValue = IntValue;
    }


    @Override
    public String toString() {
        return Integer.toString(IntValue);
    }

    @Override
    public void fromString(String input) {
        this.IntValue = Integer.parseInt(input);
    }
    
}
