package sparseDataFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import dataFrame.*;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import value.Value;
import cooValue.*;
import dValue.DValue;
import data.Data;

/*
 * SparseDataFrame
 */
public class SparseDataFrame extends DataFrame{
    private ArrayList<COOValue>[] data;
    private String [] dataTypes;
    private int dataLength;
    private Value [] hide;
    public SparseDataFrame(DataFrame df ){
        super();
        this.dataLength = 0;
        //this.dataLength = df.size(true);
        this.data = new ArrayList[df.size()];
        this.columnNames = new TreeMap<String, Integer>();
        String [] coln = df.getColumnNames();
        this.dataTypes = df.getDataTypes();
        for (int i=0; i<coln.length; i++){
            this.columnNames.put(coln[i], i);
            this.data[i] = new ArrayList<COOValue>();
        }
        sparse(df);
    }
    public SparseDataFrame(String fileName, String [] columnTypes, String [] columnNames){
        this(fileName, columnTypes, columnNames, null);
    }
    public SparseDataFrame(String fileName, String [] columnTypes, String [] columnNames, Value [] hide){
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            if(columnNames == null){
                line = br.readLine();
                columnNames = line.split(",");
                // System.out.println(columnNames);
            }
            this.data = new ArrayList[columnTypes.length];
            this.dataTypes = new String[columnTypes.length];
            this.columnNames = new TreeMap<String, Integer>();
            int i=0;
            for(;i<data.length;i++){
                this.data[i] = new ArrayList<COOValue>();
                this.dataTypes[i] = columnTypes[i].toLowerCase();
                this.columnNames.put(columnNames[i], i);
            }
            this.hide = new Value[this.dataTypes.length];
            if(hide == null){
                for(i=0;i<this.dataTypes.length;i++){
                    switch(dataTypes[i].toLowerCase()){
                        case "int":
                        this.hide[i] = IValue.builder().setValue("0").build();
                        break;
                        case "double":
                        this.hide[i] = DValue.builder().setValue("0.0").build();
                        break;
                        case "string":
                        this.hide[i] = SValue.builder().setValue("").build();
                        break;
                        case "date":
                        this.hide[i] = DTValue.builder().setValue("").build();
                        break;
                        default:
                        throw new Error("unknown data type: "+this.dataTypes[i].toLowerCase());
                    }
                }
            } else
                this.hide = hide;
            
            for(int index=0;(line = br.readLine())!= null;index++) {
                i=0;
                for(String s: line.split(",")){
                    Value el;
                    switch(columnTypes[i].toLowerCase()){
                        case "int":
                        el = IValue.builder().setValue(s).build();
                            // try{
                            //     // hide[i] = Integer.parseInt(hide.toString());
                            //     el = Integer.parseInt(s);
                            // }catch (NumberFormatException e){
                            //     el = 0;
                            // }
                        break;
                        case "double":
                        el = DValue.builder().setValue(s).build();
                            // try{
                            //     // hide = Double.parseDouble(hide.toString());
                            //     el = Double.parseDouble(s);
                            // }catch (NumberFormatException e){
                            //     el = 0.0;
                            // }
                        break;
                        case "string":
                        el = SValue.builder().setValue(s).build();
                            // el = s;
                            // hide = hide.toString();
                        break;
                        case "date":
                        el = DTValue.builder().setValue(s).build();
                        break;
                        default:
                        throw new Error("unknown data type: "+columnTypes[i].toLowerCase());
                    }
                    if(!el.equals(this.hide[i]))
                        this.data[i++].add(new COOValue(el,index));
                    //System.out.println(data[i-1]+"|"+el);
                }
                // System.out.println(line);
                // break;
            }
            // System.out.println(this);
            // automatic closed with try-with-resources
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void sparse(DataFrame df, Value [] hide){
        if(hide == null){
            this.hide = new Value[this.dataTypes.length];
            for(int i=0;i<this.dataTypes.length;i++){
                switch(dataTypes[i].toLowerCase()){
                    case "int":
                    this.hide[i] = IValue.builder().setValue("0").build();
                    break;
                    case "double":
                    this.hide[i] = DValue.builder().setValue("0.0").build();
                    break;
                    case "string":
                    this.hide[i] = SValue.builder().setValue("").build();
                    break;
                    case "date":
                    this.hide[i] = DTValue.builder().setValue("").build();
                    break;
                    default:
                    throw new Error("unknown data type: "+this.dataTypes[i].toLowerCase());
                }
            }
        } else
            this.hide = hide;
        this.dataLength += df.size(true);
        for (int i=0; i<df.size(true); i++){
            for (int j=0; j<df.size(); j++){
                Value tmp = df.get(j).get(i);
                if(!tmp.eq(this.hide[j]))
                    data[j].add(new COOValue(tmp, i));
                // Data tmp = df.get(j);
                // if(!tmp.equals(hide)){
                //     switch ( dataTypes[j] ) {
                //         case "int":
                //             data[j].add(new COOValue<Integer>((Integer)tmp,i));
                //             break;
                //         case "double":
                //             data[j].add(new COOValue<Double>((Double)tmp,i));
                //         break;
                //         case "string":
                //             data[j].add(new COOValue<String>((String)tmp,i));
                //         break;
                //     }
                // }
            }
        }
    }
    public void sparse(DataFrame df){
        sparse(df, null);
    }
    @Override
    public String [] getColumnNames(){
        String [] tmp = new String[data.length];
        int i=0;
        for(String s: columnNames.keySet())
            tmp[i++] = s;
        return tmp;
    }
    public DataFrame toDense(int from, int to, String [] colNames){
        Value [] tmp = new Value[to-from];
        ArrayList<Data> dataA = new ArrayList<Data>();
        ArrayList<String> colsA = new ArrayList<String>();
        for (String s:colNames){
            Integer i = this.columnNames.get(s);
            if(i == null)
                continue;
            for(String ss: colsA){
                if (ss.equals(s)){
                    s +="x";
                }
            }
            colsA.add(s);
            int last = 0;
            /* different approach is to dense the whole
             data and use super method to cut out the wanted piece with iloc,
             but it ain't optimal*/
            
            for (int j=0; j<this.data[i].size(); j++){
                COOValue cv = this.data[i].get(j);
                if(cv.getSpot()<from)
                    continue;
                if(cv.getSpot()>=to)
                    break;
                // System.out.println((last+from)+"|"+cv.getSpot());
                for(;last+from<cv.getSpot();last++)
                    tmp[last] = hide[i];
                tmp[last++] =(cv.getValue());
            }
            for(;last<tmp.length;last++)
                tmp[last] = hide[i];
            // System.out.println(dataTypes[i]);
            dataA.add( new Data(dataTypes[i], tmp));
        }
        /*there can be no dataframe without column names, but
        there can be one with no data in it*/
        if ( colsA.size() == 0){
            String [] dtps = new String[colNames.length];
            for(int i=0;i<dtps.length;i++)
                dtps[i] = "int";
            return new DataFrame(colNames,dtps);
        }
        String [] cols = new String[colsA.size()];
        Data [] data = new Data[colsA.size()];
        for(int i=0;i<colsA.size();i++){
            cols[i] = colsA.get(i);
            data[i] = dataA.get(i);
        }
        return new DataFrame(cols, dataTypes, data);
    }
    public DataFrame toDense(){ return toDense(0,dataLength,getColumnNames());}
    public DataFrame toDense(String [] colNames){ return toDense(0,dataLength,colNames);}
    public DataFrame toDense(int from, int to){ return toDense(from,to,getColumnNames());}
    public String toString(){
        String tmp = "SDF\n";
        Object [] cn = columnNames.keySet().toArray();
        for(int i=0;i<data.length; i++){
            tmp += cn[i]+"("+ dataTypes[i] +"): ";
            for(int j=0; j<data[i].size(); j++)
                tmp += data[i].get(j).toString();
            tmp += "\n";
        }
        return tmp;
    }
}