/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import core.dto.api.IAllocationTableDTO;
import core.dto.api.INotificationLogTableDTO;
import core.enums.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalDateTimeTextField;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import service.api.IAllocationService;
import service.api.IEventsService;
import service.api.IGroupsService;
import service.api.INotificationsLogService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static core.commons.Utils.*;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

/**
 * Created by byaxe on 03.12.16.
 */
@Component
public class ApplicationController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

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
    public Pane calendarRightPaneBottomBlock;
    public SplitPane scheduleSplitPane;
    public AnchorPane scheduleLeftPane;
    public Label scheduleGroupsLabel;
    public JFXListView scheduleGroupsList;
    public AnchorPane scheduleRightPane;
    public ICalendarAgenda scheduleAgenda;
    public JFXCheckBox allocationArchiveFlag;
    public Tab notificationsTab;
    public TableView<IAllocationTableDTO> allocationTable;
    public Tab allocationTableTab;
    public Label allocationTableStageLabel;
    public JFXComboBox allocationTableStageComboBox;
    public JFXButton allocationTablePrintButton;
    public AnchorPane allocationTableBottomPane;
    public TableColumn allocationTableId;
    public TableColumn allocationTableStudent;
    public TableColumn allocationTableStudentFirstName;
    public TableColumn allocationTableStudentLastName;
    public TableColumn allocationTableStudentMiddleName;
    public TableColumn allocationTableStudentAddress;
    public TableColumn allocationTableStudentTelephone;
    public TableColumn allocationTableGroupNumber;
    public TableColumn allocationTableOrganisation;
    public TableColumn allocationTableOrganisationTitle;
    public TableColumn allocationTableOrganisationAddress;
    public TableColumn allocationTableOrganisationTelephone;
    public TableColumn allocationTableOrganisationContacts;
    public TableColumn allocationTableOrder;
    public TableColumn allocationTableOrderDate;
    public TableColumn allocationTableOrderNumber;
    public TableColumn allocationTableOrderProfession;
    public TableColumn allocationTableOrderRank;
    public TableColumn allocationTableConfirmations;
    public TableColumn allocationTableConfirmations_1;
    public TableColumn allocationTableConfirmations_2;
    public TableColumn allocationTableConfirmations_3;
    public TableColumn allocationTableConfirmations_4;
    public TableColumn allocationTableConfirmations_5;
    public TableColumn allocationTableConfirmations_6;
    public TableColumn allocationTableConfirmations_7;
    public TableColumn allocationTableConfirmations_8;
    public TableColumn allocationTableConfirmations_9;
    public TableColumn allocationTableConfirmations_10;
    public TableColumn allocationTableArmy;
    public TableColumn allocationTableFreeAllocation;
    public TableColumn allocationTableFreeAllocationFlag;
    public TableColumn allocationTableFreeAllocationReason;
    public TableColumn allocationTableReallocation;
    public TableColumn allocationTableReallocationFlag;
    public TableColumn allocationTableReallocationId;
    public TableColumn allocationTableVoluntaryCompensation;
    public TableColumn allocationTableVoluntaryCompensationOrderDate;
    public TableColumn allocationTableVoluntaryCompensationOrderNumber;
    public TableColumn allocationTableVoluntaryCompensationConfirmationDate;
    public TableColumn allocationTableCortCompensation;
    public TableColumn allocationTableCortCompensationOrderNumber;
    public TableColumn allocationTableCortCompensationOrderDate;
    public JFXButton allocationTableFilterApplyButton;
    public Label allocationTableTitle;
    public JFXTextField allocationTableIssueYear;
    public TableView<INotificationLogTableDTO> notificationsTable;
    public TableColumn notificationsTableId;
    public TableColumn notificationsTableDate;
    public TableColumn notificationsTableType;
    public TableColumn notificationsTableDescription;
    public JFXButton notificationsClearButton;

    private ResourceBundle resourceBundle;
    private URL location;

    private IEventsService eventsService;
    private IGroupsService groupsService;
    private IAllocationService allocationService;
    private INotificationsLogService notificationsLogService;

    @Value("${calendar.upcoming.events.label.text}")
    private String upcomingEventsLabelText;

    @Value("${help.body}")
    private String HELP_BODY;

    @Value("${about.body}")
    private String ABOUT_BODY;

    public ApplicationController() {
    }

    @Autowired
    public ApplicationController(IEventsService eventsService, IGroupsService groupsService, IAllocationService allocationService, INotificationsLogService notificationsLogService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.allocationService = allocationService;
        this.notificationsLogService = notificationsLogService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.location = location;

        initCalendarTab();
        initScheduleTab();
        initAllocationTab();
        initAllocationTableTab();
        initNotificationTab();
    }

    /**
     * Дергает и делает все, что вкладка Ежедневник была успешна инициализированна и заполнена
     */
    private void initCalendarTab() {
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
        eventsService.createNewEvent(
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
        eventsService.cleanEventForm(
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
        eventsService.showPreferences();
    }*/

    @FXML
    private void handleHelp(ActionEvent actionEvent) {
        raiseMessageBoxWithCustomSize(INFORMATION, "Помощь", null, HELP_BODY, 400, 600);
    }

    @FXML
    private void handleAbout(ActionEvent actionEvent) {
        raiseMessageBoxWithCustomSize(INFORMATION, "О программе", null, ABOUT_BODY, 400, 600);
    }

    /**
     * Выводит в список все планируемые события
     */
    @FXML
    private void calendarHandleUpdateUpcomingEventsList() {
        calendarUpcomingEventsLabel.setText(upcomingEventsLabelText);
        eventsService.fillUpcomingEventsList(calendarUpcomingEventsListView);
    }

    /**
     * Заполняет комбобоксы календаря перечислениями
     */
    private void calendarFillComboBoxes() {
        eventsService.fillComboBoxes(
                calendarNoticePeriodPicker,
                calendarFrequencyPicker,
                calendarPriorityPicker
        );
    }

    /**
     *
     */
    private void calendarAddContextMenuToUpcomingEventsList() {
        eventsService.addContextMenuToUpcomingEventsList(calendarUpcomingEventsListView);
    }

    /**
     * Заполнения списка предстоящих событий в календаре
     * Отсортирован по началу события (ASC) (сверху - ближайшие)
     */
    private void calendarFillUpcomingEventsList() {
        calendarUpcomingEventsLabel.setText(upcomingEventsLabelText);
        eventsService.fillUpcomingEventsList(calendarUpcomingEventsListView);
    }

    /**
     * Создаем большой календарь для вкладки "Ежедневник"
     */
    private void calendarCreateBigCalendar() {
        eventsService.createBigCalendar(calendarRightPaneBottomBlock, calendarUpcomingEventsLabel, calendarUpcomingEventsListView);
    }

    /**
     * Устанавливаем спец формат для дата в полях выбора начала и конца нового события
     */
    private void calendarSetFormatForDatePickers() {
        calendarStartDatePicker.setDateTimeFormatter(CALENDAR_DATE_PICKER_FORMATTER);
        calendarEndDatePicker.setDateTimeFormatter(CALENDAR_DATE_PICKER_FORMATTER);
    }

    /**
     * Дергает и делает все, что вкладка Расписание была успешна инициализированна и заполнена
     */
    private void initScheduleTab() {
    }

    /**
     * Дергает и делает все, что вкладка Распределение была успешна инициализированна и заполнена
     */
    private void initAllocationTab() {
    }

    /**
     * Дергает и делает все, что вкладка Распределение (Таблица) была успешна инициализированна и заполнена
     */
    private void initAllocationTableTab() {
        allocationTableFillComboBox();
        allocationTableSetDefaultValues();
        allocationTableAddFactoryForColumns();
        allocationTableLoadContentDefault();
    }

    /**
     * Даем колонкам понять, откуда брать значения для заполнения
     */
    private void allocationTableAddFactoryForColumns() {

        // Устанавливаем Идентификатор записи
        allocationTableId.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("id"));

        // Заполняем колонки по Выпускнику
        {
            allocationTableStudentFirstName.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("studentFirstName"));
            allocationTableStudentMiddleName.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("studentMiddleName"));
            allocationTableStudentLastName.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("studentLastName"));
            allocationTableStudentAddress.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("studentAddress"));
            allocationTableStudentTelephone.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("studentTelephone"));
            allocationTableGroupNumber.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("groupNumber"));
        }

        // Заполняем колонки по Организации
        {
            allocationTableOrganisationTitle.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("organisationTitle"));
            allocationTableOrganisationAddress.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("organisationAddress"));
            allocationTableOrganisationTelephone.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("organisationTelephone"));
            allocationTableOrganisationContacts.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("organisationContacts"));
        }

        // Заполняем колонки по Приказу о приеме на работу
        {
            allocationTableOrderDate.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("orderDate"));
            allocationTableOrderNumber.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("orderNumber"));
            allocationTableOrderProfession.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("orderProfession"));
            allocationTableOrderRank.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("orderRank"));
        }

        // Заполняем подтверждения. Выгядит как ужасный костыль...
        {
            allocationTableConfirmations_1.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_1"));
            allocationTableConfirmations_2.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_2"));
            allocationTableConfirmations_3.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_3"));
            allocationTableConfirmations_4.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_4"));
            allocationTableConfirmations_5.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_5"));
        }

        // Другие настройки
        {
            allocationTableArmy.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("army"));
            allocationTableFreeAllocationFlag.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("freeAllocationFlag"));
            allocationTableFreeAllocationReason.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("freeAllocationReason"));
            allocationTableReallocationFlag.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("reallocationFlag"));
            allocationTableReallocationId.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("army"));
        }

        // Компенсации и т.д.
        {
            allocationTableVoluntaryCompensationOrderDate.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("voluntaryCompensationOrderDate"));
            allocationTableVoluntaryCompensationOrderNumber.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("voluntaryCompensationOrderNumber"));
            allocationTableVoluntaryCompensationConfirmationDate.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("voluntaryCompensationConfirmationDate"));
            allocationTableCortCompensationOrderNumber.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("cortCompensationOrderNumber"));
            allocationTableCortCompensationOrderDate.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("cortCompensationOrderDate"));

        }
    }

    /**
     * Заполняем комбобоксы на вкладке Распределение (таблица)
     */
    private void allocationTableFillComboBox() {
        allocationService.fillComboBox(allocationTableStageComboBox);
    }

    /**
     * Устанавливаем значения по умолчанию на вкладке Распределение (таблица)
     */
    private void allocationTableSetDefaultValues() {
        allocationService.setDefaultValues(allocationTableStageComboBox);
        allocationTableIssueYear.setValidators(new NumberValidator());

    }

    /**
     * Загружает записи в таблицу Распределение
     */
    private void allocationTableLoadContent(Stage stage, Boolean archive, Integer issueYear) {
        allocationTableModifyColumns(stage, issueYear);

        ObservableList<IAllocationTableDTO> content = FXCollections.observableArrayList();
        content.addAll(allocationService.find(stage, archive, issueYear));

        allocationTable.setItems(content);

        logger.info("Загружен контент в таблицу распределения.");
    }

    /**
     * Загружает данные по умолчанию в таблицу Распределение
     */
    private void allocationTableLoadContentDefault() {
        allocationTableLoadContent(Stage.FIRST, false, LocalDateTime.now().getYear());
    }

    /**
     * Модифицирует колонки таблицы Распределение
     *
     * @param stage     ступень
     * @param issueYear год выпуска
     */
    private void allocationTableModifyColumns(final Stage stage, final int issueYear) {
        TableColumnBase confirmationsParent = allocationTableConfirmations_1.getParentColumn();

        if (stage == Stage.SECOND) {

            final int nextYear = issueYear + 1;
            final int nextNextYear = nextYear + 1;

            addYearToColumnTitle(allocationTableConfirmations_1, issueYear);
            addYearToColumnTitle(allocationTableConfirmations_2, issueYear);
            addYearToColumnTitle(allocationTableConfirmations_3, nextYear);
            addYearToColumnTitle(allocationTableConfirmations_4, nextYear);
            addYearToColumnTitle(allocationTableConfirmations_5, nextYear);

            allocationTableConfirmations_6 = createTableColumn(CONFIRMATION_PERIOD_1);
            addYearToColumnTitle(allocationTableConfirmations_6, nextYear);

            allocationTableConfirmations_7 = createTableColumn(CONFIRMATION_PERIOD_2);
            addYearToColumnTitle(allocationTableConfirmations_7, nextYear);

            allocationTableConfirmations_8 = createTableColumn(CONFIRMATION_PERIOD_3);
            addYearToColumnTitle(allocationTableConfirmations_8, nextNextYear);

            allocationTableConfirmations_9 = createTableColumn(CONFIRMATION_PERIOD_4);
            addYearToColumnTitle(allocationTableConfirmations_9, nextNextYear);

            allocationTableConfirmations_10 = createTableColumn(CONFIRMATION_PERIOD_5);
            addYearToColumnTitle(allocationTableConfirmations_10, nextNextYear);

            allocationTableConfirmations_6.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_6"));
            allocationTableConfirmations_7.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_7"));
            allocationTableConfirmations_8.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_8"));
            allocationTableConfirmations_9.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_9"));
            allocationTableConfirmations_10.setCellValueFactory(new PropertyValueFactory<IAllocationTableDTO, String>("confirmations_10"));

            confirmationsParent.getColumns()
                    .setAll(allocationTableConfirmations_1, allocationTableConfirmations_2,
                            allocationTableConfirmations_3, allocationTableConfirmations_4,
                            allocationTableConfirmations_5, allocationTableConfirmations_6,
                            allocationTableConfirmations_7, allocationTableConfirmations_8,
                            allocationTableConfirmations_9, allocationTableConfirmations_10);
        } else {
            confirmationsParent.getColumns()
                    .setAll(allocationTableConfirmations_1, allocationTableConfirmations_2,
                            allocationTableConfirmations_3, allocationTableConfirmations_4,
                            allocationTableConfirmations_5);
        }
    }

    /**
     * Применяет выбранные фильтры к выборке данных
     *
     * @param actionEvent
     */
    @FXML
    private void allocationTableFilterApply(ActionEvent actionEvent) {
        try {
            boolean archive = allocationArchiveFlag.isSelected();

            int issueYear = allocationTableIssueYear.validate()
                    ? Integer.valueOf(allocationTableIssueYear.getText())
                    : LocalDateTime.now().getYear();

            Stream.of(Stage.values()).forEach(s -> {
                if (s.getTitle().equals(String.valueOf(allocationTableStageComboBox.getValue()))) {
                    allocationTableLoadContent(s, archive, issueYear);
                }
            });

        } catch (Exception e) {
            allocationTableLoadContentDefault();
            logger.error("Ошибка при попытке фильтрации данных. Загружены фильтры по умолчанию", e);
        }

    }

    /**
     * Печатает содержимое Таблицы Распредления
     *
     * @param actionEvent
     */
    @FXML
    private void allocationPrintButtonClick(ActionEvent actionEvent) {
        // TODO Add printing Job
        System.out.println("Print it!");
    }

    /**
     * Дергает и делает все, что вкладка Уведомлений была успешна инициализированна и заполнена
     */
    private void initNotificationTab() {
        notificationsTableAddFactoryForColumns();
        notificationsTableUpdate();
    }

    /**
     * Обновляем таблицу Уведомлений
     */
    private void notificationsTableUpdate() {
        ObservableList<INotificationLogTableDTO> content = FXCollections.observableArrayList();
        content.addAll(notificationsLogService.findAllForTable());

        notificationsTable.setItems(content);

        logger.info("Загружен контент в таблицу уведомлений.");
    }

    /**
     * Даем колонкам понять, откуда брать значения для заполнения
     */
    private void notificationsTableAddFactoryForColumns() {
        notificationsTableId.setCellValueFactory(new PropertyValueFactory<INotificationLogTableDTO, String>("id"));
        notificationsTableDate.setCellValueFactory(new PropertyValueFactory<INotificationLogTableDTO, String>("date"));
        notificationsTableType.setCellValueFactory(new PropertyValueFactory<INotificationLogTableDTO, String>("type"));
        notificationsTableDescription.setCellValueFactory(new PropertyValueFactory<INotificationLogTableDTO, String>("description"));
    }

    /**
     * Обрабатываем нажатие на очистку уведомлений
     *
     * @param actionEvent
     */
    @FXML
    public void handleNotificationsClear(ActionEvent actionEvent) {
        notificationsLogService.deleteAll();
        notificationsTable.setItems(FXCollections.observableArrayList());
    }

    /**
     * Нажатие на вкладку "Уведомлений"
     *
     * @param event
     */
    public void notificationsTabChoose(Event event) {
        notificationsTableUpdate();
    }
}
