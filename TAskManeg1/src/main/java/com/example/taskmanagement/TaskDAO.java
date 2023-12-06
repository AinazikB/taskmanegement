package com.example.taskmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TaskDAO {
    private Connection conn;

    public TaskDAO() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String pass = "1234";

        try {
            this.conn = DriverManager.getConnection(url, username, pass);
            System.out.println("Database successfully connected");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
