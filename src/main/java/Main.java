import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import service.api.IEventsService;

/**
 * Created by byaxe on 26.11.16.
 */
public class Main extends Application {

    private AbstractApplicationContext applicationContext;

    private IEventsService eventsService;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        applicationContext = new GenericXmlApplicationContext("application-context.xml");

        eventsService = (IEventsService) applicationContext.getBean("eventsServiceImpl");
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
    }
}
