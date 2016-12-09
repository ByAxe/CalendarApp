package controller;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.skins.JFXDatePickerSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.ApplicationServiceImpl;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by byaxe on 03.12.16.
 */
@Component
public class MainController implements Initializable {

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

    @FXML
    private JFXDatePickerSkin datePickerSkin;

    private IEventsService eventsService;

    private IGroupsService groupsService;

    private ResourceBundle resourceBundle;

    private URL location;

    private IApplicationService applicationService;

    public MainController() {
    }

    @Autowired
    public MainController(IEventsService eventsService, IGroupsService groupsService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.location = location;

        applicationService = new ApplicationServiceImpl(this, eventsService, groupsService);
    }

    public AnchorPane getCalendarDatePickerPane() {
        return calendarDatePickerPane;
    }

    public void setCalendarDatePickerPane(AnchorPane calendarDatePickerPane) {
        this.calendarDatePickerPane = calendarDatePickerPane;
    }

    public AnchorPane getCalendarSchedulePane() {
        return calendarSchedulePane;
    }

    public void setCalendarSchedulePane(AnchorPane calendarSchedulePane) {
        this.calendarSchedulePane = calendarSchedulePane;
    }

    public AnchorPane getCalendarRightPane() {
        return calendarRightPane;
    }

    public void setCalendarRightPane(AnchorPane calendarRightPane) {
        this.calendarRightPane = calendarRightPane;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getCalendarTab() {
        return calendarTab;
    }

    public void setCalendarTab(Tab calendarTab) {
        this.calendarTab = calendarTab;
    }

    public Tab getGroupsTab() {
        return groupsTab;
    }

    public void setGroupsTab(Tab groupsTab) {
        this.groupsTab = groupsTab;
    }

    public SplitPane getCalendarRootSplitPane() {
        return calendarRootSplitPane;
    }

    public void setCalendarRootSplitPane(SplitPane calendarRootSplitPane) {
        this.calendarRootSplitPane = calendarRootSplitPane;
    }

    public AnchorPane getCalendarLeftPane() {
        return calendarLeftPane;
    }

    public void setCalendarLeftPane(AnchorPane calendarLeftPane) {
        this.calendarLeftPane = calendarLeftPane;
    }

    public JFXDatePickerSkin getDatePickerSkin() {
        return datePickerSkin;
    }

    public void setDatePickerSkin(JFXDatePickerSkin datePickerSkin) {
        this.datePickerSkin = datePickerSkin;
    }
}
