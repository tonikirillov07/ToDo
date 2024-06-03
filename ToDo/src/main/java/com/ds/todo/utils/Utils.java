package com.ds.todo.utils;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.extendsNodes.ExtendedTextField;
import com.ds.todo.extendsNodes.Tile;
import com.ds.todo.pages.Page;
import com.ds.todo.utils.actionListeners.IOnAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Utils {
    public static void addActionToNode(@NotNull Node node, IOnAction onAction){
        node.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY)
                onAction.onAction();
        });
    }

    @Contract("_ -> new")
    public static @NotNull Image getImage(String resourcePath){
        return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(resourcePath)));
    }

    public static @NotNull List<ExtendedTextField> getEmptyFieldsFromArray(ExtendedTextField[] textFields){
        List<ExtendedTextField> textInputControlList = new ArrayList<>();

        Arrays.stream(textFields).toList().forEach(textInputControl -> {
            if (textInputControl == null)
                    return;

            if (textInputControl.getText().trim().isEmpty())
                textInputControlList.add(textInputControl);
        });

        return textInputControlList;
    }

    public static @NotNull Label createLabel(String text){
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));

        return label;
    }

    public static void addHboxWithDescriptionAndNode(String description, Node node, @NotNull Page page){
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
        page.addNodeToTile(hBox);
    }

    public static @NotNull List<String> getTimesList(){
        List<String> timesList = new ArrayList<>();

        for (int i = 1; i < 24; i++) {
            String hour = (i < 10 ? "0" + i : String.valueOf(i));
            timesList.add(hour + ":00");

            for (int j = 1; j < 60; j++) {
                timesList.add(hour + ":" + (j < 10 ? "0" + j : String.valueOf(j)));
            }
        }

        return timesList;
    }

    public static @NotNull String presentLocalDateInNormalView(@NotNull LocalDate localDate){
        return localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear();
    }
}
