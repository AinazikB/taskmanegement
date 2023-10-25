package com.example.taskmanagement;

import java.util.Date;

public class HomeworkTask implements Task{
    @Override
    public String toString() {
        return "HomeworkTask: " +
                "taskName='" + taskName + '\'';
    }

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
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    @Override
    public void setDeadline(Date date) {
        this.deadline = date;
    }

}