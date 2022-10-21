public class FloatDataType extends InterpreterDataType {

    private Float FloatValue;

    public FloatDataType(float FloatValue)
    {
        this.FloatValue = FloatValue;
    }

    @Override
    public String toString() {
        return FloatValue.toString();
    }

    @Override
    public void fromString(String input) {
        Float.parseFloat(input);
    }
    
}
