package com.example.taskmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.jar.Attributes;

public class HelloController {
    @FXML
    private RadioButton taskButton1;
    @FXML
    private RadioButton taskButton2;
    @FXML
    private RadioButton taskButton3;
    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private RadioButton level1;
    @FXML
    private RadioButton level2;
    @FXML
    private  RadioButton level3;
    @FXML
    private DatePicker date;
    @FXML
    private CheckBox completed;
    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void initialize() {
        listView.setItems(tasks);
    }
    @FXML
    private Label selectedText;


    @FXML
    protected void addTask() {
        Task newTask = null;
        if (taskButton1.isSelected()) {
            newTask = new HomeworkTask();
        } else if (taskButton2.isSelected()) {
            newTask = new MeetingTask();
        } else if (taskButton3.isSelected()) {
            newTask = new ShoppingTask();
        }

        if (newTask != null) {
            newTask.setTaskName(name.getText());
            newTask.setTaskDescription(description.getText());
            if (level1.isSelected()){
                newTask.setPriority(Priority.LOW);
            } else if (level2.isSelected()) {
                newTask.setPriority(Priority.MEDIUM);
            } else if (level3.isSelected()) {
                newTask.setPriority(Priority.HIGH);
            }

            LocalDate selectedDate = date.getValue();
            if (selectedDate != null) {
                Instant instant = Instant.from(selectedDate.atStartOfDay(ZoneId.systemDefault()));
                Date deadlineDate = Date.from(instant);
                newTask.setDeadline(deadlineDate);
            }

            tasks.add(newTask);
            name.clear();
            description.clear();
            taskButton1.setSelected(false);
            taskButton2.setSelected(false);
            taskButton3.setSelected(false);
            level1.setSelected(false);
            level2.setSelected(false);
            level3.setSelected(false);
            date.setValue(null);
            completed.setSelected(false);
        }
    }
    @FXML
    protected void onListClicked(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedText.setText(selectedTask.toString());
            if (selectedTask instanceof HomeworkTask) {
                taskButton1.setSelected(true);
            } else if (selectedTask instanceof MeetingTask) {
                taskButton2.setSelected(true);
            } else if (selectedTask instanceof ShoppingTask) {
                taskButton3.setSelected(true);
            }

            Priority priority = selectedTask.getPriority();
            if (priority == Priority.LOW) {
                level1.setSelected(true);
            } else if (priority == Priority.MEDIUM) {
                level2.setSelected(true);
            } else if (priority == Priority.HIGH) {
                level3.setSelected(true);
            }
            name.setText("" + selectedTask.getTaskName());
            description.setText("" + selectedTask.getDescription());
            Date deadline = selectedTask.getDeadline();
            if (deadline != null) {
                Instant instant = deadline.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                date.setValue(localDate);
            }

        } else {
            selectedText.setText("");
        }
        createTask();
    }

    @FXML
    protected void onTaskCompleted() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedTask.markAsComplete();
            deleteTask();
            name.clear();
            description.clear();

            taskButton1.setSelected(false);
            taskButton2.setSelected(false);
            taskButton3.setSelected(false);
            level1.setSelected(false);
            level2.setSelected(false);
            level3.setSelected(false);
            date.setValue(null);
            selectedText.setText("Task completed and removed");
            completed.setSelected(false);
        } else {
            selectedText.setText("No task selected");
            completed.setSelected(false);
        }


    }
    protected void createTask() {
        TaskDAO dao = new TaskDAO();

        String taskType = "";
        if (taskButton1.isSelected()) {
            taskType = "Homework Task";
        } else if (taskButton2.isSelected()) {
            taskType = "Meeting Task";
        } else if (taskButton3.isSelected()) {
            taskType = "Shopping Task";
        }

        String taskName = name.getText();
        String taskDescription = description.getText();
        String priority = "";
        if (level1.isSelected()) {
            priority = "LOW";
        } else if (level2.isSelected()) {
            priority = "MEDIUM";
        } else if (level3.isSelected()) {
            priority = "HIGH";
        }

        LocalDate selectedDate = date.getValue();

        dao.createTask(taskName, taskType, taskDescription, priority, selectedDate);

        dao.closeConnection();
    }

    protected void deleteTask(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if(selectedIndex >= 0){
            Task selectedTask = tasks.get(selectedIndex);

            TaskDAO dao = new TaskDAO();
            dao.deleteTask(selectedTask.getTaskName());
            dao.closeConnection();

            tasks.remove(selectedIndex);
        }
    }

    @FXML
    protected void updateTask(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Task selectedTask = tasks.get(selectedIndex);

            TaskDAO dao = new TaskDAO();
            dao.updateTask(selectedTask);
            dao.closeConnection();
        }
    }
}
