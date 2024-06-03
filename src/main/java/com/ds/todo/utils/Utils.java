package com.ds.todo.utils;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.extendsNodes.ExtendedTextField;
import com.ds.todo.utils.actionListeners.IOnAction;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
}
