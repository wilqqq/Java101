
package app;

import dataFrame.DataFrame;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import sparseDataFrame.SparseDataFrame;
import value.Value;

import java.time.LocalDate;

import dValue.DValue;
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
        // sdf.toDense(1,4,new String[]{"kl 2","kl 3","kl 2"}).print();

        // System.out.println("\n\n--- DataFrame from a file! ---");
        // DataFrame df2 = new  DataFrame( 
        //     "data/data.csv",
        //     new String[]{"double","double","double"},
        //     null
        // );
        // df2.iloc(5,10).print();

        // System.out.println("\n\n--- SparseDataFrame from a file! ---");
        // SparseDataFrame sdf2 = new SparseDataFrame(
        //     "data/sparse.csv",
        //     new String[]{"double","double","double"},
        //     null
        // );
        // DataFrame hlpr = sdf2.toDense(5,10,new String[]{"last","x","y","a","b","c"});
        // hlpr.print();

        // System.out.println("\n\n--- Value class and its inheritances! ---");
        // DValue dv = DValue.builder().setValue("12.34").build();
        // Value dv0 = dv.create("0.0");
        // DValue dv2 = (DValue)dv.clone();
        // System.out.println(dv+"+"+dv2+"="+(dv.add(dv2)));
        // System.out.println(dv+"*"+dv2+"="+(dv.mul(dv2)));
        // System.out.println(dv+"-"+dv2+"="+(dv.sub(dv2)));
        // System.out.println(dv+"/"+dv2+"="+(dv.div(dv2)));
        // try {
        //     dv.div(dv0);
        // } catch (Exception e) {
        //     System.out.println(dv+"/"+dv0+"=Exception: "+e.getMessage());
        // }
        // SValue sv1 = SValue.builder().setValue("valuevalue").build();
        // Value sv2 = sv1.create("vvalu");
        // System.out.println(sv1+" rotfl with "+sv2+" = "+(sv1.mul(sv2))+"["+sv1.getValue().length()+"] then derotf: "+sv1.div(sv2)+"["+sv1.getValue().length()+"]");
        // DTValue dtv1 = DTValue.builder()
        // .setSeconds("00")
        // .setMinutes("44")
        // .setHour("12")
        // .setDay("03")
        // .setMonth("06")
        // .setYear("1996")
        // .build();
        // Value dtv2 = dtv1.create("1996-06-03 15:44:22");
        // System.out.println(dtv1+" >= "+dtv2+" = "+(dtv1.gte(dtv2)));
        // System.out.println(dtv1+" <= "+dtv2+" = "+(dtv1.lte(dtv2)));
        // System.out.println(dtv1+" + "+dtv2+" = "+(dtv1.add(dtv2).sub(DTValue.EPOCH_DT_VALUE))); //FOR MEAN USE SUM OF DIFFERENCEs BETWEEN DATES AND EPOCH
        // System.out.println(dtv1+" / 2  = "+(dtv1.div(IValue.builder().setValue("2").build())));
        
        
        System.out.println("\n\n--- Group by! ---");
        DataFrame dfgr = new  DataFrame( 
            "data/groupby.csv",
            new String[]{"string","date","double","double"},
            null
        );
        dfgr.iloc(5,10,false).print();
        dfgr.groupby(new String[]{"id"}).sum().print();
        //dfgr.groupby(new String[]{"id"}).mean().print();


        // System.out.println("\n\n--- GUI! ---");
    }
}
