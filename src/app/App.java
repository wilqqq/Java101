
package app;

import dataFrame.DataFrame;
import dataFrame.*;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import sparseDataFrame.SparseDataFrame;
import value.Value;

import java.io.File;
import java.time.LocalDate;

import dValue.DValue;
import data.Data;

// import javafx.application.Application;
// import javafx.scene.Group;
// import javafx.scene.Scene;
// import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.ScatterChart;
// import javafx.scene.chart.XYChart;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.stage.FileChooser;
// import javafx.stage.Stage;

import java.sql.*;


public class App{
//public class App extends Application{

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

    /*
    DataFrame dffx;

    private Group drawDF(String x_column, String y_column){
        System.out.println("--- GUI SCATTER PLOT");

        //Defining the x axis               
        NumberAxis xAxis = new NumberAxis(0.0, 0.0038, 10); 
        xAxis.setLabel(x_column);          
                
        //Defining the y axis 
        NumberAxis yAxis = new NumberAxis(0, 0.076, 10); 
        yAxis.setLabel(y_column);

        //Creating the Scatter chart 
        ScatterChart<Number, Number> scatterChart = new ScatterChart(xAxis, yAxis); 

        //Prepare XYChart.Series objects by setting data 
        XYChart.Series series = new XYChart.Series();  

        //fill the scatterplot with data
        //TODO what if not present
        Data x = dffx.get(x_column);
        Data y = dffx.get(y_column);

        for(int i=0; i<x.size(); i++)
            series.getData().add(new XYChart.Data(((DValue)x.get(i)).getValue(), ((DValue)y.get(i)).getValue())); 
        // series.getData().add(new XYChart.Data(4, 5.5)); 
        // series.getData().add(new XYChart.Data(11, 14)); 
        // series.getData().add(new XYChart.Data(4, 5)); 
        // series.getData().add(new XYChart.Data(3, 3.5)); 
        // series.getData().add(new XYChart.Data(6.5, 7)); 

        //Setting the data to scatter chart        
        scatterChart.getData().addAll(series);
        scatterChart.setLegendVisible(false);
        // scatterChart.setBackground(arg0);

        return new Group(scatterChart);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("DataFrame on JavaFX");

        FileChooser fChoose = new FileChooser();
        fChoose.setInitialDirectory(new File("data"));
        fChoose.setInitialFileName("mingroupby.csv");

        Button btn = new Button("Select File");

        btn.setOnAction(event->{
            File sFile = fChoose.showOpenDialog(stage);
            System.out.println("file name: "+sFile.getName());
            DataFrame df = new DataFrame(
                "data/"+sFile.getName(),
                new String[]{"double","double","double"},
                null
            );
            dffx = df.iloc(0, 40);
            System.out.println("done");
            // df.print(); // THREADING ERROR 
        });

        VBox vBox = new VBox(btn);

        vBox.setLayoutX(460);
        vBox.setLayoutY(440);

        Group root = new Group(vBox);

        Scene scene = new Scene(root, 640, 480);

        Button btn2 = new Button("DRAW");

        btn2.setOnAction(event->{
            if(dffx != null){
                Scene s = new Scene(drawDF("last", "y"),540,400);
                stage.setScene(s);
            }else
                System.out.println("cant do that");
        });

        VBox vBox2 = new VBox(btn2);

        vBox2.setLayoutX(100);
        vBox2.setLayoutY(440);

        root.getChildren().add(vBox2);

        stage.setScene(scene);
        stage.show();
    }
    */
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
        
        
        // System.out.println("\n\n--- Group by! ---");
        // DataFrame dfgr = new  DataFrame( 
        //     "data/groupby.csv",
        //     // "data/groupby.csv",
        //     new String[]{"string","date","double","double"},
        //     null
        // );
        // dfgr.print();
        // dfgr.groupby(new String[]{"id","date"}).sum().print();
        // dfgr.groupby(new String[]{"id","date"}).mean().print();
        // dfgr.groupby(new String[]{"id","date"}).var().print();
        // dfgr.groupby(new String[]{"id","date"}).std().print();
        // dfgr.groupby(new String[]{"id","date"}).min().print();
        // dfgr.groupby(new String[]{"id","date"}).max().print();
        // dfgr.groupby(new String[]{"id","date"}).apply(new Mediana()).print();


        // //needs uncommenting of:
        // imports
        // launch
        // start
        // Class App extends...
        // launch()
        // mv $JAVA_HOME/lib/jrt-fs.jar $JAVA_HOME/lib
        // change debug configuration to JavaFX
        
        System.out.println("\n\n--- MariaDB and MySQL! ---");
        System.out.println("Remember to enable database: sudo systemctl start mariadb.service");
        System.out.println("init");
        try {
            try{
                Connection con = DriverManager.getConnection(
                "jdbc:mariadb://localhost/dfdb", "javadf", "javadf");//}= DriverManager.getConnection("jdbc:mariadb://localhost:3306/testj?user=diego2&password=diego")){
                System.out.println("connected");
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                System.out.println("done");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
 

    }
}
