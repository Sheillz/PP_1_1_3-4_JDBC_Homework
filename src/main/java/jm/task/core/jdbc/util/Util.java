package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class Util {
        private static final String url = "jdbc:mysql://localhost:3306/mydbtest";
        private static final String neme = "root";
        private static final String password = "777514";

        public static Connection getConnection() {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, neme, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return connection;
        }
    }




