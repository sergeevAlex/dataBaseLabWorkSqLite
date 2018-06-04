package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteHelper {
    private static Connection c = null;
    public static Connection getConn() throws Exception {
        if(c == null){
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/alexey/IdeaProjects/DataBaseLabWork3SQlite/src/sample/Learning.sqlite");
        }
        return c;
    }
}