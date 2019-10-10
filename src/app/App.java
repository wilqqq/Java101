package app;

import dataFrame.DataFrame;
import data.Data;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Henlo Java");
        DataFrame df = new  DataFrame(new String[]{"kol1","kol2","kol3"}, new String[]{"int","double","string"});
        df.add(new Data[]{
            new Data("int", new Object[]{(Integer)33,(Integer)22,(Integer)11}), new Data("double", new Object[]{(Double)3.3,(Double)2.2,(Double)1.1}), new Data("string", new Object[]{"czy czy", "dwa dwa", "jdn jdn"}) });
        df.print();
        System.out.println(df.get("kol1"));
        df.get("kfkfk");
        df.iloc(0).print();
    }
}