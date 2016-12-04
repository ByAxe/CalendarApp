package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by byaxe on 03.12.16.
 */
@Component
public class Controller implements Initializable {

    @FXML
    public MenuBar menuBar;

    @FXML
    public JFXTabPane tabPane;

    @FXML
    public Tab calendarTab;

    @FXML
    public Tab groupsTab;

    private IEventsService eventsService;

    private IGroupsService groupsService;

    private ResourceBundle resourceBundle;

    private URL location;

    public Controller() {
    }

    @Autowired
    public Controller(IEventsService eventsService, IGroupsService groupsService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.location = location;

    }

}
