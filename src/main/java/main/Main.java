/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by byaxe on 26.11.16.
 */
public class Main extends Application {

    private AbstractApplicationContext applicationContext;

    private Parent rootNode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        applicationContext = new GenericXmlApplicationContext("application-context.xml");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));

        fxmlLoader.setControllerFactory(applicationContext::getBean);

        rootNode = fxmlLoader.load();
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(rootNode);
        scene.getStylesheets().add("css/main.css");
        scene.getStylesheets().add("css/calendar.css");

        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-light.ttf"), 14);

        stage.setScene(scene);

        stage.setResizable(false);
        stage.setMaximized(false);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
    }
}
