package com.ds.todo;

import com.ds.todo.task.TaskParser;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String taskTime = "3/6/2024 13:22";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(taskTime, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        boolean isCame = (dateTime.getHour() == currentDateTime.getHour()) & (dateTime.getMinute() == currentDateTime.getMinute())
                & (currentDateTime.getMonthValue() == dateTime.getMonthValue()) & (currentDateTime.getDayOfMonth() == dateTime.getDayOfMonth())
                & (currentDateTime.getYear() == dateTime.getYear());

        System.out.println(isCame);
    }
}
