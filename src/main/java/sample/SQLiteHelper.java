package sample;


import sample.controller.HelpController;

import java.sql.*;


public class SQLiteHelper {
    public static Connection DBConnector(){
        try {
            Connection con = null;
            Class.forName("org.sqlite.JDBC");
            con= DriverManager.getConnection(HelpController.URL_DB);
            return con;
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void CheckConnection(){
       Connection con= SQLiteHelper.DBConnector();
        if(con != null){
            System.out.println("Connection successful");

        }else {
            System.out.println("Connection not successful");
        }
    }
}


