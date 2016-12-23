/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.api.*;
import view.controlls.DateChooser;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static core.commons.Utils.*;
import static core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Носитель основной логики приложения
 * <p>
 * Created by byaxe on 08.12.16.
 */
@Service("applicationService")
public class ApplicationServiceImpl implements IApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final IEventsService eventsService;
    private final IGroupsService groupsService;
    private final IRulersService rulersService;
    private final INotificationsLogService notificationsLogService;
    private final IOrdersService ordersService;
    private final IOrganisationsService organisationsService;
    private final IPreferencesService preferencesService;
    private final IStudentsService studentsService;
    private final IAllocationService allocationService;
    private final CalendarValidator calendarValidator;

    @Value("${help.body}")
    private String HELP_BODY;

    @Value("${about.body}")
    private String ABOUT_BODY;

    @Autowired
    public ApplicationServiceImpl(IEventsService eventsService, IGroupsService groupsService,
                                  IRulersService rulersService, INotificationsLogService notificationsLogService,
                                  IOrdersService ordersService, IOrganisationsService organisationsService,
                                  IPreferencesService preferencesService, IStudentsService studentsService,
                                  IAllocationService allocationService, CalendarValidator calendarValidator) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.rulersService = rulersService;
        this.notificationsLogService = notificationsLogService;
        this.ordersService = ordersService;
        this.organisationsService = organisationsService;
        this.preferencesService = preferencesService;
        this.studentsService = studentsService;
        this.allocationService = allocationService;
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
        final String alertBody;

        final Result result = calendarValidator.validateNewEvent(event);

        if (Objects.equals(result.getResult(), SUCCESS)) {
            eventsService.save(event);

            alertType = INFORMATION;
            alertTitle = "Информация";
            alertHeader = "Создано событие";
            alertBody = "Было создано новое событие";

            calendarCleanEventForm(title, starts, ends, noticePeriod, frequency, priority, null);
        } else {
            alertType = ERROR;

            alertTitle = "Ошибка";
            alertHeader = "При заполнении данных вы допустили следущие ошибки";
            alertBody = result.errorsToString();
        }

        raiseMessageBox(alertType, alertTitle, alertHeader, alertBody);
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
                        .map(event -> {
                            Label label = new Label(event.toPrettyString());
                            label.setId(String.valueOf(event.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    /**
     * Добавляем контекстное меню для взаимодействия с отдельными событиями, после их создания
     *
     * @param calendarUpcomingEventsListView список с предстоящими событиями
     */
    @Override
    public void calendarAddContextMenuToUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView) {
        final ContextMenu calendarUpcomingEventsContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Удалить");
        MenuItem change = new MenuItem("Редактировать");

        calendarUpcomingEventsContextMenu.getItems().addAll(delete/*, change*/);

        delete.setOnAction(event -> {
            Label item = calendarUpcomingEventsListView.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, "Внимание", "Удалить элемент?", "Вы действительно хотите удалить данное событие: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            // Если он все же подтвердил удаление - удаляем элемент
            Optional.ofNullable(eventsService.findUpcomingEvents())
                    .ifPresent(l -> l.stream()
                            .filter(e -> Objects.equals(String.valueOf(e.getUuid()), item.getId()))
                            .forEach(eventsService::delete));

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            calendarFillUpcomingEventsList(calendarUpcomingEventsListView);
        });
        change.setOnAction(event -> {
            System.out.println("change...");
        });

        calendarUpcomingEventsListView.setContextMenu(calendarUpcomingEventsContextMenu);
    }

    @Override
    public void showHelp() {
        raiseMessageBoxWithCustomSize(INFORMATION, "Помощь", null, HELP_BODY, 400, 600);
    }

    @Override
    public void showAbout() {
        raiseMessageBoxWithCustomSize(INFORMATION, "О программе", null, ABOUT_BODY, 400, 600);
    }

    /**
     * Создаем большой календарь и вешаем обработчик по нажатию на определенные даты
     *
     * @param calendarRightPaneBottomBlock   Вся панель в которую будет всунут календарь
     * @param calendarUpcomingEventsLabel    Заголовок списка предстоящих событий
     * @param calendarUpcomingEventsListView Список предстоящих событий
     */
    @Override
    public void calendarCreateBigCalendar(Pane calendarRightPaneBottomBlock, Label calendarUpcomingEventsLabel, JFXListView<Label> calendarUpcomingEventsListView) {
        final DateChooser dateChooser = new DateChooser();

        calendarRightPaneBottomBlock.getChildren().add(dateChooser);

        dateChooser.setOnMouseClicked(e -> calendarHandleClickOnDay(dateChooser, calendarUpcomingEventsLabel, calendarUpcomingEventsListView));
    }

    /**
     * По нажатию на календарь, берем выбранную дату {@link Date} и вписываем в список предстоящих событий те,
     * которые будут ТОЛЬКО в этот выбранный день
     *
     * @param dateChooser                    Календарь
     * @param calendarUpcomingEventsLabel    Заголовок списка предстоящих событий
     * @param calendarUpcomingEventsListView Список предстоящих событий
     */
    private void calendarHandleClickOnDay(DateChooser dateChooser, Label calendarUpcomingEventsLabel, JFXListView<Label> calendarUpcomingEventsListView) {
        // Получаем нажатую дату
        LocalDateTime dateTime = convertDateToLocalDateTime(dateChooser.getDate());

        // Вписываем в заголовок таблицы предстоящих событий, что показывает события за определенный день
        calendarUpcomingEventsLabel.setText("Список событий на " + dateTime.format(CALENDAR_ON_DATE_FORMATTER));

        // Получаем события ТОЛЬКО за определенный период
        List<IEventsDTO> upcomingEventsForPeriod = eventsService.findUpcomingEventsForPeriod(getStartOfDay(dateTime), getEndOfDay(dateTime));

        // Чистим список
        calendarUpcomingEventsListView.getItems().clear();

        // Заполняем список событиями ТОЛЬКО за определенный период
        calendarUpcomingEventsListView.getItems()
                .addAll(upcomingEventsForPeriod.stream()
                        .map(e -> {
                            Label label = new Label(e.toPrettyString());
                            label.setId(String.valueOf(e.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

}
