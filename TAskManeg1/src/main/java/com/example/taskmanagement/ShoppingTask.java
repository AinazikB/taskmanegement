package com.example.taskmanagement;

import java.util.Date;

public class ShoppingTask implements Task{
    private String taskName;
    private String taskDescription;
    private boolean completed;
    private Priority priority;
    private Date deadline;
    @Override
    public void setTask(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Date getDeadline() {
        return deadline;
    }

    @Override
    public String getDescription() {
        return taskDescription;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public void markAsComplete() {
        completed = true;
    }

    @Override
    public String toString() {
        return "ShoppingTask: " + taskName + '\'';
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void setDeadline(Date date) {
        this.deadline = date;
    }
}
