package dataFrame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DataFrame - a class for holding vectors of values with different data types
 * in a tabular form
 */
public class DFDB extends DataFrame{
    private String[] columnNames;
    private String login;
    private String hostAddr;
    private String db;
    private Connection con;
    private boolean connected;

    protected DFDB() {
        login = "'javadf'";
        hostAddr = "'localhost'";
        db = "/dfdb";
        connected = false;
        con = null;
    }

    protected DFDB(String passwd) {
        this();
        connect(passwd);
    }

    public void connect(String passwd){
        try{
            //}= DriverManager.getConnection("jdbc:mariadb://localhost:3306/testj?user=diego2&password=diego")){
            con = DriverManager.getConnection(
            "jdbc:mariadb://"+hostAddr+db, 
            login, 
            passwd 
            );
            connected = true;
        }catch(SQLException e){
            connected = false;
            System.err.println("could not connect to database");
        }
    }

    public boolean loadFile(String fileName, String tabName){
        if(!connected)
            return false;
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            if (columnNames == null) {
                line = br.readLine();
                columnNames = line.split(",");
            }
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
                        break;
                    case "double":
                        el = DValue.builder().setValue(s).build();
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
                }
            }
            // automatic closed with try-with-resources
        } catch (FileNotFoundException e) {
            System.err.println("no such file, aborting");
            return false;
        }

        // if (statement.isWrapperFor(Class.forName("com.mysql.jdbc.Driver"))) {
        //     Statement mariaDbStatement = statement.unwrap(Class.forName("com.mysql.jdbc.Driver"));
        //     mariaDbStatement.setLocalInfileInputStream(in);
        //     String sql = "LOAD DATA LOCAL INFILE 'dummyFileName'"
        //                 + " INTO TABLE gigantic_load_data_infile "
        //                 + " FIELDS TERMINATED BY '\\t' ENCLOSED BY ''"
        //                 + " ESCAPED BY '\\\\' LINES TERMINATED BY '\\n'";
        //     statement.execute(sql);
        // } else {
        //     in.close();
        //     throw new RuntimeException("Mariadb JDBC adaptor must be used");
        // }
    }

}