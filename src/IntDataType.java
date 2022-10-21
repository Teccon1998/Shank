public class IntDataType extends InterpreterDataType {

    private int IntValue;


    public IntDataType(int IntValue)
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
