/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import controlls.DateChooser;
import core.commons.Result;
import core.dto.EventsDTOImpl;
import core.dto.api.IEventsDTO;
import core.enums.EventType;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;
import core.validators.api.IValidator;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalDateTimeTextField;
import model.entity.EventsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.EventsRepository;
import service.api.IEventsService;
import service.quartz.JobInitializer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static core.commons.Utils.*;
import static core.enums.ResultEnum.SUCCESS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 26.11.16.
 * <p>
 * Бизнес-логика событий
 */
@Service("eventsService")
@Transactional(readOnly = true, propagation = Propagation.NESTED, rollbackFor = {Exception.class})
public class EventsServiceImpl implements IEventsService {

    private final EventsRepository eventsRepository;
    private final ConversionService conversionService;
    private final IValidator<Result, IEventsDTO> calendarValidator;

    @Value("${validation.error.title}")
    private String VALIDATION_ERROR_TITLE;

    @Value("${validation.error.header}")
    private String VALIDATION_ERROR_HEADER;

    @Value("${validation.success.title}")
    private String VALIDATION_SUCCESS_TITLE;

    @Value("${validation.success.header}")
    private String VALIDATION_SUCCESS_HEADER;

    @Value("${validation.success.body}")
    private String VALIDATION_SUCCESS_BODY;

    @Value("${change.element.title}")
    private String CHANGE_ELEMENT_TITLE;

    @Value("${change.element.body}")
    private String CHANGE_ELEMENT_BODY;

    @Value("${change.element.header}")
    private String CHANGE_ELEMENT_HEADER;

    @Value("${delete.element.title}")
    private String DELETE_ELEMENT_TITLE;

    @Value("${delete.element.header}")
    private String DELETE_ELEMENT_HEADER;

    @Value("${delete.context.menu.item}")
    private String DELETE_CONTEXT_MENU_ITEM;

    @Value("${change.context.menu.item}")
    private String CHANGE_CONTEXT_MENU_ITEM;

    @Value("${delete.constraint.violation.title}")
    private String DELETE_CONSTRAINT_VIOLATION_TITLE;

    @Value("${delete.constraint.violation.header}")
    private String DELETE_CONSTRAINT_VIOLATION_HEADER;

    @Value("${delete.constraint.violation.body}")
    private String DELETE_CONSTRAINT_VIOLATION_BODY;

    @Autowired
    private JobInitializer jobInitializer;

