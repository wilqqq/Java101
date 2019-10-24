package app;

import dataFrame.DataFrame;
import sparseDataFrame.SparseDataFrame;
import data.Data;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Henlo Java");

        // System.out.println("### lab1\n\n---dataframe tests ---");
        // DataFrame df = new  DataFrame(
        //     new String[]{"kol1","kol2","kol3"},
        //     new String[]{"int","double","string"}
        // );
        // df.add(new Data[]{
        //     new Data("int", new Object[]{(Integer)33,(Integer)22,(Integer)0}), 
        //     new Data("double", new Object[]{(Double)3.3,(Double)0.0,(Double)1.1}), 
        //     new Data("string", new Object[]{"", "dwa dwa", "jdn jdn"}) }
        // );
        // df.print();
        
        // System.out.println("\n\n---get column existing ---");
        // System.out.println(df.get("kol1"));

        // System.out.println("\n\n---get v-column non-existing ---");
        // df.get("kfkfk"); //null object

        // System.out.println("\n\n---get h-column existing ---");
        // df.iloc(0).print();

        // System.out.println("\n\n---get h-column non-existing ---");
        // df.iloc(10); //null object

        // System.out.println("\n\n---get span of h-columns ---");
        // df.iloc(1,2).print();

        // System.out.println("\n\n---get span of v-columns ---");
        // df.get(new String[]{"kol1", "kol2"}, false).print();


        // System.out.println("### lab 2\n\n---sparsed df tests ---");
        // DataFrame ndf = new  DataFrame(
        //     new String[]{"kl 1","kl 2","kl 3"},
        //     new String[]{"int","int","int"}, 
        //     new Data[]{
        //         new Data("int", new Object[]{(Integer)33,(Integer)22,(Integer)0,(Integer)0,(Integer)0,(Integer)0,(Integer)33,(Integer)22,(Integer)0}), 
        //         new Data("int", new Object[]{(Integer)33,(Integer)0,(Integer)0,(Integer)33,(Integer)22,(Integer)0,(Integer)33,(Integer)22,(Integer)0}), 
        //         new Data("int", new Object[]{(Integer)0,(Integer)22,(Integer)0,(Integer)33,(Integer)22,(Integer)0,(Integer)33,(Integer)22,(Integer)0}) 
        //     }
        // );
        // System.out.println("\n\n--- original df ---");
        // ndf.print();

        // System.out.println("\n\n--- sparsed df ---");
        // SparseDataFrame sdf = new SparseDataFrame(ndf);
        // System.out.println(sdf);

        // System.out.println("\n\n--- densed sdf ---");
        // sdf.toDense(1,4,new String[]{"kl 1","kl 3"}).print();

        System.out.println("\n\n--- DataFrame from a file! ---");
        // System.out.println((new File(".")).getCanonicalPath());
        DataFrame df = new  DataFrame( 
            "data/data.csv",
            new String[]{"double","double","double"},
            null
        );
        df.print();
    }
}