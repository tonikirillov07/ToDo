package com.ds.todo.extendsNodes;

import com.ds.todo.Animations;
import com.ds.todo.Constants;
import com.ds.todo.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tile extends VBox {
    private final double width, height;
    public static final double DEFAULT_WIDTH = 606d;
    public static final double DEFAULT_HEIGHT = 645d;
    private Label titleLabel;

    public Tile(double width, double height) {
        this.width = width;
        this.height = height;

        init();
    }

    private void init() {
        setWidth(width);
        setHeight(height);
        setMaxWidth(width);
        getStyleClass().add("tile");
        setAlignment(Pos.TOP_CENTER);
        setEffect(new DropShadow());

        animate();
    }

    public void addTitle(String title){
        titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 32));
        VBox.setMargin(titleLabel, new Insets(15, 0, 0,0));

        addChild(titleLabel);
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    private void animate(){
        Animations.addTranslateByUpAnimationToNode(this, true);
    }

    public void addChild(Node node){
        getChildren().add(node);
    }
}
