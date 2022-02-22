package org.my.testdb;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDbConnection {

    public static void main(String[] args) {

        //setup connection variables
        String user = "****";
        String pass = "****";
        String jdbcURL = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";

        //get connection to db
        try {
            System.out.println("Connecting to database: " + jdbcURL);
            Connection myConn = DriverManager.getConnection(jdbcURL, user, pass);
            System.out.println("Connection successful");
            myConn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
