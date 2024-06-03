package com.ds.todo;

import com.ds.todo.pages.TasksListPage;
import com.ds.todo.utils.actionListeners.IOnAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static com.ds.todo.Constants.START_WINDOW_HEIGHT;
import static com.ds.todo.Constants.START_WINDOW_WIDTH;
import static com.ds.todo.utils.Utils.addActionToNode;

public class MainController {
    @FXML
    private ImageView closeButtonImageView;

    @FXML
    private HBox headerHbox;

    @FXML
    private VBox mainContentvbox;

    @FXML
    private ImageView minimizeButtonImageView;

    @FXML
    private Label titleLabel;
    private double windowX, windowY;

    @FXML
    void initialize() {
        initHeader();
        initBackground();

        TasksListPage tasksListPage = new TasksListPage(null, mainContentvbox, "Aufgabenliste");
        tasksListPage.open();
    }

    private void initBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/background.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, new BackgroundSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT, false, false, true, true));
        mainContentvbox.setBackground(new Background(backgroundImage));
    }

    private void initHeader() {
        titleLabel.setText("To-do-Anwendung");
        titleLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));

        initControlButtons();
        initHeaderDrug();
    }

    private void initHeaderDrug() {
        headerHbox.setOnMousePressed(event -> {
            windowX = event.getSceneX();
            windowY = event.getSceneY();
        });

        headerHbox.setOnMouseDragged(event -> {
            headerHbox.getScene().getWindow().setX(event.getScreenX() - windowX);
            headerHbox.getScene().getWindow().setY(event.getScreenY() - windowY);
        });

    }

    private void initControlButtons() {
        addActionToNode(closeButtonImageView, this::close);
        addActionToNode(minimizeButtonImageView, this::minimize);
    }

    private Stage getStage(){
        return (Stage) closeButtonImageView.getScene().getWindow();
    }

    private void minimize(){
        getStage().setIconified(true);
    }

    private void close(){
        getStage().close();
        Platform.exit();
        System.exit(0);
    }
}

