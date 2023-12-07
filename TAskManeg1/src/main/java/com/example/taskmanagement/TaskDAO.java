package com.example.taskmanagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public void createTask(String taskName, String taskType, String description, String priority, LocalDate deadline) {
        String sql = "INSERT INTO task (title, task_type, description, priority, deadline) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, taskName);
            stmt.setString(2, taskType);
            stmt.setString(3, description);
            stmt.setString(4, priority);

            if (deadline != null) {
                stmt.setDate(5, java.sql.Date.valueOf(deadline));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating task failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String taskType = resultSet.getString("task_type");
                String taskName = resultSet.getString("title");
                String description = resultSet.getString("description");
                String priority = resultSet.getString("priority");
                java.sql.Date deadlineDate = resultSet.getDate("deadline");

                Task task;
                if ("Homework Task".equals(taskType)) {
                    task = new HomeworkTask();
                } else if ("Meeting Task".equals(taskType)) {
                    task = new MeetingTask();
                } else if ("Shopping Task".equals(taskType)) {
                    task = new ShoppingTask();
                } else {
                    continue;
                }

                task.setTaskName(taskName);
                task.setTaskDescription(description);
                task.setPriority(Priority.valueOf(priority));

                if (deadlineDate != null) {
                    task.setDeadline(new Date(deadlineDate.getTime()));
                }

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET task_type = ?, description = ?, priority = ?, deadline = ? WHERE title = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskType());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().toString());
            stmt.setDate(4, task.getDeadline() != null ? new java.sql.Date(task.getDeadline().getTime()) : null);
            stmt.setString(5, task.getTaskName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("now rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String taskName) {
        String sql = "DELETE FROM task WHERE title = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taskName);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No rows were deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

