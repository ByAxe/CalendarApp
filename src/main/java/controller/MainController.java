package controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public Label calendarStartDateLabel;
    public JFXTextField calendarEventTitlePicker;
    public JFXComboBox calendarNoticePeriodPicker;
    public Label calendarNoticePeriodLabel;
    public JFXComboBox calendarPriorityPicker;
    public Label calendarPriorityLabel;
    public JFXComboBox calendarFrequencyPicker;
    public Label calendarFrequencyLabel;
    public LocalDateTimeTextField calendarStartDatePicker;
    public Label calendarEndDateLabel;
    public LocalDateTimeTextField calendarEndDatePicker;
    public JFXButton calendarCreateEventButton;
    public JFXButton calendarClearEventButton;
    public JFXListView calendarUpcomingEventsListView;
    public Label calendarUpcomingEventsLabel;
    public SplitPane calendarLeftSplitPane;
    public AnchorPane calendarDatePickerPane;
    public AnchorPane calendarSchedulePane;
    public AnchorPane calendarRightPane;
    public JFXTabPane tabPane;
    public Tab calendarTab;
    public Tab groupsTab;
    public SplitPane calendarRootSplitPane;
    public AnchorPane calendarLeftPane;
    private IApplicationService applicationService;
    private IEventsService eventsService;
    private IGroupsService groupsService;
    private ResourceBundle resourceBundle;
    private URL location;

    public MainController() {
    }

    @Autowired
    public MainController(IEventsService eventsService, IGroupsService groupsService, IApplicationService applicationService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.applicationService = applicationService;
    }
//    ------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.location = location;

        calendarFillComboBoxes();
        calendarCleanEventForm(null);
    }
//    ------------------------------------------------------------------------------------------------------------------

    @FXML
    private void calendarCreateNewEvent(ActionEvent actionEvent) {
        applicationService.calendarCreateNewEvent(
                getCalendarEventTitlePicker(),
                getCalendarStartDatePicker(),
                getCalendarEndDatePicker(),
                getCalendarNoticePeriodPicker(),
                getCalendarFrequencyPicker(),
                getCalendarPriorityPicker(),
                actionEvent
        );
    }

    @FXML
    private void calendarCleanEventForm(ActionEvent actionEvent) {
        applicationService.calendarCleanEventForm(
                getCalendarEventTitlePicker(),
                getCalendarStartDatePicker(),
                getCalendarEndDatePicker(),
                getCalendarNoticePeriodPicker(),
                getCalendarFrequencyPicker(),
                getCalendarPriorityPicker(),
                actionEvent);
    }

    private void calendarFillComboBoxes() {
        applicationService.calendarFillComboBoxes(
                getCalendarNoticePeriodPicker(),
                getCalendarFrequencyPicker(),
                getCalendarPriorityPicker()
        );
    }


//    ------------------------------------------------------------------------------------------------------------------

    public Label getCalendarStartDateLabel() {
        return calendarStartDateLabel;
    }

    public void setCalendarStartDateLabel(Label calendarStartDateLabel) {
        this.calendarStartDateLabel = calendarStartDateLabel;
    }

    public JFXTextField getCalendarEventTitlePicker() {
        return calendarEventTitlePicker;
    }

    public void setCalendarEventTitlePicker(JFXTextField calendarEventTitlePicker) {
        this.calendarEventTitlePicker = calendarEventTitlePicker;
    }

    public JFXComboBox getCalendarNoticePeriodPicker() {
        return calendarNoticePeriodPicker;
    }

    public void setCalendarNoticePeriodPicker(JFXComboBox calendarNoticePeriodPicker) {
        this.calendarNoticePeriodPicker = calendarNoticePeriodPicker;
    }

    public Label getCalendarNoticePeriodLabel() {
        return calendarNoticePeriodLabel;
    }

    public void setCalendarNoticePeriodLabel(Label calendarNoticePeriodLabel) {
        this.calendarNoticePeriodLabel = calendarNoticePeriodLabel;
    }

    public JFXComboBox getCalendarPriorityPicker() {
        return calendarPriorityPicker;
    }

    public void setCalendarPriorityPicker(JFXComboBox calendarPriorityPicker) {
        this.calendarPriorityPicker = calendarPriorityPicker;
    }

    public Label getCalendarPriorityLabel() {
        return calendarPriorityLabel;
    }

    public void setCalendarPriorityLabel(Label calendarPriorityLabel) {
        this.calendarPriorityLabel = calendarPriorityLabel;
    }

    public JFXComboBox getCalendarFrequencyPicker() {
        return calendarFrequencyPicker;
    }

    public void setCalendarFrequencyPicker(JFXComboBox calendarFrequencyPicker) {
        this.calendarFrequencyPicker = calendarFrequencyPicker;
    }

    public Label getCalendarFrequencyLabel() {
        return calendarFrequencyLabel;
    }

    public void setCalendarFrequencyLabel(Label calendarFrequencyLabel) {
        this.calendarFrequencyLabel = calendarFrequencyLabel;
    }

    public LocalDateTimeTextField getCalendarStartDatePicker() {
        return calendarStartDatePicker;
    }

    public void setCalendarStartDatePicker(LocalDateTimeTextField calendarStartDatePicker) {
        this.calendarStartDatePicker = calendarStartDatePicker;
    }

    public Label getCalendarEndDateLabel() {
        return calendarEndDateLabel;
    }

    public void setCalendarEndDateLabel(Label calendarEndDateLabel) {
        this.calendarEndDateLabel = calendarEndDateLabel;
    }

    public LocalDateTimeTextField getCalendarEndDatePicker() {
        return calendarEndDatePicker;
    }

    public void setCalendarEndDatePicker(LocalDateTimeTextField calendarEndDatePicker) {
        this.calendarEndDatePicker = calendarEndDatePicker;
    }

    public JFXButton getCalendarCreateEventButton() {
        return calendarCreateEventButton;
    }

    public void setCalendarCreateEventButton(JFXButton calendarCreateEventButton) {
        this.calendarCreateEventButton = calendarCreateEventButton;
    }

    public JFXButton getCalendarClearEventButton() {
        return calendarClearEventButton;
    }

    public void setCalendarClearEventButton(JFXButton calendarClearEventButton) {
        this.calendarClearEventButton = calendarClearEventButton;
    }

    public JFXListView getCalendarUpcomingEventsListView() {
        return calendarUpcomingEventsListView;
    }

    public void setCalendarUpcomingEventsListView(JFXListView calendarUpcomingEventsListView) {
        this.calendarUpcomingEventsListView = calendarUpcomingEventsListView;
    }

    public Label getCalendarUpcomingEventsLabel() {
        return calendarUpcomingEventsLabel;
    }

    public void setCalendarUpcomingEventsLabel(Label calendarUpcomingEventsLabel) {
        this.calendarUpcomingEventsLabel = calendarUpcomingEventsLabel;
    }

    public SplitPane getCalendarLeftSplitPane() {
        return calendarLeftSplitPane;
    }

    public void setCalendarLeftSplitPane(SplitPane calendarLeftSplitPane) {
        this.calendarLeftSplitPane = calendarLeftSplitPane;
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
}
