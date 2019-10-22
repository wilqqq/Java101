package sparseDataFrame;

import java.util.ArrayList;
import java.util.TreeMap;

import dataFrame.*;
import cooValue.*;
import data.Data;

/*
 * SparseDataFrame
 */
public class SparseDataFrame extends DataFrame{
    private ArrayList<COOValue>[] data;
    private String [] dataTypes;
    private int dataLength;
    private Object hide;
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
    public void sparse(DataFrame df, Object hide){
        this.hide = hide;
        this.dataLength += df.size(true);
        for (int i=0; i<df.size(true); i++){
            for (int j=0; j<df.size(); j++){
                Object tmp = df.get(j).get(i);
                if(!tmp.equals(hide))
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
    @Override
    public String [] getColumnNames(){
        String [] tmp = new String[data.length];
        int i=0;
        for(String s: columnNames.keySet())
            tmp[i++] = s;
        return tmp;
    }
    public DataFrame toDense(int from, int to, String [] colNames){
        Object [] tmp = new Object[to-from];
        Data [] data = new Data[dataTypes.length];
        for (String s:colNames){
            Integer i = this.columnNames.get(s);
            if(i == null)
                continue;
            int last = 0;
            for (int j=0; j<this.data[i].size(); j++){
                COOValue cv = this.data[i].get(j);
                if(cv.getSpot()<from)
                    continue;
                if(cv.getSpot()>=to)
                    break;
                // System.out.println((last+from)+"|"+cv.getSpot());
                for(;last+from<cv.getSpot();last++)
                    tmp[last] = hide;
                tmp[last++] =(cv.getValue());
            }
            for(;last<tmp.length;last++)
                tmp[last] = hide;
            // System.out.println(dataTypes[i]);
            data[i] = new Data(dataTypes[i], tmp);
        }
        // System.out.println(super.getColumnNames());
        // System.out.println(dataTypes);
        // System.out.println(data);
        return new DataFrame(this.getColumnNames(), dataTypes, data);
    }
    public DataFrame toDense(){ return toDense(0,dataLength,getColumnNames());}
    public DataFrame toDense(String [] colNames){ return toDense(0,dataLength,colNames);}
    public DataFrame toDense(int from, int to){ return toDense(from,to,getColumnNames());}
    public void sparse(DataFrame df){
        sparse(df, 0);
    }
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