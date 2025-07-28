/**
 * 
 */
package com.flipkart.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/*
 *@Author : "Aditi Baveja"
 *@ClassName: "DBUtils"
 *@Exceptions: "java.sql.SQLException, java.io.FileNotFoundException, java.io.IOException"
 *@Version : "1.0"
 *@See : "java.sql.Connection, java.sql.DriverManager, java.util.Properties"
 */
public class DBUtils {
private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Properties prop = new Properties();
                // This is the correct way to load a resource from src/main/resources
                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("config.properties");

                if (inputStream == null) {
                    System.out.println("Sorry, unable to find config.properties");
                    return null;
                }

                prop.load(inputStream);
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");

                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
