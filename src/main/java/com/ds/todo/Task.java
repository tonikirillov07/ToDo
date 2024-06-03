package com.ds.todo;

public class Task {
    private long id;
    private final String taskName;
    private final String taskTime;
    private final String taskDescription;

    public Task(String taskTime, String taskName, String taskDescription) {
        this.taskTime = taskTime;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public Task(long id, String taskName, String taskTime, String taskDescription) {
        this.id = id;
        this.taskName = taskName;
        this.taskTime = taskTime;
        this.taskDescription = taskDescription;
    }

    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}
