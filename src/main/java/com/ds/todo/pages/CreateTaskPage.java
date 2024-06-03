package com.ds.todo.pages;

import com.ds.todo.task.Task;
import com.ds.todo.database.DatabaseService;
import com.ds.todo.extendsNodes.ExtendedButton;
import com.ds.todo.extendsNodes.ExtendedTextField;
import com.ds.todo.utils.Utils;
import com.ds.todo.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

import static com.ds.todo.utils.Utils.*;

public class CreateTaskPage extends Page{
    private ExtendedTextField extendedTextFieldTaskName, extendedTextFieldTaskDescription;
    private DatePicker datePicker;
    private ComboBox<String> timeComboBox;

    public CreateTaskPage(Page previousPage, VBox mainContentVbox, String title) {
        super(previousPage, mainContentVbox, title);
    }

    @Override
    public void onOpen() {
        getTile().addTitle(getTitle());

        createTaskNameTextField();
        createTaskDescriptionTextField();
        createDatePicker();
        createTimeComboBox();

        createNextButton();
        initBackButton(10d);
    }

    private void createTimeComboBox() {
        timeComboBox = new ComboBox<>();
        timeComboBox.getItems().addAll(getTimesList());

        addHboxWithDescriptionAndNode("Zeit: ", timeComboBox, this);
    }

    private void createNextButton() {
        ExtendedButton nextButton = new ExtendedButton("Weiter", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        nextButton.setStyle("-fx-background-color: rgb(47, 38, 38);");
        nextButton.addAction(this::addTask);
        VBox.setMargin(nextButton, new Insets(100d, 40d, 0d, 40d));
        addNodeToTile(nextButton);
    }

    private void addTask(){
        List<ExtendedTextField> emptyFields = getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldTaskName, extendedTextFieldTaskDescription});
        if(!emptyFields.isEmpty()){
            emptyFields.forEach(ExtendedTextField::setError);
            return;
        }

        if(datePicker.getValue() == null){
            ErrorDialog.show(new IllegalArgumentException("Wählen Sie ein Datum aus, bevor Sie eine Aufgabe hinzufügen"));
            return;
        }

        if(timeComboBox.getValue() == null){
            ErrorDialog.show(new IllegalArgumentException("Wählen Sie die Zeit aus, bevor Sie eine Aufgabe hinzufügen"));
            return;
        }

        LocalDate localDate = datePicker.getValue();
        String time = Utils.presentLocalDateInNormalView(localDate) + " " + timeComboBox.getValue();

        List<Task> allTasks = DatabaseService.getAllTasks();
        boolean hasTaskAtSomeTime = false;

        assert allTasks != null;
        for (Task task : allTasks) {
            if(task.getTaskTime().equals(time)){
                ErrorDialog.show(new IllegalArgumentException("Es gibt bereits zu tun"));
                hasTaskAtSomeTime = true;

                break;
            }
        }

        if(hasTaskAtSomeTime)
            return;

        Task task = new Task(time, extendedTextFieldTaskName.getText(), extendedTextFieldTaskDescription.getText(), false);
        DatabaseService.addTask(task);

        goToPreviousPage();
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        addHboxWithDescriptionAndNode("Datum: ", datePicker, this);
    }

    private void createTaskDescriptionTextField() {
        extendedTextFieldTaskDescription = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Beschreibung der Aufgabe", Utils.getImage("images/taskDescription.png"));
        VBox.setMargin(extendedTextFieldTaskDescription, new Insets(15d, 40d, 0, 40d));
        addNodeToTile(extendedTextFieldTaskDescription);
    }

    private void createTaskNameTextField() {
        extendedTextFieldTaskName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Name der Aufgabe", Utils.getImage("images/taskName.png"));
        VBox.setMargin(extendedTextFieldTaskName, new Insets(50d, 40d, 0, 40d));
        addNodeToTile(extendedTextFieldTaskName);
    }
}
