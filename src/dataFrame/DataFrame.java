package dataFrame;

import data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * DataFrame - a class for holding vectors of values with different data types in a tabular form
 */
public class DataFrame {
    private Data [] data;
    protected Map<String, Integer> columnNames;

    protected DataFrame(){}

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
        if(elements != null)
            add(elements);
    }

    public DataFrame(String fileName, String [] columnTypes, String [] columnNames){
		try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            if(columnNames == null){
                line = br.readLine();
                columnNames = line.split(",");
                // System.out.println(columnNames);
            }
            // br.mark(0); //<- aint gonna work for a big file
            // count elements + data integrity check (no missing values)
            // int colSize = 0;
			// while ((line = br.readLine())!= null) {
            //     colSize++;
            //     if ( line.split(",").length != columnNames.length)
            //         throw new Error("missing data in line: " + colSize);
            // }
            // br.close();
            // br = new BufferedReader(new FileReader(fileName));
            data = new Data[columnTypes.length];
            int i=0;
            for(;i<data.length;i++)
                data[i] = new Data(columnTypes[i].toLowerCase());
            while((line = br.readLine())!= null) {
                i=0;
                for(String s: line.split(",")){
                    Object el;
                    switch(columnTypes[i].toLowerCase()){
                        case "int":
                            try{
                                el = Integer.parseInt(s);
                            }catch (NumberFormatException e){
                                el = 0;
                            }
                        break;
                        case "double":
                            try{
                                el = Double.parseDouble(s);
                            }catch (NumberFormatException e){
                                el = 0.0;
                            }
                        break;
                        case "string":
                            el = s;
                        break;
                        default:
                        throw new Error("unknown data type: "+columnTypes[i].toLowerCase());
                    }
                    data[i++].add(el);
                    // System.out.println(data[i-1]+"|"+el);
                }
                // System.out.println(line);
                break;
            }
            this.columnNames = new TreeMap<String, Integer>();
            for (i=0; i<columnNames.length; i++){
                this.columnNames.put(columnNames[i], i);
                this.data[i] = new Data(columnTypes[i]);
            }
            // automatic closed with try-with-resources
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void add(Data [] elements){
        if(elements.length != data.length)
            throw new Error("Wrong dimensions");
        try {
            //TODO - maping and smart data input
            //for(Data d: elements){
            for(int i=0; i<data.length; i++){
                //Integer i = this.columnNames.get(arg0)
                //System.out.println(elements[i].getType().equals(data[i].getType()));
                if( elements[i].getType().equals(data[i].getType()))
                    data[i] = elements[i].copy(0, -1);
                else
                    throw new Error("Wrong column names");
            }
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public String toString() {
        String tmp = "";
        String all;
        int len = 8;
        for(String s: columnNames.keySet()){
            tmp += s + "\t";
            //len += s.length()/8;
        }
        all = tmp + "\n";
        len += tmp.length();
        tmp = "";
        for(int j=0; j<data.length; j++)
            tmp += data[j].getType() + "\t";
        // if(tmp.length()>len)
        //     len = tmp.length();
        all += tmp +"\n";
        for(int i=0; i<data[0].size(); i++){
            tmp = "";
            for(int j=0; j<len; j++)
                tmp += "-";
            tmp += "\n";
            for(int j=0; j<data.length; j++)
                tmp += data[j].get(i).toString() + "\t";
            all += tmp + "\n";
        }
        return all;
    }

    public void print(){
        System.out.println(toString());
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

    public Data get(int index){
        if(index < data.length)
            return data[index].copy(0, -1);
        else
            return null;
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

    public int size( boolean column ){ 
        if (!column)
            return this.data.length;
        else
            return this.data[0].size();
    }

    public int size(){ return size(false);}
}