/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package main.preloader;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.sun.javafx.font.PrismFontFactory.isEmbedded;
import static javafx.application.Preloader.StateChangeNotification.Type.BEFORE_START;

/**
 * Created by A.Litvinau on 1/8/2017.
 */
public class SimplePreloader extends Preloader {
    JFXProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new JFXProgressBar();
        bar.setPrefHeight(20);
        bar.setPrefWidth(125);
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == BEFORE_START) {
            if (isEmbedded && stage.isShowing()) {
                //fade out, hide stage at the end of animation
                FadeTransition ft = new FadeTransition(
                        Duration.millis(1000), stage.getScene().getRoot());
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                final Stage s = stage;
                ft.setOnFinished(t -> s.hide());
                ft.play();
            } else {
                stage.hide();
            }
        }
    }
}
