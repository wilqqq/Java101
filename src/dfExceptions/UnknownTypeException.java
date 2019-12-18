package dfExceptions;

public class UnknownTypeException extends Exception {
    private static final long serialVersionUID = -7489072332569080238L;
    private int index;
    private String type;
    private String colName;

    public UnknownTypeException(int index, String elementType, String colName){
        this.index=index;
        this.type =elementType;
        this.colName=colName;
    }

    public int getIndex() {
        return index;
    }
    public String getType() {
        return type;
    }
    public String getColName(){
        return colName;
    }

    public void printMessage(){
        System.out.println(getStringMessage());
    }

    public String getStringMessage(){
        return "Invalid operation for column: " + colName + "\nunknown type at index: " + index + "\n" +
                "\ngot type: " + type;
    }

}