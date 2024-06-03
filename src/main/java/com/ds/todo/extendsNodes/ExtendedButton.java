package com.ds.todo.extendsNodes;

import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.utils.actionListeners.IOnAction;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;

public class ExtendedButton extends Button {
    private final double width;
    private final double height;
    public static final double DEFAULT_WIDTH = 270d;
    public static final double DEFAULT_HEIGHT = 50d;

    public ExtendedButton(String s, double width, double height) {
        super(s);
        this.width = width;
        this.height = height;

        init();
    }

    private void init() {
        setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 20));
        setPrefSize(width, height);
        setMinSize(width, height);
        getStyleClass().add("button-next");
        setEffect(new DropShadow());
        setCursor(Cursor.HAND);
    }

    public void addAction(IOnAction onAction){
        setOnAction(actionEvent -> onAction.onAction());
    }
}
