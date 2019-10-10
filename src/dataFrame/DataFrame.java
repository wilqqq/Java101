package dataFrame;

import data.*;

/**
 * DataFrame
 */
public class DataFrame {
    private Data [] data;

    public DataFrame( String [] columnNames, String [] dataTypes){
        data = new Data[columnNames.length];
        for (int i=0; i<columnNames.length; i++){
            data[i] = new Data(columnNames[i], dataTypes[i]);
        }
    }

    public void add(Data [] elements){
        if(elements.length != data.length)
            throw new Error("Wrong dimensions");
        try {
            //TODO - maping and smart data input
            // for(Data d: elements)
            for(int i=0; i<data.length; i++)
                if( elements[i].getType() == data[i].getType())
                    data[i] = elements[i];
                else
                    throw new Error("Wrong column names");
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public void print() {
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
}