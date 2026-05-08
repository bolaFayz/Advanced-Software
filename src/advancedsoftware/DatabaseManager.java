package advancedsoftware;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class DatabaseManager {
    
        private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/SoftCoreDB";
                String user = "root";
                String password = "Root1234!"; 
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("connected successfully");
            } catch (SQLException e) {
                System.err.println("Database Connection Failed: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}