package com.example.taskmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 445);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        TaskDAO dao = new TaskDAO();
        System.out.println(dao);
        List<Task> tasks = dao.readTasks();
        dao.closeConnection();
        System.out.printf("%-20s %-20s %-30s %-15s %-15s\n", "Task Name", "Task Type", "Description", "Priority", "Deadline");
        for (Task task : tasks) {
            System.out.printf("%-20s %-20s %-30s %-15s %-15s\n",
                    task.getTaskName(),
                    task.getTaskType(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getDeadline());
        }
    }
}