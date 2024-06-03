package com.ds.todo;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Animations {
    public static void addTranslateByUpAnimationToNode(Node node, boolean byUp){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), node);
        translateTransition.setFromY(-200 * (byUp ? 1 : -1));
        translateTransition.setToY(0);
        translateTransition.setAutoReverse(true);
        translateTransition.setDelay(Duration.millis(30));
        translateTransition.play();

    }
}
