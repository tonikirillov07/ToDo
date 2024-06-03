package com.ds.todo.pages;

import com.ds.todo.extendsNodes.ExtendedButton;
import com.ds.todo.extendsNodes.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class Page {
    private final Page previousPage;
    private final VBox mainContentVbox;
    private final String title;
    private Tile tile;

    public Page(Page previousPage, VBox mainContentVbox, String title) {
        this.previousPage = previousPage;
        this.mainContentVbox = mainContentVbox;
        this.title = title;

        applyDefaultPaddingsToTile();
    }

    public void open(){
        if(previousPage != null)
            previousPage.close();

        createTile();
        onOpen();
    }

    public void close(){
        mainContentVbox.getChildren().clear();
    }

    private void createTile(){
        tile = new Tile(Tile.DEFAULT_WIDTH, Tile.DEFAULT_HEIGHT);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.getChildren().add(tile);

        addNodeToPage(vBox);
    }

    public ExtendedButton initBackButton(double topMargin) {
        ExtendedButton backButton = new ExtendedButton("Zurück", ExtendedButton.DEFAULT_WIDTH, ExtendedButton.DEFAULT_HEIGHT);
        backButton.addAction(this::goToPreviousPage);
        VBox.setMargin(backButton, new Insets(topMargin, 40d, 30d, 40d));
        addNodeToTile(backButton);

        return backButton;
    }

    public void goToPreviousPage(){
        if(previousPage != null) {
            close();
            previousPage.open();
        }
    }

    public void reopen(){
        close();
        open();
    }

    public void addNodeToPage(Node node){
        mainContentVbox.getChildren().add(node);
    }

    public void addNodeToTile(Node node){
        if(tile != null)
            tile.getChildren().add(node);
        else
            createTile();
    }

    public Tile getTile() {
        return tile;
    }

    public String getTitle() {
        return title;
    }

    private void applyDefaultPaddingsToTile(){
        getMainContentVbox().setPadding(new Insets(0d, 160d, 0d, 160d));
    }

    public VBox getMainContentVbox() {
        return mainContentVbox;
    }

    public abstract void onOpen();
}
