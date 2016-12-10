package service.api;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by byaxe on 08.12.16.
 */
public interface IApplicationService {

    void calendarCreateNewEvent(ActionEvent actionEvent);

    void calendarCleanEventForm(ActionEvent actionEvent);

    void initialize(URL location, ResourceBundle resources);
}