    @Autowired
    public EventsServiceImpl(EventsRepository eventsRepository, ConversionService conversionService, IValidator<Result, IEventsDTO> calendarValidator) {
        this.eventsRepository = eventsRepository;
        this.conversionService = conversionService;
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
    @Transactional
    public void createNewEvent(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
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

        final Result result = calendarValidator.validate(event);

        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(event);

            alertType = INFORMATION;
            alertTitle = VALIDATION_SUCCESS_TITLE;
            alertHeader = VALIDATION_SUCCESS_HEADER;
            alertBody = VALIDATION_SUCCESS_BODY;

            cleanEventForm(title, starts, ends, noticePeriod, frequency, priority, null);
        } else {
            alertType = ERROR;

            alertTitle = VALIDATION_ERROR_TITLE;
            alertHeader = VALIDATION_ERROR_HEADER;
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
    public void cleanEventForm(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                               JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                               ActionEvent actionEvent) {
        starts.setLocalDateTime(LocalDateTime.now());
        ends.setLocalDateTime(LocalDateTime.now().plus(1, HOURS));
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
    public void fillComboBoxes(JFXComboBox noticePeriodPicker, JFXComboBox frequencyPicker,
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
    public void fillUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView) {
        calendarUpcomingEventsListView.getItems()
                .setAll(findUpcomingEvents()
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
    @Transactional
    public void addContextMenuToUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView) {
        final ContextMenu calendarUpcomingEventsContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem(DELETE_CONTEXT_MENU_ITEM);
        MenuItem change = new MenuItem(CHANGE_CONTEXT_MENU_ITEM);

        calendarUpcomingEventsContextMenu.getItems().addAll(delete/*, change*/);

        delete.setOnAction(event -> {
            Label item = calendarUpcomingEventsListView.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данное событие: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            IEventsDTO eventDto = findByUuid(UUID.fromString(item.getId()));
            delete(eventDto);

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillUpcomingEventsList(calendarUpcomingEventsListView);
        });
        change.setOnAction(event -> {
            Label item = calendarUpcomingEventsListView.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;


            System.out.println("change...");
        });

        calendarUpcomingEventsListView.setContextMenu(calendarUpcomingEventsContextMenu);
    }

    /**
     * Создаем большой календарь и вешаем обработчик по нажатию на определенные даты
     *
     * @param calendarRightPaneBottomBlock   Вся панель в которую будет всунут календарь
     * @param calendarUpcomingEventsLabel    Заголовок списка предстоящих событий
     * @param calendarUpcomingEventsListView Список предстоящих событий
     */
    @Override
    public void createBigCalendar(Pane calendarRightPaneBottomBlock, Label calendarUpcomingEventsLabel, JFXListView<Label> calendarUpcomingEventsListView) {
        final DateChooser dateChooser = new DateChooser();

        calendarRightPaneBottomBlock.getChildren().add(dateChooser);

        dateChooser.setOnMouseClicked(e -> handleClickOnDay(dateChooser, calendarUpcomingEventsLabel, calendarUpcomingEventsListView));
    }

    /**
     * По нажатию на календарь, берем выбранную дату {@link Date} и вписываем в список предстоящих событий те,
     * которые будут ТОЛЬКО в этот выбранный день
     *
     * @param dateChooser            Календарь
     * @param upcomingEventsLabel    Заголовок списка предстоящих событий
     * @param upcomingEventsListView Список предстоящих событий
     */
    private void handleClickOnDay(DateChooser dateChooser, Label upcomingEventsLabel, JFXListView<Label> upcomingEventsListView) {
        // Получаем нажатую дату
        LocalDateTime dateTime = convertDateToLocalDateTime(dateChooser.getDate());

        // Вписываем в заголовок таблицы предстоящих событий, что показывает события за определенный день
        upcomingEventsLabel.setText("Список событий на " + dateTime.format(CALENDAR_ON_DATE_FORMATTER));

        // Получаем события ТОЛЬКО за определенный период
        List<IEventsDTO> upcomingEventsForPeriod = findUpcomingEventsForPeriod(getStartOfDay(dateTime), getEndOfDay(dateTime));

        // Чистим список
        upcomingEventsListView.getItems().clear();

        // Заполняем список событиями ТОЛЬКО за определенный период
        upcomingEventsListView.getItems()
                .addAll(upcomingEventsForPeriod.stream()
                        .map(e -> {
                            Label label = new Label(e.toPrettyString());
                            label.setId(String.valueOf(e.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    /**
     * Сохранение единичной сущности
     *
     * @param sourceDto сохраняемая сущность в виде DTO
     * @return сохраненная сущность в виде DTO
     */
    @Override
    @Transactional
    public IEventsDTO save(IEventsDTO sourceDto) {
        EventsEntity sourceEntity = convertDtoToEntity(sourceDto);

        EventsEntity savedEntity = eventsRepository.save(sourceEntity);

        IEventsDTO newEvent = convertEntityToDto(savedEntity);

        jobInitializer.synchronizeJobWithCalendarEvents(newEvent, EventType.SAVE);

        return newEvent;
    }

    /**
     * Сохранение списка сущностей
     *
     * @param sourceDtoList сохраняемый список сущностей в виде DTO
     * @return сохраненный список сущностей в виде DTO
     */
    @Override
    @Transactional
    public Iterable<IEventsDTO> save(Iterable<IEventsDTO> sourceDtoList) {
        List<EventsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        Iterable<EventsEntity> savedEntities = eventsRepository.save(sourceEntities);

        List<IEventsDTO> newEvents = convertListEntityToDto(savedEntities);

        newEvents.forEach(e -> jobInitializer.synchronizeJobWithCalendarEvents(e, EventType.SAVE));

        return newEvents;
    }

    @Override
    public IEventsDTO findByUuid(UUID uuid) {
        EventsEntity entity = eventsRepository.findByUuid(uuid);
        return convertEntityToDto(entity);
    }

    /**
     * Возвращает сортированный список событий
     *
     * @param sort схема сортировки
     * @return сортированный список
     */
    @Override
    public Iterable<IEventsDTO> findAll(Sort sort) {
        Iterable<EventsEntity> entities = eventsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    /**
     * Возвращает страницу событий
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<IEventsDTO> findAll(Pageable pageable) {
        Page<EventsEntity> entities = eventsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    /**
     * Находим все события
     *
     * @return Список событий как DTO
     */
    @Override
    public Iterable<IEventsDTO> findAll() {
        Iterable<EventsEntity> eventsEntities = eventsRepository.findAll();

        return convertListEntityToDto(eventsEntities);
    }

    /**
     * Получение списка событий с определенными идентификаторами
     *
     * @param idList Список индефикаторов записей, которые нужно выбрать
     * @return Список DTO с указанными идентификаторами
     */
    @Override
    public Iterable<IEventsDTO> findAll(Iterable<Long> idList) {
        Iterable<EventsEntity> eventsEntities = eventsRepository.findAll(idList);

        return convertListEntityToDto(eventsEntities);
    }

    /**
     * Получение события по идентификатору
     *
     * @param id Идентификатор
     * @return Событие как DTO
     */
    @Override
    public IEventsDTO findOne(Long id) {
        EventsEntity entity = eventsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    /**
     * Получение количества записей
     *
     * @return Количество записей
     */
    @Override
    public long count() {
        return eventsRepository.count();
    }

    /**
     * Проверка на существование сущности с данным идентификатором
     *
     * @param id Идентификатор
     * @return true/false
     */
    @Override
    public boolean exists(Long id) {
        return eventsRepository.exists(id);
    }

    /**
     * Удаление события по идентификатору
     *
     * @param id Идентификатор
     */
    @Override
    @Transactional
    public void delete(Long id) {
        jobInitializer.synchronizeJobWithCalendarEvents(findOne(id), EventType.DELETE);

        eventsRepository.delete(id);
    }

    /**
     * Удаление события по сущности
     *
     * @param dto Сущность
     */
    @Override
    @Transactional
    public void delete(IEventsDTO dto) {
        jobInitializer.synchronizeJobWithCalendarEvents(dto, EventType.DELETE);

        EventsEntity entity = convertDtoToEntity(dto);

        eventsRepository.delete(entity);
    }

    @Override
    @Transactional
    public Long deleteByUuid(UUID uuid) {
        return eventsRepository.deleteByUuid(uuid);
    }


    /**
     * Удаление списка событий
     *
     * @param dtoList список сущностей
     */
    @Override
    @Transactional
    public void delete(Iterable<? extends IEventsDTO> dtoList) {
        dtoList.forEach(e -> jobInitializer.synchronizeJobWithCalendarEvents(e, EventType.DELETE));

        List<EventsEntity> entities = convertListDtoToEntity((Iterable<IEventsDTO>) dtoList);

        eventsRepository.delete(entities);
    }

    /**
     * Удаление всех событий
     */
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Найти все предстоящие события
     *
     * @return список предстоящих событий
     */
    @Override
    public List<IEventsDTO> findUpcomingEvents() {
        List<EventsEntity> entities = eventsRepository.findUpcomingEvents();

        return convertListEntityToDto(entities);
    }


    @Override
    public List<IEventsDTO> findUpcomingEventsForPeriod(Date starts, Date ends) {
        List<EventsEntity> entities = eventsRepository.findUpcomingEventsForPeriod(starts, ends);

        return convertListEntityToDto(entities);
    }

    public EventsEntity convertDtoToEntity(IEventsDTO dto) {
        return conversionService.convert(dto, EventsEntity.class);
    }

    public IEventsDTO convertEntityToDto(EventsEntity entity) {
        return conversionService.convert(entity, IEventsDTO.class);
    }

}
