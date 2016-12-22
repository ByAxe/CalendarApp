/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
public class ApplicationController implements Initializable {

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
    public JFXListView<Label> calendarUpcomingEventsListView;
    public Label calendarUpcomingEventsLabel;
    public SplitPane calendarLeftSplitPane;
    public AnchorPane calendarDatePickerPane;
    public AnchorPane calendarSchedulePane;
    public AnchorPane calendarRightPane;
    public JFXTabPane tabPane;
    public Tab calendarTab;
    public Tab scheduleTab;
    public SplitPane calendarRootSplitPane;
    public AnchorPane calendarLeftPane;
    public Tab allocationTab;
    public JFXButton calendarUpdateUpcomingEventsListButton;
    public MenuItem exitMenuItem;
    public MenuItem preferencesMenuItem;
    public MenuItem helpMenuItem;
    public MenuItem aboutMenuItem;
    private IApplicationService applicationService;
    private IEventsService eventsService;
    private IGroupsService groupsService;
    private ResourceBundle resourceBundle;
    private URL location;

    public ApplicationController() {
    }

    @Autowired
    public ApplicationController(IEventsService eventsService, IGroupsService groupsService, IApplicationService applicationService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.applicationService = applicationService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.location = location;

        calendarFillComboBoxes();
        calendarCleanEventForm(null);

        calendarFillUpcomingEventsList();

//        calendarAddContextMenuToUpcomingEventsList();
    }

    /**
     * Обработка нажатия кнопки нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @FXML
    private void calendarCreateNewEvent(ActionEvent actionEvent) {
        applicationService.calendarCreateNewEvent(
                calendarEventTitlePicker,
                calendarStartDatePicker,
                calendarEndDatePicker,
                calendarNoticePeriodPicker,
                calendarFrequencyPicker,
                calendarPriorityPicker,
                actionEvent
        );

        calendarHandleUpdateUpcomingEventsList();
    }

    /**
     * Обработка нажатия кнопки очистки формы для создания нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @FXML
    private void calendarCleanEventForm(ActionEvent actionEvent) {
        applicationService.calendarCleanEventForm(
                calendarEventTitlePicker,
                calendarStartDatePicker,
                calendarEndDatePicker,
                calendarNoticePeriodPicker,
                calendarFrequencyPicker,
                calendarPriorityPicker,
                actionEvent);

        calendarHandleUpdateUpcomingEventsList();
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

/*    @FXML
    private void handlePreferencesClick(ActionEvent actionEvent) {
        applicationService.showPreferences();
    }*/

    @FXML
    private void handleHelp(ActionEvent actionEvent) {
        applicationService.showHelp();
    }


    @FXML
    private void handleAbout(ActionEvent actionEvent) {
        applicationService.showAbout();
    }

    /**
     * Заполняет комбобоксы календаря перечислениями
     */
    private void calendarFillComboBoxes() {
        applicationService.calendarFillComboBoxes(
                calendarNoticePeriodPicker,
                calendarFrequencyPicker,
                calendarPriorityPicker
        );
    }

    /**
     *
     */
    private void calendarAddContextMenuToUpcomingEventsList() {
        applicationService.calendarAddContextMenuToUpcomingEventsList(calendarUpcomingEventsListView);
    }

    /**
     * Заполнения списка предстоящих событий в календаре
     * Отсортирован по началу события (ASC) (сверху - ближайшие)
     */
    private void calendarFillUpcomingEventsList() {
        applicationService.calendarFillUpcomingEventsList(calendarUpcomingEventsListView);
    }


    /**
     *
     */
    public void calendarHandleUpdateUpcomingEventsList() {
        applicationService.calendarFillUpcomingEventsList(calendarUpcomingEventsListView);
    }
}
