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

    public static void getS() {
        
    }
}