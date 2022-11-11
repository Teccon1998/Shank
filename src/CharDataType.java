public class CharDataType extends InterpreterDataType {

    private char CharValue;

    public CharDataType(char CharValue) {
        this.CharValue = CharValue;
    }
    
    public char getCharValue() {
        return this.CharValue;
    }

    public void setCharValue(char CharValue) {
        this.CharValue = CharValue;
    }

    @Override
    public String toString() {
        return Character.toString(CharValue);
    }

    @Override
    public void fromString(String input) throws Exception {
        if(input.length()>1)
        {
            throw new Exception("Overloading buffer. Cannot have larger than 1 char for string");
        }
        this.CharValue = input.charAt(0);
    }

    
    
}
