package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        scene.getStylesheets().add("css/style.css");

        stage.setScene(scene);

        stage.setResizable(false);
        stage.hide();

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
    }
}
