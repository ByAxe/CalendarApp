package service;

import controller.MainController;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;
import javafx.event.ActionEvent;
import jfxtras.scene.control.LocalDateTimeTextField;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Носитель основной логики приложения
 * <p>
 * Created by byaxe on 08.12.16.
 */
public class ApplicationServiceImpl implements IApplicationService {

    private final MainController controller;

    private final IEventsService eventsService;

    private final IGroupsService groupsService;

    public ApplicationServiceImpl(MainController controller, IEventsService eventsService, IGroupsService groupsService) {
        this.controller = controller;
        this.eventsService = eventsService;
        this.groupsService = groupsService;
    }

    /**
     * Метод вызывающийся при старте приложения, в одноименном методе Контроллера
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        calendarFillComboBoxes();
        calendarAddListenersToNewEventFields();
    }

    /**
     * Обработка нажатия кнопки нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @Override
    public void calendarCreateNewEvent(ActionEvent actionEvent) {

    }

    /**
     * Обработка нажатия кнопки очистки формы для создания нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @Override
    public void calendarCleanEventForm(ActionEvent actionEvent) {
        controller.getCalendarStartDatePicker().setLocalDateTime(null);
        controller.getCalendarEndDatePicker().setLocalDateTime(null);
        controller.getCalendarEventTitlePicker().setText("");
        controller.getCalendarNoticePeriodPicker().setValue(null);
        controller.getCalendarFrequencyPicker().setValue(null);
        controller.getCalendarPriorityPicker().setValue(null);
    }


    /**
     * Заполняет комбобоксы календаря перечислениями
     */
    private void calendarFillComboBoxes() {
        controller.getCalendarNoticePeriodPicker()
                .getItems()
                .addAll(Stream.of(NoticePeriod.values()).map(NoticePeriod::getName).collect(toList()));

        controller.getCalendarFrequencyPicker()
                .getItems()
                .addAll(Stream.of(Frequency.values()).map(Frequency::getName).collect(toList()));

        controller.getCalendarPriorityPicker()
                .getItems()
                .addAll(Stream.of(Priority.values()).map(Priority::getName).collect(toList()));
    }

    private void calendarAddListenersToNewEventFields() {

        final LocalDateTimeTextField calendarStartDatePicker = controller.getCalendarStartDatePicker();

        calendarStartDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && calendarStartDatePicker.getLocalDateTime() != null) {
                System.out.println("Hello!");
            }
        });


    }

}
