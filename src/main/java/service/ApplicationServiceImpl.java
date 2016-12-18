package service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.commons.Result;
import core.dto.EventsDTOImpl;
import core.dto.api.IEventsDTO;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;
import core.validators.CalendarValidator;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.IApplicationService;
import service.api.IEventsService;
import service.api.IGroupsService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;

/**
 * Носитель основной логики приложения
 * <p>
 * Created by byaxe on 08.12.16.
 */
@Service("applicationService")
public class ApplicationServiceImpl implements IApplicationService {

    private final IEventsService eventsService;

    private final IGroupsService groupsService;

    private final CalendarValidator calendarValidator;

    @Autowired
    public ApplicationServiceImpl(IEventsService eventsService, IGroupsService groupsService,
                                  CalendarValidator calendarValidator) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.calendarValidator = calendarValidator;
    }

    /**
     * Обработка нажатия кнопки нового события, на вкладке Календарь
     *
     * @param title        Суть события
     * @param starts       Дата начала события
     * @param ends         Дата окончания события
     * @param noticePeriod Период напоминания о событии
     * @param frequency    Частота происхождения события
     * @param priority     Приорит события
     * @param actionEvent  Тип события (JavaFx событие)
     */
    @Override
    public void calendarCreateNewEvent(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                                       JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                                       ActionEvent actionEvent) {
        final IEventsDTO event = new EventsDTOImpl();

        event.setUuid(UUID.randomUUID());
        event.setDtUpdate(new Date());

        event.setTitle(title.getText());
        event.setStarts(starts.getLocalDateTime());
        event.setEnds(ends.getLocalDateTime());

        Stream.of(NoticePeriod.values()).forEach(np -> {
            if (np.getName().equals(String.valueOf(noticePeriod.getValue()))) event.setNoticePeriod(np);
        });

        Stream.of(Frequency.values()).forEach(f -> {
            if (f.getName().equals(String.valueOf(frequency.getValue()))) event.setFrequency(f);
        });

        Stream.of(Priority.values()).forEach(p -> {
            if (p.getName().equals(String.valueOf(priority.getValue()))) event.setPriority(p);
        });

        final Alert.AlertType alertType;
        final String alertTitle;
        final String alertHeader;
        final StringBuilder alertBody = new StringBuilder();

        final Result result = calendarValidator.validateNewEvent(event);

        if (Objects.equals(result.getResult(), SUCCESS)) {
            eventsService.save(event);

            alertType = Alert.AlertType.INFORMATION;
            alertTitle = "Информация";
            alertHeader = "Создано событие";
            alertBody.append("Было создано новое событие");

            calendarCleanEventForm(title, starts, ends, noticePeriod, frequency, priority, null);
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
     * @param title        Суть события
     * @param starts       Дата начала события
     * @param ends         Дата окончания события
     * @param noticePeriod Период напоминания о событии
     * @param frequency    Частота происхождения события
     * @param priority     Приорит события
     * @param actionEvent  Тип события (JavaFx событие)
     */
    @Override
    public void calendarCleanEventForm(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                                       JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                                       ActionEvent actionEvent) {
        starts.setLocalDateTime(LocalDateTime.now());
        ends.setLocalDateTime(LocalDateTime.now());
        title.setText("");
        noticePeriod.setValue(NoticePeriod.getDefault().getName());
        frequency.setValue(Frequency.getDefault().getName());
        priority.setValue(Priority.getDefault().getName());
    }


    /**
     * Заполняет комбобоксы перечислениями
     *
     * @param noticePeriodPicker Комбобокс выбора периода напоминания
     * @param frequencyPicker    Комбобокс выбора частотности
     * @param priorityPicker     Комбобокс выбора приоритета
     */
    @Override
    public void calendarFillComboBoxes(JFXComboBox noticePeriodPicker, JFXComboBox frequencyPicker,
                                       JFXComboBox priorityPicker) {
        noticePeriodPicker.getItems()
                .addAll(Stream.of(NoticePeriod.values()).map(NoticePeriod::getName).collect(toList()));

        frequencyPicker.getItems()
                .addAll(Stream.of(Frequency.values()).map(Frequency::getName).collect(toList()));

        priorityPicker.getItems()
                .addAll(Stream.of(Priority.values()).map(Priority::getName).collect(toList()));
    }

    /**
     * Заполнения списка предстоящих событий
     * Отсортирован по началу события (ASC) (сверху - ближайшие)
     *
     * @param calendarUpcomingEventsListView Список который необходимо заполнить
     */
    @Override
    public void calendarFillUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView) {
        calendarUpcomingEventsListView.getItems().clear();

        calendarUpcomingEventsListView.getItems()
                .addAll(eventsService.findUpcomingEvents()
                        .stream()
                        .map(event -> new Label(event.toPrettyString()))
                        .collect(toList()));
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
