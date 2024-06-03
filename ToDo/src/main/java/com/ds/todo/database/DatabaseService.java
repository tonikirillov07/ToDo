package com.ds.todo.database;

import com.ds.todo.task.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ds.todo.database.DatabaseConstants.*;

public class DatabaseService {
    private static @Nullable Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + DatabaseConstants.DATABASE_PATH);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static @Nullable String getValue(String row, long id){
        return getValueWithWhereValue(row, ID_ROW, String.valueOf(id));
    }

    public static @Nullable String getValueWithWhereValue(String row, String whereRow, String whereValue){
        try {
            String select = "SELECT " + row + " FROM " + TABLE_NAME + " WHERE " + whereRow + "='" + whereValue + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            String returnValue = resultSet.getString(1);

            preparedStatement.close();
            resultSet.close();

            return returnValue;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void changeValue(String row, String newValue, long id){
        try{
            String change = "UPDATE " + TABLE_NAME + " SET " + row + "=" + "'" + newValue + "'" + " WHERE " + ID_ROW + "=" + "'" + id + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(change);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteTask(long id){
        try{
            String delete = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_ROW + "='" + id + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(delete);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(@NotNull String value){
        return value.equals("1") | value.equals("true") | value.equals("yes");
    }

    public static boolean addTask(Task task){
        try {
            String insert = "INSERT INTO " + TABLE_NAME + "(" + TASK_NAME_ROW + "," + TASK_TIME_ROW + "," + TASK_DESCRIPTION_ROW + ")" + " VALUES(?,?,?)";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(insert);
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskTime());
            preparedStatement.setString(3, task.getTaskDescription());
            preparedStatement.executeUpdate();

            preparedStatement.close();

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static @Nullable List<Task> getAllTasks(){
        try {
            List<Task> allTasks = new ArrayList<>();

            String getAll = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID_ROW + " ASC";
            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(getAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                allTasks.add(new Task(Long.parseLong(resultSet.getString(ID_ROW)), resultSet.getString(TASK_NAME_ROW), resultSet.getString(TASK_TIME_ROW), resultSet.getString(TASK_DESCRIPTION_ROW),
                        getBoolean(resultSet.getString(TASK_IS_DONE_ROW))));
            }

            resultSet.close();
            preparedStatement.close();

            return allTasks;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteAllTasks(){
        try{
            String deleteAll = "DELETE FROM " + TABLE_NAME;
            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(deleteAll);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            resetAutoincrement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void resetAutoincrement(){
        try {
            String resetAI = "DELETE FROM sqlite_sequence WHERE name='" + TABLE_NAME + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(resetAI);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
