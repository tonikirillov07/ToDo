package com.ds.todo.task;

public class Task {
    private long id;
    private final String taskName;
    private final String taskTime;
    private final String taskDescription;
    private final boolean isDone;
    private boolean canSendNotification = true;

    public Task(String taskTime, String taskName, String taskDescription, boolean isDone) {
        this.taskTime = taskTime;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    public Task(long id, String taskName, String taskTime, String taskDescription, boolean isDone) {
        this.id = id;
        this.taskName = taskName;
        this.taskTime = taskTime;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
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

    public boolean isDone() {
        return isDone;
    }

    public boolean isCanSendNotification() {
        return canSendNotification;
    }

    public void setCanSendNotification(boolean canSendNotification) {
        this.canSendNotification = canSendNotification;
    }
}
