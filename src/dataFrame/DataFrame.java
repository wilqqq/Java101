package dataFrame;

import data.*;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dValue.DValue;

/**
 * DataFrame - a class for holding vectors of values with different data types
 * in a tabular form
 */
public class DataFrame {
    private Data[] data;
    protected Map<String, Integer> columnNames;

    protected DataFrame() {
    }

    public DataFrame(String[] columnNames, String[] dataTypes) {
        this.data = new Data[columnNames.length];
        this.columnNames = new TreeMap<String, Integer>();
        for (int i = 0; i < columnNames.length; i++) {
            this.columnNames.put(columnNames[i], i);
            this.data[i] = new Data(dataTypes[i]);
        }
    }

    public DataFrame(String[] columnNames, String[] dataTypes, Data[] elements) {
        this(columnNames, dataTypes);
        if (elements != null)
            add(elements);
    }

    public DataFrame(String fileName, String[] columnTypes, String[] columnNames) {
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            if (columnNames == null) {
                line = br.readLine();
                columnNames = line.split(",");
                // System.out.println(columnNames);
            }
            // br.mark(0); //<- aint gonna work for a big file
            // count elements + data integrity check (no missing values)
            // int colSize = 0;
            // while ((line = br.readLine())!= null) {
            // colSize++;
            // if ( line.split(",").length != columnNames.length)
            // throw new Error("missing data in line: " + colSize);
            // }
            // br.close();
            // br = new BufferedReader(new FileReader(fileName));
            this.data = new Data[columnTypes.length];
            this.columnNames = new TreeMap<String, Integer>();
            int i = 0;
            for (; i < data.length; i++) {
                this.data[i] = new Data(columnTypes[i].toLowerCase());
                this.columnNames.put(columnNames[i], i);
            }
            while ((line = br.readLine()) != null) {
                i = 0;
                for (String s : line.split(",")) {
                    Value el;
                    switch (columnTypes[i].toLowerCase()) {
                    case "int":
                        el = IValue.builder().setValue(s).build();
                        // try{
                        // el = Integer.parseInt(s);
                        // }catch (NumberFormatException e){
                        // el = 0;
                        // }
                        break;
                    case "double":
                        el = DValue.builder().setValue(s).build();
                        // try{
                        // el = Double.parseDouble(s);
                        // }catch (NumberFormatException e){
                        // el = 0.0;
                        // }
                        break;
                    case "string":
                        el = SValue.builder().setValue(s).build();
                        // el = s;
                        break;
                    case "date":
                        el = DTValue.builder().setValue(s).build();
                        break;
                    default:
                        throw new Error("unknown data type: " + columnTypes[i].toLowerCase());
                    }
                    this.data[i++].add(el);
                    // System.out.println(data[i-1]+"|"+el);
                }
                // System.out.println(line);
                // break;
            }
            // this.columnNames = new TreeMap<String, Integer>();
            // for (i=0; i<columnNames.length; i++){
            // this.data[i] = new Data(columnTypes[i]);
            // }
            // automatic closed with try-with-resources
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void add(Data[] elements) {
    //     if (elements.length != data.length)
    //         throw new Error("Wrong dimensions");
    //     try {
    //         // TODO - maping and smart data input
    //         // for(Data d: elements){
    //         for (int i = 0; i < data.length; i++) {
    //             // Integer i = this.columnNames.get(arg0)
    //             // System.out.println(elements[i].getType().equals(data[i].getType()));
    //             if (elements[i].getType().equals(data[i].getType())){
    //                 data[i] = elements[i].copy(0, -1);
    //             }else
    //                 throw new Error("Wrong column names");
    //         }
    //     } catch (Exception e) {
    //         throw new Error(e.toString());
    //     }
    // }

    public void add(Data[] elements) {
        if (elements.length != data.length)
            throw new Error("Wrong dimensions");
        try {
            // TODO - maping and smart data input
            // for(Data d: elements){
            for (int i = 0; i < data.length; i++) {
                // Integer i = this.columnNames.get(arg0)
                // System.out.println(elements[i].getType().equals(data[i].getType()));
                if (elements[i].getType().equals(data[i].getType())){
                    data[i].set(elements[i].copy(0, -1));
                }else
                    throw new Error("Wrong column names");
            }
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public String toString() {
        String tmp = "";
        String all;
        // int len = 8;
        // TODO wrong order (alphabetical)
        for (String s : columnNames.keySet()) {
            tmp += s + "\t";
            // len += s.length()/8;
        }
        all = tmp + "\n";
        // len += tmp.length();
        tmp = "";
        for (int j = 0; j < data.length; j++)
            tmp += data[j].getType() + "\t";
        // if(tmp.length()>len)
        // len = tmp.length();
        all += tmp + "\n";
        for (int i = 0; i < data[0].size(); i++) {
            tmp = "";
            // for(int j=0; j<len; j++)
            // tmp += "-";
            // tmp += "\n";
            for (int j = 0; j < data.length; j++)
                tmp += data[j].get(i).toString() + "\t";
            all += tmp + "\n";
        }
        return all;
    }

    public void print() {
        System.out.println(toString());
    }

    public Data get(String columnName, int from, int to, boolean copy) {
        Integer l = this.columnNames.get(columnName);
        if (l == null)
            return null;
        else
            return data[l].copy(from, to,copy);
    }

    public Data get(String columnName, int from, int to) {
        return get(columnName, from, to, true);
    }

    public Data get(String columnName) {
        return get(columnName, 0, -1);
    }

    public Data get(int index) {
        if (index < data.length)
            return data[index].copy(0, -1);
        else
            return null;
    }

    public DataFrame get(String[] cols, boolean copy, int from, int to) {
        Data[] columns = new Data[cols.length];
        String[] types = new String[cols.length];
        String[] names = new String[cols.length];
        int it = 0;
        // TODO copy
        if (copy) {
            for (String s : cols) {
                columns[it] = get(s, from, to);
                if (columns[it] != null) {
                    names[it] = new String(s);
                    types[it] = new String(columns[it].getType());
                    it++;
                }
            }
        } else {
            for (String s : cols) {
                columns[it] = get(s, from, to);
                if (columns[it] != null) {
                    names[it] = s;
                    types[it] = columns[it].getType();
                    it++;
                }
            }
        }
        if (it != cols.length) {
            Data[] columnsO = new Data[it];
            String[] typesO = new String[it];
            String[] namesO = new String[it];

            for (int i = 0; i < it; i++) {
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

    public DataFrame get(String[] cols, boolean copy) {
        return get(cols, copy, 0, -1);
    }

    public String[] getColumnNames() {
        String[] tmp = new String[data.length];
        int i = 0;
        for (String s : columnNames.keySet())
            tmp[i++] = s;
        return tmp;
    }

    public String[] getDataTypes() {
        String[] tmp = new String[data.length];
        int i = 0;
        for (Data d : data)
            tmp[i++] = d.getType();
        return tmp;
    }

    public DataFrame iloc(int from, int to) {
        return iloc(from, to, true);
    }
    public DataFrame iloc(int from, int to, boolean copy) {
        if (to > data[0].size() || (from >= to && to > 0))
            return null;
        String[] cn = getColumnNames();
        return get(cn, copy, from, to);
    }

    public DataFrame iloc(int index) {
        return iloc(index, index + 1);
    }

    public int size(boolean column) {
        if (!column)
            return this.data.length;
        else
            return this.data[0].size();
    }

    public int size() {
        return size(false);
    }
    /**
     * implementing Groupby interface methods
     * making hash map with keys given by @param groupBy and values which are new DataFrames: smaller than original one and
     * having specific property eg. when grupBy "id" first DataFrame will have columns with only "id" == "a"
     * @param groupBy name of column to sort for
     * @return inner class having smaller data frames which are sorted by the column given in @param groupBy
     *
     */
    public Group groupby(String[] colnames) {
        HashMap<List<Value>, DataFrame> result = new HashMap<>(colnames.length);
        List<Data> columns = Arrays.stream(colnames).map(this::get).collect(Collectors.toList());
        for (int i =0; i< size(true); i++){
            List<Value> values = new ArrayList<>(columns.size());
            for (Data column: columns){
                values.add(column.get(i));
            }
            DataFrame hlpr = result.get(values);
            boolean tr = result.keySet().contains(values);
            if (!result.containsKey(values)){
                result.put(values, iloc(i));
            }
            else {
                result.get(values).add(iloc(i).data);
            }
        }
        return new Group(result, colnames);
    }

    /**
     * Group - a class for grouping data inside a dataframe
     */
    public class Group implements Groupby {

        protected HashMap<List<Value>, DataFrame> map;
        protected List<String> colNames;

        public Group(HashMap<List<Value>, DataFrame> map, String[] colNames){
            this.map = map;
            this.colNames = Arrays.asList(colNames);
        }

        // TODO apply() documentation
        @Override
        public DataFrame apply(Applyable a) {
            return null;
        }

        // TODO max() documentation
        @Override
        public DataFrame max() {
            DataFrame df = ((DataFrame)map.values().toArray()[0]);
            String [] dfNames = df.getColumnNames();
            String [] dfTypes = df.getDataTypes();
            DataFrame result = new DataFrame(dfNames, dfTypes);
            for (List<Value> values: map.keySet()){
                // List<Value> toAdd = new ArrayList<>(values);//wrong order may occur
                Data [] toAdd = new Data[dfNames.length];
                int valIndex = 0;
                DataFrame dataFrameHelp = map.get(values);
                String [] names = dataFrameHelp.getColumnNames();
                String [] types = dataFrameHelp.getDataTypes();

                for (int k=0; k<dataFrameHelp.size(); k++){
                    if(colNames.contains(names[k])){
                        toAdd[k] = new Data(types[k], values.get(valIndex));
                        continue;
                    }

                    Data column = dataFrameHelp.data[k];

                    Value max = column.get(0);
                    for (int i=1; i<column.size();i++){
                        Value value = column.get(i);
                        if(value.gte(max)) 
                            max=value;
                    }
                    // toAdd.add(max);
                    toAdd[k] = new Data(types[k], max);

                }
                // try{
                    // Data [] add = new Data[toAdd.size()];
                    // for(int i=0; i<add.length; i++)
                    //     add[i] = new
                    result.add(toAdd);
                // }
                // catch (IOException e){
                //     System.out.println("Adding elements to DataFrame unsuccessful");
                // }
            }
            return result;
        }

        // TODO mean() documentation
        @Override
        public DataFrame mean() {
            DataFrame df = sum();
            String [] dfNames = df.getColumnNames();
            for(int j=0;j<df.size();j++){ //change to for j
                if(colNames.contains(dfNames[j]))
                    continue;
                Data column = df.data[j];
                for(int i=0; i<column.size(); i++)
                    column.get(i,false).div(IValue.builder().setValue(df.size(true)).build());
            }
            return df;
        }

        // TODO min() documentation
        @Override
        public DataFrame min() {
            DataFrame df = ((DataFrame)map.values().toArray()[0]);
            String [] dfNames = df.getColumnNames();
            String [] dfTypes = df.getDataTypes();
            DataFrame result = new DataFrame(dfNames, dfTypes);
            for (List<Value> values: map.keySet()){
                // List<Value> toAdd = new ArrayList<>(values);//wrong order may occur
                Data [] toAdd = new Data[dfNames.length];
                int valIndex = 0;
                DataFrame dataFrameHelp = map.get(values);
                String [] names = dataFrameHelp.getColumnNames();
                String [] types = dataFrameHelp.getDataTypes();

                for (int k=0; k<dataFrameHelp.size(); k++){
                    if(colNames.contains(names[k])){
                        toAdd[k] = new Data(types[k], values.get(valIndex));
                        continue;
                    }

                    Data column = dataFrameHelp.data[k];

                    Value max = column.get(0);
                    for (int i=1; i<column.size();i++){
                        Value value = column.get(i);
                        if(value.lte(max)) 
                            max=value;
                    }
                    // toAdd.add(max);
                    toAdd[k] = new Data(types[k], max);

                }
                // try{
                    // Data [] add = new Data[toAdd.size()];
                    // for(int i=0; i<add.length; i++)
                    //     add[i] = new
                    result.add(toAdd);
                // }
                // catch (IOException e){
                //     System.out.println("Adding elements to DataFrame unsuccessful");
                // }
            }
            return result;
        }

        // TODO std() documentation
        @Override
        public DataFrame std() {
            return null;
        }

        // TODO sum() documentation
        @Override
        public DataFrame sum() {
            DataFrame df = ((DataFrame)map.values().toArray()[0]);
            String [] dfNames = df.getColumnNames();
            String [] dfTypes = df.getDataTypes();
            DataFrame result = new DataFrame(dfNames, dfTypes);
            for (List<Value> values: map.keySet()){
                // List<Value> toAdd = new ArrayList<>(values);//wrong order may occur
                Data [] toAdd = new Data[dfNames.length];
                int valIndex = 0;
                DataFrame dataFrameHelp = map.get(values);
                String [] names = dataFrameHelp.getColumnNames();
                String [] types = dataFrameHelp.getDataTypes();

                for (int k=0; k<dataFrameHelp.size(); k++){
                    if(colNames.contains(names[k])){
                        toAdd[k] = new Data(types[k], values.get(valIndex));
                        continue;
                    }

                    Data column = dataFrameHelp.data[k];

                    Value sum = column.get(0);
                    for (int i=1; i<column.size();i++){
                        sum.add(column.get(i));
                    }
                    // toAdd.add(max);
                    toAdd[k] = new Data(types[k], sum);

                }
                // try{
                    // Data [] add = new Data[toAdd.size()];
                    // for(int i=0; i<add.length; i++)
                    //     add[i] = new
                    result.add(toAdd);
                // }
                // catch (IOException e){
                //     System.out.println("Adding elements to DataFrame unsuccessful");
                // }
            }
            return result;
        }

        // TODO var() documentation
        @Override
        public DataFrame var() {
            return null;
        }
    
        
    }
}

interface Groupby{
    //maximal value from columns except the one specified to group
    DataFrame max();
    DataFrame min();
    DataFrame mean();
    DataFrame std();
    DataFrame sum();
    DataFrame var();
    DataFrame apply(Applyable a);
}

interface Applyable{
    DataFrame apply(DataFrame df);
}

class Mediana implements Applyable{
    @Override
    public DataFrame apply(DataFrame df){
        DataFrame outPut = new DataFrame(df.getColumnNames(),df.getDataTypes());
        int medianaIndex = df.size(true)/2;
        for(int i=0;i<outPut.size();i++){
            outPut.get(i).add(outPut.get(i).get(medianaIndex));
        }
        // for(int i=0;i<df.size();i++){ //each column
        //     for(int j=0;j<df.size(true);j++){ //each element in row
        //         System.out.println(df.get(i).get(j));
        //         break;
        //     }
        // }
        return outPut;
    }
}