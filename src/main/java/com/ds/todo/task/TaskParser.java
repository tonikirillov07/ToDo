package com.ds.todo.task;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class TaskParser {
    @Contract(pure = true)
    public static @NotNull String extractTimeFromTaskTime(@NotNull String taskTime){
        int spaceIndex = taskTime.indexOf(" ") + 1;
        char[] charsArray = taskTime.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = spaceIndex; i < charsArray.length; i++) {
            stringBuilder.append(charsArray[i]);
        }

        return stringBuilder.toString();
    }

    public static @NotNull String extractDateFromTaskTime(@NotNull String taskTime){
        int spaceIndex = taskTime.indexOf(" ");
        char[] charsArray = taskTime.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < spaceIndex; i++) {
            stringBuilder.append(charsArray[i]);
        }

        return stringBuilder.toString();
    }

    public static boolean isTaskTimeCame(String taskTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(taskTime, formatter);

        LocalDateTime currentDateTime = LocalDateTime.now();

        return (dateTime.getHour() == currentDateTime.getHour()) & (dateTime.getMinute() == currentDateTime.getMinute())
                & (currentDateTime.getMonthValue() == dateTime.getMonthValue()) & (currentDateTime.getDayOfMonth() == dateTime.getDayOfMonth())
                & (currentDateTime.getYear() == dateTime.getYear());
    }
}
