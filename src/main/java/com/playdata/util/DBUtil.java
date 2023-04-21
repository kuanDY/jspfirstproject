package com.playdata.util;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection getConnection(ServletContext context) throws SQLException {
        String url = context.getInitParameter("DB_URL");
        String user = context.getInitParameter("DB_USER");
        String password = context.getInitParameter("DB_PASSWORD");

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, password);
    }
}
