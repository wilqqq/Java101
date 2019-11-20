package dfExceptions;

import value.Value;

public class WrongTypeException extends Exception {
    private static final long serialVersionUID = -7489072632569080238L;
    private int index;
    private Class<? extends Value> valueExpected;
    private Class<? extends Value> actualValue;
    private String colName;

    public WrongTypeException(int index, Class<? extends Value> columnClass, Class<? extends Value> elementClass, String colName){
        this.index=index;
        this.valueExpected=columnClass;
        this.actualValue=elementClass;
        this.colName=colName;
    }

    public int getIndex() {
        return index;
    }

    public Class<? extends Value> getValueExpected() {
        return valueExpected;
    }

    public Class<? extends Value> getActualValue() {
        return actualValue;
    }
    public String getColName(){
        return colName;
    }

    public void printMessage(){
        System.out.println("Invalid operation for column: "+colName+ "\ntypes incompatible in index: "+index+"\n" +
                "Expected type: "+valueExpected.toString()+"\nActual type: "+actualValue.toString());
    }

    public String getStringMessage(){
        return "Invalid operation for column: " + colName + "\ntypes incompatible in index: " + index + "\n" +
                "Expected type: " + valueExpected.toString() + "\nActual type: " + actualValue.toString();
    }

}