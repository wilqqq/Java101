package data;

//array list -> faster, like vector but not synchronised 
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Data {
    private String type;
    private ArrayList<Object> data;
    private String name;
    public Data (String name,String dataType){
        this.name = name;
        this.type = dataType.toLowerCase();
        switch ( this.type ) {
            //cant use primitive in generic type, must use wrapper class
            //use instanceof to check if it's a int 
            case "int":
            data = new ArrayList<Integer>();
            break;
            case "double":
            data = new ArrayList<Double>();
            break;
            case "string":
            data = new ArrayList<String>();
            break;
            default:
            throw new Error("NOT A KNOWN TYPE");
        }
    }
    public Data (Data copy) {
        this(copy.name, copy.type);
        this.data = copy.data;
    }
    public int size() { return data.size();}
    public void set(Object element, int index) {
        switch ( type ) {
            //cant use primitive in generic type, must use wrapper class
            //use instanceof to check if it's a int 
            case "int":
            if(index >= data.size())
                data.add((Integer)element);
            else
                data.set(index, (Integer)element);
            break;
            case "double":
            if(index >= data.size())
                data.add((Double)element);
            else
                data.set(index, (Double)element);
            break;
            case "string":
            if(index >= data.size())
                data.add((String)element);
            else
                data.set(index, (String)element);
            break;
        }
    }
    public Object get(int index){
        if(index >= data.size())
            throw new IndexOutOfBoundsException();
        return data.get(index);
    }
    public Data copy(int from, int to){
        if(from >= data.size() || from > to)
            throw new IndexOutOfBoundsException();
        Data ret = new Data(this.name, this.type);
        for(; from<to; from++)
            ret.set(data.get(from),from); //using add
        return ret;
    }
}