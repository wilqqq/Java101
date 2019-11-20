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
    public Data (String dataType, Value element){
        this.type = dataType.toLowerCase();
        data = new ArrayList<Value>();
        add(element);
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
            throw new Error("unknown data type: "+dataType.toLowerCase());//TODO own exception being trown
        }
        set(castObj);
    }
    public Data (Data source) {
        this(source.type);
        Data obj = source.copy(0, source.size());
        this.data = obj.data;
    }
    public String getType() {
        return this.type;
    }
    public int size() { return data.size();}
    public void set(Value element, int index) {
        if(index >= data.size())
            data.add(element);
        else
            data.set(index, element);
    }
    public void add(Value element) {
        data.add(element);
    }
    public void set(Value [] elements) {
        try {
            for(Value e: elements)
                add(e);
        } catch (Exception e) {//TODO own exception being trown
            throw new Error("WRONG TYPE AT: "+e.toString());
        }
    }
    public void set(Data elements) {
        try {
            for(int e=0;e<elements.size();e++)
                add(elements.get(e));
        } catch (Exception e) {//TODO own exception being trown
            throw new Error("WRONG TYPE AT: "+e.toString());
        }
    }
    public Value get(int index){
        return get(index, true);
    }
    public Value get(int index, boolean copy){
        if(index >= data.size())
            throw new IndexOutOfBoundsException();
        if(copy){
            try {
                return (Value)data.get(index).clone();
            } catch (Exception e) {
                return data.get(index);
            }
        }
        return data.get(index);
    }
    public Data copy(int from, int to, boolean copy){
        if(to < 0)
            to = data.size() + 1 +to;
        if(from > data.size() || from > to)
            throw new IndexOutOfBoundsException();
        Data ret = new Data(this.type);
        for(; from<to; from++){
            Value v = data.get(from);
            //TODO lazy clone
            ret.set(copy?(Value)v.create(v.toString()):v,from); //using add
        }
        return ret;
    }
    public Data copy(int from, int to){
        return copy(from, to, true);
    }
    public String toString(){
        String ret = type.toString() + ": [ ";
        for(Object o: data)
            ret += o.toString() + " ";
        ret += "]";
        return ret;
    }
}