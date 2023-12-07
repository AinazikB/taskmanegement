package com.example.taskmanagement;
import java.util.Date;

public interface Task {
    String getTaskType();
    void setTaskType(String taskType);
    void setTask(String taskName, String taskDescription);
    void setTaskName(String taskName);
    String getTaskName();
    String getDescription();
    void setTaskDescription(String taskDescription);
    void markAsComplete();
    void setPriority(Priority priority);
    Priority getPriority();
    void setDeadline(Date date);
    Date getDeadline();
}
