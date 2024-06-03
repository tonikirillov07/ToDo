package com.ds.todo.pages;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.database.DatabaseService;
import com.ds.todo.extendsNodes.ExtendedButton;
import com.ds.todo.extendsNodes.ExtendedTextField;
import com.ds.todo.task.Task;
import com.ds.todo.task.TaskParser;
import com.ds.todo.utils.Utils;
import com.ds.todo.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Objects;

import static com.ds.todo.database.DatabaseConstants.*;
import static com.ds.todo.utils.Utils.addHboxWithDescriptionAndNode;
import static com.ds.todo.utils.Utils.presentLocalDateInNormalView;

public class EditTaskPage extends Page{
    private final String taskId, taskName, taskDescription, taskTime;
    private ExtendedTextField extendedTextFieldNewTaskName, extendedTextFieldNewTaskDescription;
    private DatePicker newDatePicker;
    private ComboBox<String> newTimeComboBox;
    private CheckBox isDoneCheckBox;

    public EditTaskPage(Page previousPage, VBox mainContentVbox, String title, String taskId, String taskName, String taskDescription, String taskTime) {
        super(previousPage, mainContentVbox, title);
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
    }

    @Override
    public void onOpen() {
        getTile().addTitle(getTitle());

        createDescriptionText();
        createNewNameTextField();
        createNewDescriptionTextField();
        createNewDatePicker();
        createNewTimeComboBox();
        createIsDoneCheckBox();
        createDeleteCurrentTaskButton();
        createApplyButton();
    }

    private void createIsDoneCheckBox() {
        isDoneCheckBox = new CheckBox("");
        isDoneCheckBox.setSelected(DatabaseService.getBoolean(Objects.requireNonNull(DatabaseService.getValue(TASK_IS_DONE_ROW, Long.parseLong(taskId)))));
        addHboxWithDescriptionAndNode("Erledigt: ", isDoneCheckBox, this);

        isDoneCheckBox.setOnAction(actionEvent -> DatabaseService.changeValue(TASK_IS_DONE_ROW, String.valueOf(isDoneCheckBox.isSelected()), Long.parseLong(taskId)));
    }

    private void createApplyButton() {
        ExtendedButton applyButton = initBackButton(0d);
        applyButton.setText("Speichern");
        VBox.setMargin(applyButton, new Insets(6d, 0, 18d, 0d));

        applyButton.addAction(this::onApplyButtonAction);

    }

    private void onApplyButtonAction() {
        long taskIdLong = Long.parseLong(taskId);
        String previousTime = DatabaseService.getValue(TASK_TIME_ROW, taskIdLong);

        if(!extendedTextFieldNewTaskName.getText().isEmpty())
            DatabaseService.changeValue(TASK_NAME_ROW, extendedTextFieldNewTaskName.getText(), taskIdLong);

        if(!extendedTextFieldNewTaskDescription.getText().isEmpty())
            DatabaseService.changeValue(TASK_DESCRIPTION_ROW, extendedTextFieldNewTaskDescription.getText(), taskIdLong);

        if(newTimeComboBox.getValue() != null & newDatePicker.getValue() == null){
            String oldTaskDate = TaskParser.extractDateFromTaskTime(Objects.requireNonNull(DatabaseService.getValue(TASK_TIME_ROW, taskIdLong)));
            DatabaseService.changeValue(TASK_TIME_ROW, oldTaskDate + " " + newTimeComboBox.getValue(), taskIdLong);
        }

        if(newDatePicker.getValue() != null & newTimeComboBox.getValue() == null){
            String oldTaskTime = TaskParser.extractTimeFromTaskTime(Objects.requireNonNull(DatabaseService.getValue(TASK_TIME_ROW, taskIdLong)));
            DatabaseService.changeValue(TASK_TIME_ROW, presentLocalDateInNormalView(newDatePicker.getValue()) + " " + oldTaskTime, taskIdLong);
        }

        if(newDatePicker.getValue() != null & newTimeComboBox.getValue() != null){
            DatabaseService.changeValue(TASK_TIME_ROW, presentLocalDateInNormalView(newDatePicker.getValue()) + " " + newTimeComboBox.getValue(), taskIdLong);
        }

        String newTime = DatabaseService.getValue(TASK_TIME_ROW, taskIdLong);

        boolean hasTaskAtSomeTime = false;
        List<Task> taskList = DatabaseService.getAllTasks();

        assert taskList != null;
        for (Task task : taskList) {
            if(task.getTaskTime().equals(newTime) & task.getId() != taskIdLong){
                hasTaskAtSomeTime = true;
                break;
            }
        }

        if(hasTaskAtSomeTime){
            ErrorDialog.show(new IllegalArgumentException("Zu diesem Zeitpunkt gibt es bereits Aufgaben"));
            DatabaseService.changeValue(TASK_TIME_ROW, previousTime, taskIdLong);
            return;
        }

        goToPreviousPage();
    }

    private void createDeleteCurrentTaskButton() {
        ExtendedButton deleteCurrentTaskExtendedButton = new ExtendedButton("Löschen", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        deleteCurrentTaskExtendedButton.setStyle("-fx-background-color: rgb(187, 71, 71);");
        VBox.setMargin(deleteCurrentTaskExtendedButton, new Insets(50d, 0d, 10d, 0d));
        addNodeToTile(deleteCurrentTaskExtendedButton);

        deleteCurrentTaskExtendedButton.addAction(() -> {
            DatabaseService.deleteTask(Long.parseLong(taskId));
            goToPreviousPage();
        });
    }

    private void createNewTimeComboBox() {
        newTimeComboBox = new ComboBox<>();
        newTimeComboBox.getItems().addAll(Utils.getTimesList());

        addHboxWithDescriptionAndNode("Die neue Zeit: ", newTimeComboBox, this);
    }

    private void createNewDatePicker() {
        newDatePicker = new DatePicker();
        Utils.addHboxWithDescriptionAndNode("Neues Datum", newDatePicker, this);
    }

    private void createNewDescriptionTextField() {
        extendedTextFieldNewTaskDescription = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Neue Beschreibung", Utils.getImage("images/taskDescription.png"));
        VBox.setMargin(extendedTextFieldNewTaskDescription, new Insets(15d, 40d, 0, 40d));
        addNodeToTile(extendedTextFieldNewTaskDescription);
    }

    private void createNewNameTextField() {
        extendedTextFieldNewTaskName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Neuer Name", Utils.getImage("images/taskName.png"));
        VBox.setMargin(extendedTextFieldNewTaskName, new Insets(30d, 40d, 0, 40d));
        addNodeToTile(extendedTextFieldNewTaskName);
    }

    private void createDescriptionText() {
        String mainInfo = "Name: " + taskName + "\n" +
                "Die Beschreibung: " + taskDescription + "\n" +
                "Die Zeit: " + taskTime;

        Label mainInfoLabel = Utils.createLabel(mainInfo);
        mainInfoLabel.setWrapText(true);
        mainInfoLabel.setLineSpacing(8d);
        mainInfoLabel.setTooltip(new Tooltip(mainInfo));
        mainInfoLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 17d));
        VBox.setMargin(mainInfoLabel, new Insets(60d, 38d, 0d, 38d));

        addNodeToTile(mainInfoLabel);
    }
}
