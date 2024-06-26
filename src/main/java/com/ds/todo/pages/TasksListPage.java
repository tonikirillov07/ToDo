package com.ds.todo.pages;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.task.Task;
import com.ds.todo.database.DatabaseService;
import com.ds.todo.extendsNodes.ExtendedButton;
import com.ds.todo.extendsNodes.TaskTile;
import com.ds.todo.task.TasksUpdater;
import com.ds.todo.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.ds.todo.utils.Utils.createLabel;

public class TasksListPage extends Page{
    private final TasksUpdater tasksUpdater;

    public TasksListPage(Page previousPage, VBox mainContentVbox, String title) {
        super(previousPage, mainContentVbox, title);

        tasksUpdater = new TasksUpdater();
    }

    @Override
    public void onOpen() {
        getTile().addTitle(getTitle());
        getTile().setMaxHeight(709d);

        createScrollPane();
        createAddTaskButton();
        createClearTasksButton();
        createExportLabelButton();
    }

    private void createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox contentVbox = new VBox();
        contentVbox.setAlignment(Pos.TOP_CENTER);
        contentVbox.setSpacing(10d);
        scrollPane.setPadding(new Insets(15d));

        loadTasks(contentVbox);

        scrollPane.setContent(contentVbox);
        addNodeToTile(scrollPane);
    }

    private void loadTasks(VBox contentVbox){
        List<Task> allTasks = DatabaseService.getAllTasks();

        if(Objects.requireNonNull(allTasks).isEmpty()){
            createNoTasksLabel(contentVbox);
            return;
        }

        tasksUpdater.start(allTasks);

        allTasks.forEach(task -> {
            TaskTile taskTile = new TaskTile(TaskTile.DEFAULT_WIDTH, TaskTile.DEFAULT_HEIGHT, String.valueOf(task.getId()), task.getTaskName(), task.getTaskTime(), task.getTaskDescription());
            if(task.isDone())
                taskTile.markAsDone();

            Utils.addActionToNode(taskTile, () -> {
                EditTaskPage editTaskPage = new EditTaskPage(this, getMainContentVbox(), "Aufgabe bearbeiten", taskTile.getTaskId(), taskTile.getName(), taskTile.getDescription(), task.getTaskTime());
                editTaskPage.open();
            });

            contentVbox.getChildren().add(taskTile);
        });
    }

    private void createAddTaskButton() {
        ExtendedButton addTaskExtendedButton = new ExtendedButton("Aufgabe hinzufügen", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        VBox.setMargin(addTaskExtendedButton, new Insets(25d, 0d, 10d, 0d));
        addNodeToTile(addTaskExtendedButton);

        addTaskExtendedButton.addAction(() -> {
            CreateTaskPage createTaskPage = new CreateTaskPage(this, getMainContentVbox(), "Aufgabe erstellen");
            createTaskPage.open();
        });
    }

    private void createClearTasksButton() {
        ExtendedButton clearTasksExtendedButton = new ExtendedButton("Alle Aufgaben löschen", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        clearTasksExtendedButton.setStyle("-fx-background-color: rgb(187, 71, 71);");
        VBox.setMargin(clearTasksExtendedButton, new Insets(10d, 0d, 25d, 0d));
        addNodeToTile(clearTasksExtendedButton);

        clearTasksExtendedButton.addAction(() -> {
            DatabaseService.deleteAllTasks();
            tasksUpdater.stop();

            reopen();
        });

        clearTasksExtendedButton.setDisable(Objects.requireNonNull(DatabaseService.getAllTasks()).isEmpty());
    }

    private void createNoTasksLabel(@NotNull VBox contentVbox){
        Label label = new Label("Sie haben noch keine geplanten Aufgaben");
        label.setTextFill(javafx.scene.paint.Color.LIGHTGRAY);
        label.setUnderline(true);
        label.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));
        VBox.setMargin(label, new Insets(35d, 0d, 35d, 0d));

        contentVbox.getChildren().add(label);
    }

    private void createExportLabelButton(){
        Label exportLabelButton = createLabel("Aufgaben exportieren");
        exportLabelButton.setTextFill(Color.LIGHTGRAY);
        exportLabelButton.setUnderline(true);
        exportLabelButton.setCursor(Cursor.HAND);

        exportLabelButton.setOnMouseEntered(mouseEvent -> exportLabelButton.setOpacity(0.5d));
        exportLabelButton.setOnMouseExited(mouseEvent -> exportLabelButton.setOpacity(1d));
        VBox.setMargin(exportLabelButton, new Insets(15d, 0, 10, 0d));

        addNodeToTile(exportLabelButton);

        List<Task> allTasks = DatabaseService.getAllTasks();

        assert allTasks != null;
        exportLabelButton.setDisable(allTasks.isEmpty());
        Utils.addActionToNode(exportLabelButton, () -> exportAll(allTasks));
    }

    private void exportAll(@NotNull List<Task> taskList){
        try {
            File file = new File("exported-to-dos.txt");
            if(file.exists())
                file.delete();

            file.createNewFile();

            for (Task task : taskList) {
                try {
                    FileWriter fileWriter = new FileWriter(file, true);
                    fileWriter.write("\n\n" + task.getId() + ". " + task.getTaskName() + " (" + task.getTaskTime() + ")\n" + task.getTaskDescription());
                    fileWriter.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
