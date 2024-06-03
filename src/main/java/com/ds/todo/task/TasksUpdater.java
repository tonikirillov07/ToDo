package com.ds.todo.task;

import com.ds.todo.utils.NotificationsSender;

import java.awt.*;
import java.util.List;

public class TasksUpdater {
    private boolean isTimeCanBeUpdated = true;

    public void start(List<Task> tasks){
        isTimeCanBeUpdated = true;

        new Thread(() -> {
            while (isTimeCanBeUpdated){
                tasks.forEach(task -> {
                    if(TaskParser.isTaskTimeCame(task.getTaskTime()) & task.isCanSendNotification()){
                        NotificationsSender.send("Sie haben für diese Zeit ein Geschäft geplant", task.getTaskName(), TrayIcon.MessageType.INFO);
                        task.setCanSendNotification(false);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void stop(){
        isTimeCanBeUpdated = false;
    }
}
