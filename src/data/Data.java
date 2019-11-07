package data;

//array list -> faster, like vector but not synchronised 
import java.util.ArrayList;

import dValue.DValue;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import value.Value;

public class Data {
    private String type;
    // private ArrayList<Object> data;
    private ArrayList<Value> data;

    public Data (String dataType){
        this.type = dataType.toLowerCase();
        data = new ArrayList<Value>();
    }
    public Data (String dataType, Value [] elements){
        this.type = dataType.toLowerCase();
        data = new ArrayList<Value>();
        set(elements);
    }
    public Data (String dataType, Object [] elements){
        this.type = dataType.toLowerCase();
        data = new ArrayList<Value>();
        Value [] castObj = new Value[elements.length];
        int i=0;
        switch(type){
            case "int":
            for(Object o: elements)
                castObj[i++] = IValue.builder().setValue((int)o).build();
            break;
            case "double":
            for(Object o: elements)
                castObj[i++] = DValue.builder().setValue((double)o).build();
            break;
            case "string":
            for(Object o: elements)
                castObj[i++] = SValue.builder().setValue(o.toString()).build();
            break;
            case "date":
            for(Object o: elements)
                castObj[i++] = DTValue.builder().setValue(o.toString()).build();
            break;
            default:
            throw new Error("unknown data type: "+dataType.toLowerCase());
        }
        set(castObj);
    }
    public Data (Data source) {
        this(source.type);
        Data obj = source.copy(0, source.size());
        this.data = obj.data;// possibly wrong / fixed
    }
    public String getType() {
        return this.type;
    }
    public int size() { return data.size();}
    public void set(Value element, int index) {
        // System.out.println(type);
        // System.out.println(element);
        // switch ( type ) {
        //     //cant use primitive in generic type, must use wrapper class
        //     //use instanceof to check if it's a int 
        //     case "int":
        //         if(!(element instanceof Integer))
        //             throw new Error("WRONG TYPE");
        //         if(index >= data.size())
        //             data.add(element);
        //         else
        //             data.set(index, element);
        //         break;
        //     case "double":
        //         if(!(element instanceof Double))
        //             throw new Error("WRONG TYPE");
        //         if(index >= data.size())
        //             data.add(element);
        //         else
        //             data.set(index, element);
        //     break;
        //     case "string":
        //         if(!(element instanceof String))
        //             throw new Error("WRONG TYPE");
        //         if(index >= data.size())
        //             data.add(element);
        //         else
        //             data.set(index, element);
        //     break;
        // }
        if(index >= data.size())
            data.add(element);
        else
            data.set(index, element);
    }
    public void add(Value element) {
        // System.out.println(type);
        // switch ( type ) {
        //     //cant use primitive in generic type, must use wrapper class
        //     //use instanceof to check if it's a int 
        //     case "int":
        //         if(!(element instanceof Integer))
        //             throw new Error("WRONG TYPE");
        //         break;
        //     case "double":
        //         if(!(element instanceof Double))
        //             throw new Error("WRONG TYPE");
        //     break;
        //     case "string":
        //         if(!(element instanceof String))
        //             throw new Error("WRONG TYPE");
        //     break;
        // }
        data.add(element);
    }
    public void set(Value [] elements) {
        try {
            for(Value e: elements)
                set(e, this.data.size());
        } catch (Exception e) {
            throw new Error("WRONG TYPE AT: "+e.toString());
        }
    }
    public Value get(int index){
        if(index >= data.size())
            throw new IndexOutOfBoundsException();
        return data.get(index);
    }
    public Data copy(int from, int to){
        if(to < 0)
            to = data.size() + 1 +to;
        if(from >= data.size() || from > to)
            throw new IndexOutOfBoundsException();
        Data ret = new Data(this.type);
        for(; from<to; from++){
            //System.out.println(from);
            ret.set(data.get(from),from); //using add
        }
        return ret;
    }
    public String toString(){
        String ret = type.toString() + ": [ ";
        for(Object o: data)
            ret += o.toString() + " ";
        ret += "]";
        return ret;
    }
}