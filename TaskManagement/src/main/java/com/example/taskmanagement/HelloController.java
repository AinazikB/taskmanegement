package com.example.taskmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
            tasks.add(newTask);
            name.clear();
            description.clear();
        }
    }
    @FXML
    protected void onListClicked(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedText.setText(selectedTask.toString());
        } else {
            selectedText.setText("");
        }
    }
    @FXML
    protected void onTaskCompleted() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedTask.markAsComplete();
            completed.setSelected(true);
        }
    }
}
