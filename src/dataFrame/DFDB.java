package dataFrame;

import dValue.DValue;
import data.Data;
import dfExceptions.UnknownTypeException;
import dtValue.DTValue;
import iValue.IValue;
import sValue.SValue;
import value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

/**
 * DFDB - a class for holding vectors of values with different data types
 * in a tabular form on a database server (mariaDB)
 */
public class DFDB extends DataFrame{
    private String[] columnNames;
    private String login;
    private String hostAddr;
    private String db;
    private Connection conn;
    private boolean connected;

    protected DFDB() {
        login = "javadf";
        hostAddr = "localhost";
        db = "/db";
        connected = false;
        conn = null;
    }

    public DFDB(String passwd) {
        this();
        connect(passwd);
    }

    public void connect(String passwd){
        try{
            //}= DriverManager.getConnection("jdbc:mariadb://localhost:3306/testj?user=diego2&password=diego")){
//            System.out.println("jdbc:mariadb://"+hostAddr+db+' '+login+' '+passwd);
            conn = DriverManager.getConnection(
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

    public DataFrame get(String tableName, String [] columnNames, String [] columnTypes, int from, int to) throws SQLException, UnknownTypeException {
        return SELECT(this.conn, tableName, columnNames, columnTypes, from, to);
    }

    static DataFrame SELECT(Connection conn,String tableName, String [] columnNames, String [] columnTypes, int from, int to) throws SQLException, UnknownTypeException {
        String WHERE = "";
        if (from == to)
            WHERE += " WHERE ROW_NUMBER()=" + from;
        else if (from < to)
            WHERE += " WHERE ROW_NUMBER() BETWEEN " + from + " AND " + to;

        return SELECT(conn, tableName, columnNames, columnTypes, WHERE,null);
    }

    static DataFrame SELECT(Connection conn,String tableName, String [] columnNames, String [] columnTypes, String WHERE, String [] MODS) throws SQLException, UnknownTypeException {
        try {
            Statement st = conn.createStatement();
            if(MODS == null){
                MODS = new  String[columnNames.length];
                for (int i=0;i<MODS.length;i++)
                    MODS[i]="";
            }
            String query = "SELECT "+MODS[0]+'(' + columnNames[0];
            int i=1;
            for (; i < columnNames.length; i++) {
                query += "),"+ MODS[i]+'('+ columnNames[i];
            }
            query += ") FROM " + tableName+' ';
            query += WHERE+";";

            ResultSet rs = st.executeQuery(query);

            DataFrame df = new DataFrame(columnNames,columnTypes);

            Data [] data = new Data[columnNames.length];
            for (i=0; i<data.length; i++){
                data[i] = new Data(columnTypes[i].toLowerCase());
            }

            while(rs.next()){
                for(i=0;i<columnNames.length;i++){
                    Value el;
//                    System.out.println(rs.getString(columnNames[i]));
                    String pre="",post="";
                    if(MODS[i]!=""){
                        pre = MODS[i]+'(';
                        post = ")";
                    }

                    switch (columnTypes[i].toLowerCase()) {
                        case "int":
                            el = IValue.builder().setValue(rs.getString(pre+columnNames[i]+post)).build();
                            break;
                        case "double":
                            el = DValue.builder().setValue(rs.getString(pre+columnNames[i]+post)).build();
                            break;
                        case "string":
                            el = SValue.builder().setValue(rs.getString(pre+columnNames[i]+post)).build();
                            break;
                        case "date":
                            String s = rs.getString(pre+columnNames[i]+post);
//                            el = new DTValue(rs.getDate(columnNames[i]));
                            el = DTValue.builder().setValue(s).build();
//                            el = new DTValue(rs.getString(columnNames[i]));
                            break;
                        default:
                            throw new UnknownTypeException(97,columnTypes[i],columnNames[i]);
//                            throw new Error("unknown data type: " + columnTypes[i].toLowerCase());
                    }
                    data[i].add(el);
                }
            }

            df.add(data);
            st.close();

            return df;
        }catch (SQLException | UnknownTypeException e){
            throw e;
        }
    }

    public String ask(String query) throws SQLException{
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        String rcv="";
        while (rs.next()){
            rcv+=rs.getString(1)+'\t';
//            rcv+=rs.getString("Database")+'\n';
        }
        st.close();
        rs.close();
        return rcv+='\n';
    }

//    public boolean loadLocalFile(String absoluteFilePath, String tableName) throws SQLException {
//
//        String query = "LOAD DATA LOCAL INFILE '" +
//                absoluteFilePath +
//                "' INTO TABLE " + tableName +
//                " CHARACTER SET UTF8 FIELDS TERMINATED BY',' OPTIONALLY ENCLOSED BY\"\" LINES TERMINATED BY '\\n';";
//        System.out.println(ask(query));
//        return true;
//    }
    public boolean loadLocalFile(String absoluteFilePath, String tableName, String [] columnNames, String [] columnTypes) {
        if (!connected)
            return false;
        String query = "CREATE TABLE IF NOT EXISTS " +
                tableName + "(";
        query += columnNames[0] + ' ' + columnTypes[0];
        for (int i = 1; i < columnNames.length; i++) {
            query += ',' + columnNames[i] + ' ' + columnTypes[i];
        }
        query += ");";
        try {
            Statement st = conn.createStatement();
            st.execute(query);
            query = "LOAD DATA LOCAL INFILE '" +
                    absoluteFilePath +
                    "' INTO TABLE " + tableName +
                    " CHARACTER SET UTF8 FIELDS TERMINATED BY',' OPTIONALLY ENCLOSED BY\"\" LINES TERMINATED BY '\\n' IGNORE 1 LINES;";
            st.execute(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("\nSQLException occured\n");
            return false;
        }
        return true;
    }
    public boolean loadLocalFile(String absoluteFilePath, String tableName ,String [] columnNames) {
        try (FileReader fr = new FileReader(absoluteFilePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            if(columnNames == null){
                line = br.readLine();
                columnNames = line.split(",");
            }

            String [] tmp = br.readLine().split(",");

            if(tmp.length != columnNames.length)
                return false;

            while ((line = br.readLine()) != null) {
                String [] s = line.split(",");
                //find the largest and the type;
                for (int i=0;i<s.length;i++) {
                    if(s[i].length() >= tmp[i].length()) {
                        tmp[i] = s[i];
                    }
                }
            }
            String [] columnTypes;
            //basing on tmp recognise types
            for(String s : tmp){
                //no idea, try casting, count letters followed by continue?
                //TODO HOW TO RECOGNIZE THEM VARIABLE TYPES
            }
            //tmp solution
            columnTypes = tmp;

            return loadLocalFile(absoluteFilePath,tableName,columnNames,columnTypes);
            // automatic closed with try-with-resources
        } catch (Exception e) {
            return false;
        }
    }

    public DataFrame groupby(String tableName, String [] columnNamesToShow,String [] columnNamesToGroupBy, String [] columnTypes, String [] MODS) throws SQLException, UnknownTypeException {
        String GROUPBY = "GROUP BY "+columnNamesToGroupBy[0];
        for (int i=1;i<columnNamesToGroupBy.length;i++)
            GROUPBY+=','+columnNamesToGroupBy[i];
        return SELECT(this.conn, tableName, columnNamesToShow, columnTypes, GROUPBY, MODS);
    }

    public DataFrame min(String tableName, String [] columnNamesToShow,String [] columnNamesToGroupBy, String [] columnTypes) throws SQLException, UnknownTypeException {
        String [] MODS = new  String[columnNamesToShow.length];
        MODS[0] = "";
        for (int i=1;i<MODS.length;i++)
            MODS[i]="MIN";
        return groupby(tableName,columnNamesToShow,columnNamesToGroupBy,columnTypes,MODS);
    }
}