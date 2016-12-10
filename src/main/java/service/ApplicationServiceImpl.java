package service;

import controller.MainController;
import core.commons.Result;
import core.dto.EventsDTOImpl;
import core.dto.api.IEventsDTO;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;
import core.validators.CalendarValidator;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Stream;

import static core.enums.ResultEnum.SUCCESS;
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
        calendarCleanEventForm(null);
    }

    /**
     * Обработка нажатия кнопки нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @Override
    public void calendarCreateNewEvent(ActionEvent actionEvent) {
        final IEventsDTO event = new EventsDTOImpl();

        event.setUuid(UUID.randomUUID());
        event.setDtUpdate(new Date());

        event.setTitle(controller.getCalendarEventTitlePicker().getText());
        event.setStarts(controller.getCalendarStartDatePicker().getLocalDateTime());
        event.setEnds(controller.getCalendarEndDatePicker().getLocalDateTime());

        final String noticePeriodName = String.valueOf(controller.getCalendarNoticePeriodPicker().getValue());
        Stream.of(NoticePeriod.values()).forEach(np -> {
            if (np.getName().equals(noticePeriodName)) event.setNoticePeriod(np);
        });

        final String frequencyName = String.valueOf(controller.getCalendarFrequencyPicker().getValue());
        Stream.of(Frequency.values()).forEach(f -> {
            if (f.getName().equals(frequencyName)) event.setFrequency(f);
        });

        final String priorityName = String.valueOf(controller.getCalendarPriorityPicker().getValue());
        Stream.of(Priority.values()).forEach(p -> {
            if (p.getName().equals(priorityName)) event.setPriority(p);
        });

        final Alert.AlertType alertType;
        final String alertTitle;
        final String alertHeader;
        final StringBuilder alertBody = new StringBuilder();

        final Result result = new CalendarValidator().validateNewEvent(event);
        if (Objects.equals(result.getResult(), SUCCESS)) {
            eventsService.save(event);

            alertType = Alert.AlertType.INFORMATION;
            alertTitle = "Информация";
            alertHeader = "Создано событие";
            alertBody.append("Было создано новое событие");
        } else {
            alertType = Alert.AlertType.ERROR;

            alertTitle = "Ошибка";
            alertHeader = "При заполнении данных вы допустили следущие ошибки";
            result.getPayload().forEach(alertBody::append);
        }

        raiseMessageBox(alertType, alertTitle, alertHeader, alertBody.toString());
    }

    /**
     * Обработка нажатия кнопки очистки формы для создания нового события, на вкладке Календарь
     *
     * @param actionEvent
     */
    @Override
    public void calendarCleanEventForm(ActionEvent actionEvent) {
        controller.getCalendarStartDatePicker().setLocalDateTime(LocalDateTime.now());
        controller.getCalendarEndDatePicker().setLocalDateTime(LocalDateTime.now());
        controller.getCalendarEventTitlePicker().setText("");
        controller.getCalendarNoticePeriodPicker().setValue(NoticePeriod.getDefault().getName());
        controller.getCalendarFrequencyPicker().setValue(Frequency.getDefault().getName());
        controller.getCalendarPriorityPicker().setValue(Priority.getDefault().getName());
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

    /**
     * Создает сообщение
     *
     * @param type   Тип сообщения
     * @param title  Надпись сверху
     * @param header Заголовок
     * @param body   Основной текст сообщения
     */
    private void raiseMessageBox(Alert.AlertType type, String title, String header, String body) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }
}
