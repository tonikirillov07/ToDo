package com.ds.todo;

import com.ds.todo.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import static com.ds.todo.Constants.WINDOW_TITLE;
import static javafx.scene.paint.Color.TRANSPARENT;

public class Main extends Application {
    /*
    TODO: Разработка ToDo-приложение на Java с использованием Eclipse IDE
     c подвязанной базой данных, которое позволит пользователям планировать свое время:
     создавая, просматривая, редактируя и удаляя задачи. З основных момента: добавлять данные
     (записи о делах и встречах), экспортировать данные и проверка на свободное время.
     */

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Constants.START_WINDOW_WIDTH, Constants.START_WINDOW_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        scene.setFill(TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.getIcons().add(Utils.getImage("images/icon/window_icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}