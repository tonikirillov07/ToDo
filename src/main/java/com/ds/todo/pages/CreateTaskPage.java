package com.ds.todo.pages;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.Task;
import com.ds.todo.database.DatabaseService;
import com.ds.todo.extendsNodes.ExtendedButton;
import com.ds.todo.extendsNodes.ExtendedTextField;
import com.ds.todo.utils.Utils;
import com.ds.todo.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ds.todo.utils.Utils.getEmptyFieldsFromArray;

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

        addHboxWithDescriptionAndNode("Die Zeit: ", timeComboBox);
    }

    private void createNextButton() {
        ExtendedButton nextButton = new ExtendedButton("Weiter", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        nextButton.setStyle("-fx-background-color: rgb(47, 38, 38);");
        nextButton.addAction(this::addTask);
        VBox.setMargin(nextButton, new Insets(100d, 40d, 0d, 40d));
        addNodeToTile(nextButton);
    }

    private @NotNull List<String> getTimesList(){
        List<String> timesList = new ArrayList<>();

        for (int i = 1; i < 24; i++) {
            timesList.add((i < 10 ? "0" + i : String.valueOf(i)) + ":00:00");
        }

        return timesList;
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
        String time = localDate.getDayOfMonth() + "/" + localDate.getMonth() + "/" + localDate.getYear() + " " + timeComboBox.getValue();

        Task task = new Task(time, extendedTextFieldTaskName.getText(), extendedTextFieldTaskDescription.getText());
        DatabaseService.addTask(task);

        goToPreviousPage();
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        addHboxWithDescriptionAndNode("Datum: ", datePicker);
    }

    private void addHboxWithDescriptionAndNode(String description, Node node){
        HBox hBox = new HBox();
        VBox.setMargin(hBox, new Insets(15d, 40d, 0, 40d));

        HBox labelHbox = new HBox();
        labelHbox.setAlignment(Pos.CENTER_LEFT);
        labelHbox.setPadding(new Insets(8d));
        HBox.setHgrow(labelHbox, Priority.ALWAYS);

        HBox nodeHbox = new HBox();
        nodeHbox.setAlignment(Pos.CENTER_RIGHT);
        nodeHbox.setPadding(new Insets(8d));
        nodeHbox.getChildren().add(node);
        HBox.setHgrow(nodeHbox, Priority.ALWAYS);

        Label label = new Label(description);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 16d));
        labelHbox.getChildren().add(label);

        hBox.getChildren().addAll(labelHbox, nodeHbox);
        addNodeToTile(hBox);
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
