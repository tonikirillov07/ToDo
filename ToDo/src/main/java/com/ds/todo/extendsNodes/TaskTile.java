package com.ds.todo.extendsNodes;

import com.ds.todo.Animations;
import com.ds.todo.Constants;
import com.ds.todo.Main;
import com.ds.todo.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TaskTile extends HBox {
    public static final double DEFAULT_WIDTH = 410d;
    public static final double DEFAULT_HEIGHT = 72d;
    private final double width;
    private final double height;
    private final String id, name, time, description;

    public TaskTile(double width, double height, String id, String name, String time, String description) {
        this.width = width;
        this.height = height;
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;

        init();
    }

    private void init() {
        getStyleClass().add("task-tile");
        setPrefSize(width, height);
        setMinSize(width, height);
        VBox.setMargin(this, new Insets(8d, 40d, 8d, 40d));
        setEffect(new DropShadow());
        
        createIdHbox();
        createNameHbox();
        createTimeHbox();

        Animations.addTranslateByUpAnimationToNode(this, false);
    }

    private void createTimeHbox() {
        Label timeLabel = Utils.createLabel(time);
        timeLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));

        createComponentHbox(timeLabel, Pos.CENTER_RIGHT);
    }

    private void createNameHbox() {
        Label nameLabel = Utils.createLabel(name);
        nameLabel.setTooltip(new Tooltip(name));
        nameLabel.setMaxWidth(202d);

        createComponentHbox(nameLabel, Pos.CENTER);
    }

    private void createIdHbox() {
        createComponentHbox(Utils.createLabel(id + "."), Pos.CENTER_LEFT);
    }

    private void createComponentHbox(Node node, Pos alignment){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(13d));
        hBox.setAlignment(alignment);
        hBox.getChildren().add(node);
        HBox.setHgrow(hBox, Priority.ALWAYS);

        getChildren().add(hBox);
    }

    public void markAsDone(){
        setOpacity(0.5d);
        setStyle("-fx-border-color: rgb(3, 129, 0);");
    }

    public String getTaskId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
