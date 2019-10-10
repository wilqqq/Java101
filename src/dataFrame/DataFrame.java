package dataFrame;

import data.*;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * DataFrame
 */
public class DataFrame {
    private Data [] data;
    private Map<String, Integer> columnNames;

    public DataFrame( String [] columnNames, String [] dataTypes){
        this.data = new Data[columnNames.length];
        this.columnNames = new TreeMap<String, Integer>();
        for (int i=0; i<columnNames.length; i++){
            this.columnNames.put(columnNames[i], i);
            this.data[i] = new Data(dataTypes[i]);
        }
    }
    public DataFrame( String [] columnNames, String [] dataTypes, Data [] elements){
        this(columnNames, dataTypes);
        add(elements);
    }

    public void add(Data [] elements){
        if(elements.length != data.length)
            throw new Error("Wrong dimensions");
        try {
            //TODO - maping and smart data input
            // for(Data d: elements)
            for(int i=0; i<data.length; i++){
                //System.out.println(elements[i].getType().equals(data[i].getType()));
                if( elements[i].getType().equals(data[i].getType()))
                    data[i] = elements[i].copy(0, -1); //TODO fixed sizes
                else
                    throw new Error("Wrong column names");
            }
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public void print() {
        System.out.println(columnNames.keySet());
        String tmp = "| ";
        for(int j=0; j<data.length; j++)
            tmp += data[j].getType() + "|\t";
        System.out.println(tmp);
        int len = tmp.length();
        for(int i=0; i<data[0].size(); i++){
            tmp = "";
            for(int j=0; j<len; j++)
                tmp += "-";
            System.out.println(tmp);
            tmp = "|";
            for(int j=0; j<data.length; j++)
                tmp += data[j].get(i).toString() + "|\t";
            System.out.println(tmp);
        }
    }

    public Data get(String columnName, int from, int to){
        Integer l = this.columnNames.get(columnName);
        if(l==null)
            return null;
        else
            return data[l].copy(from, to);
    }

    public Data get(String columnName){
        return get(columnName, 0, -1);
    }

    public DataFrame get(String [] cols, boolean copy, int from, int to){
        Data [] columns = new Data[cols.length];
        String [] types = new String[cols.length];
        String [] names = new String[cols.length];
        int it=0;
        //TODO copy
        // if (copy) {
            for( String s: cols){
                columns[it] = get(s, from, to);
                if(columns[it] != null){
                    names[it] = new String(s);
                    types[it] = new String(columns[it].getType());
                    it++;
                }  
            }
        // } else {
        // }
        //TODO - fit to the size (use Arraylist)
        if(it != cols.length){
            Data [] columnsO = new Data[it];
            String [] typesO = new String[it];
            String [] namesO = new String[it];

            for(int i=0;i<it;i++){
                columnsO[i] = columns[i];
                typesO[i] = types[i];
                namesO[i] = names[i];
            }

            columns = columnsO;
            types = typesO;
            names = namesO;
        }
        return new DataFrame(names, types, columns);
    }
    public DataFrame get(String [] cols, boolean copy){
        return get(cols, copy, 0, -1);
    }

    public String [] getColumnNames(){
        String [] tmp = new String[data.length];
        int i=0;
        for(String s: columnNames.keySet())
            tmp[i++] = s;
        return tmp;
    }

    public String [] getDataTypes(){
        String [] tmp = new String[data.length];
        int i=0;
        for(Data d: data)
            tmp[i++] = d.getType();
        return tmp;
    }

    public DataFrame iloc(int from, int to){
        if(to >= data[0].size() || (from >= to && to > 0))
            return null;
        String [] cn = getColumnNames();
        return get(cn, true, from, to);
    }

    public DataFrame iloc(int index){
        return iloc(index, index+1);
    }

    public int size(){return this.data.length;}
}