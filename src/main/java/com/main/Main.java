/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by byaxe on 26.11.16.
 */
public class Main extends Application {

    private AbstractApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private Parent rootNode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        try {
            applicationContext = new GenericXmlApplicationContext("application-context.xml");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

            fxmlLoader.setControllerFactory(applicationContext::getBean);

            rootNode = fxmlLoader.load();
        } catch (Exception e) {
            logger.error("Init method failed!", e);
        }
    }

    public void start(Stage stage) throws Exception {
        try {
            Scene scene = new Scene(rootNode);
            scene.getStylesheets().add("css/main.css");
            scene.getStylesheets().add("css/calendar.css");

            Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-light.ttf"), 14);

            stage.setScene(scene);

            stage.setResizable(false);
//            stage.setMaximized(false);

            stage.show();
        } catch (Exception e) {
            logger.error("start method failed!", e);
        }
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
    }
}
