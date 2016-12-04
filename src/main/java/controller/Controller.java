package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane calendarDatePickerPane;

    @FXML
    private AnchorPane calendarSchedulePane;

    @FXML
    private AnchorPane calendarRightPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab calendarTab;

    @FXML
    private Tab groupsTab;

    @FXML
    private SplitPane calendarRootSplitPane;

    @FXML
    private AnchorPane calendarLeftPane;

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
