package dataFrame;

public class Mediana implements Applyable{
    @Override
    public DataFrame apply(DataFrame df){
        DataFrame outPut = new DataFrame(df.getColumnNames(),df.getDataTypes());
        int medianaIndex = df.size(true)/2;
        for(int i=0;i<outPut.size();i++){
            outPut.get(i,false).add(df.get(i).get(medianaIndex));
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