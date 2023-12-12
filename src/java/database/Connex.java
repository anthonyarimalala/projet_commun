package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connex {
    private static Connection connection = null;
    private static String URL = "jdbc:postgresql://localhost:5432/s5s3";
    private static String USER = "postgres";
    private static String PASSWORD = "anthony";
    
    public static void main(String[] args){
    }
    public Connex(){
        
    }
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
