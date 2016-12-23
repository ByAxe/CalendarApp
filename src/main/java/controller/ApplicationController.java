/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.net.URL;
import java.util.ResourceBundle;

import static core.commons.Utils.CALENDAR_DATE_PICKER_FORMATTER;

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
    public Pane calendarRightPaneTopBlock;
    public Label calendarMonthTitle;
    public ButtonBar calendarLeftButtonBar;
    public JFXButton calendarLeftMonthButton;
    public ButtonBar calendarRightButtonBar;
    public JFXButton calendarRightMonthButton;
    public Pane calendarRightPaneBottomBlock;
    public JFXButton calendarLeftYearButton;
    public JFXButton calendarRightYearButton;
    private IApplicationService applicationService;
    private IEventsService eventsService;
    private IGroupsService groupsService;
    private ResourceBundle resourceBundle;
    private URL location;
    @Value("${calendar.upcoming.events.label.text}")
    private String upcomingEventsLabelText;

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

        calendarSetFormatForDatePickers();

        calendarCreateBigCalendar();

        calendarAddContextMenuToUpcomingEventsList();
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
     * Выводит в список все планируемые события
     */
    @FXML
    private void calendarHandleUpdateUpcomingEventsList() {
        calendarUpcomingEventsLabel.setText(upcomingEventsLabelText);
        applicationService.calendarFillUpcomingEventsList(calendarUpcomingEventsListView);
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
        calendarUpcomingEventsLabel.setText(upcomingEventsLabelText);
        applicationService.calendarFillUpcomingEventsList(calendarUpcomingEventsListView);
    }

    /**
     * Создаем большой календарь для вкладки "Ежедневник"
     */
    private void calendarCreateBigCalendar() {
        applicationService.calendarCreateBigCalendar(calendarRightPaneBottomBlock, calendarUpcomingEventsLabel, calendarUpcomingEventsListView);
    }

    /**
     * Устанавливаем спец формат для дата в полях выбора начала и конца нового события
     */
    private void calendarSetFormatForDatePickers() {
        calendarStartDatePicker.setDateTimeFormatter(CALENDAR_DATE_PICKER_FORMATTER);
        calendarEndDatePicker.setDateTimeFormatter(CALENDAR_DATE_PICKER_FORMATTER);
    }
}
