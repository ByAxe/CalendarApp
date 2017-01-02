/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import core.dto.AllocationDTOImpl;
import core.dto.api.*;
import core.enums.CompensationType;
import core.enums.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import jfxtras.scene.control.LocalDateTextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import service.api.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static core.commons.Utils.*;
import static core.enums.CompensationType.*;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.ERROR;
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
    public JFXButton allocationTableExcelButton;
    public Pane allocationHeaderPane;
    public Label allocationStudentLabel;
    public JFXButton allocationEditButton;
    public JFXTextField allocationSearchTextField;
    public JFXComboBox allocationStudentsList;
    public Label allocationGroupLabel;
    public JFXComboBox allocationGroupsList;
    public JFXCheckBox allocationArchive;
    public Label allocationOrganisationLabel;
    public JFXComboBox allocationOrganisationsList;
    public JFXCheckBox allocationArmy;
    public JFXCheckBox allocationFreeAllocation;
    public JFXTextArea allocationFreeAllocationReason;
    public Pane allocationConfirmationsPane;
    public Label allocationConfirmationsLabel;
    public JFXCheckBox allocationConfirmation_1;
    public JFXCheckBox allocationConfirmation_2;
    public JFXCheckBox allocationConfirmation_3;
    public JFXCheckBox allocationConfirmation_4;
    public JFXCheckBox allocationConfirmation_5;
    public JFXCheckBox allocationConfirmation_6;
    public JFXCheckBox allocationConfirmation_7;
    public JFXCheckBox allocationConfirmation_8;
    public JFXCheckBox allocationConfirmation_9;
    public JFXCheckBox allocationConfirmation_10;
    public JFXTextField allocationIssueYear;
    public Pane allocationOrderPane;
    public Label allocationOrderLabel;
    public JFXTextField allocationOrderNumber;
    public LocalDateTextField allocationOrderDate;
    public JFXTextField allocationOrderProfession;
    public JFXTextField allocationOrderRank;
    public JFXTextArea allocationOrderPayload;
    public JFXCheckBox allocationReallocation;
    public Pane allocationCompensationPane;
    public Label allocationCompensationLabel;
    public JFXTextField allocationCompensationNumber;
    public LocalDateTextField allocationCompensationDate;
    public JFXComboBox allocationCompensationType;
    public LocalDateTextField allocationCompensationConfirmationDate;
    public JFXButton allocationSaveButton;
    public Tab managementTab;
    public Tab managementStudentsTab;
    public JFXTextField managementStudentLastName;
    public JFXTextField managementStudentFirstName;
    public JFXTextField managementStudentMiddleName;
    public JFXTextField managementStudentTelephone;
    public JFXTextField managementStudentAddress;
    public JFXButton managementStudentCleanButton;
    public JFXButton managementStudentSaveButton;
    public JFXListView<Label> managementStudentsList;
    public Tab managementGroupsTab;
    public JFXTextField managementGroupTitle;
    public JFXTextField managementGroupNumber;
    public JFXTextField managementGroupSpecialization;
    public JFXTextField managementGroupQualification;
    public JFXTextField managementGroupDescription;
    public JFXComboBox managementGroupRulers;
    public JFXListView<Label> managementGroupsList;
    public JFXButton managementGroupCleanButton;
    public JFXButton managementGroupSaveButton;
    public JFXListView<Label> managementRulersList;
    public JFXTextField managementRulerLastName;
    public JFXTextField managementRulerFirstName;
    public JFXTextField managementRulerMiddleName;
    public JFXTextField managementRulerPayload;
    public JFXButton managementRulerCleanButton;
    public JFXButton managementRulerSaveButton;
    public Tab managementRulersTab;
    public Tab managementOrganisationsTab;
    public JFXTextField managementOrganisationTitle;
    public JFXTextField managementOrganisationAddress;
    public JFXTextField managementOrganisationTelephone;
    public JFXTextField managementOrganisationContacts;
    public JFXListView<Label> managementOrganisationsList;
    public JFXButton managementOrganisationCleanButton;
    public JFXButton managementOrganisationSaveButton;
    public JFXTextField managementStudentId;
    public JFXTextField managementGroupId;
    public JFXTextField managementRulerId;
    public JFXTextField managementOrganisationId;
    public JFXTextField managementGroupHours;
    public JFXButton allocationCleanButton;
    public Label allocationStageLabel;
    public JFXComboBox allocationStage;
    public Label allocationCompensationConfirmationDateLabel;
    public Label allocationCompensationDateLabel;
    public JFXTextField allocationReallocationId;
    public JFXTextField allocationId;

    private ResourceBundle resourceBundle;
    private URL location;

    private IEventsService eventsService;
    private IGroupsService groupsService;
    private IAllocationService allocationService;
    private INotificationsLogService notificationsLogService;
    private IStudentsService studentsService;
    private IOrganisationsService organisationsService;
    private IRulersService rulersService;

    @Value("${calendar.upcoming.events.label.text}")
    private String upcomingEventsLabelText;

    @Value("${help.body}")
    private String HELP_BODY;

    @Value("${about.body}")
    private String ABOUT_BODY;

    @Value("${confirmation.message}")
    private String CONFIRMED;

    public ApplicationController() {
    }

    @Autowired
    public ApplicationController(IEventsService eventsService, IGroupsService groupsService, IAllocationService allocationService,
                                 INotificationsLogService notificationsLogService, IStudentsService studentsService,
                                 IOrganisationsService organisationsService, IRulersService rulersService) {
        this.eventsService = eventsService;
        this.groupsService = groupsService;
        this.allocationService = allocationService;
        this.notificationsLogService = notificationsLogService;
        this.studentsService = studentsService;
        this.organisationsService = organisationsService;
        this.rulersService = rulersService;
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
        initManagementTab();

        refresh();
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
    private void allocationTableLoadContent(Stage stage, Boolean archive, Integer issueYear, Integer issueMonth) {
        allocationTableModifyColumns(stage, issueYear);

        ObservableList<IAllocationTableDTO> content = FXCollections.observableArrayList();
        content.addAll(allocationService.find(stage, archive, issueYear, issueMonth));

        allocationTable.setItems(content);

        logger.info("Загружен контент в таблицу распределения.");
    }

    /**
     * Загружает данные по умолчанию в таблицу Распределение
     */
    private void allocationTableLoadContentDefault() {
        LocalDateTime now = LocalDateTime.now();

        allocationTableLoadContent(Stage.FIRST, false, now.getYear(), now.getMonthValue());
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
            final boolean archive = allocationArchiveFlag.isSelected();
            final LocalDateTime now = LocalDateTime.now();

            final int issueYear;
            final int issueMonth;

            if (allocationTableIssueYear.validate()) {
                issueYear = Integer.valueOf(allocationTableIssueYear.getText());
            } else {
                issueYear = now.getYear();
            }

            if (allocationTableIssueMonth.validate()) {
                issueMonth = Integer.valueOf(allocationTableIssueMonth.getText());
            } else {
                issueMonth = now.getMonthValue();
            }

            Stream.of(Stage.values()).forEach(s -> {
                if (s.getTitle().equals(String.valueOf(allocationTableStageComboBox.getValue()))) {
                    allocationTableLoadContent(s, archive, issueYear, issueMonth);
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

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        raiseMessageBox(ERROR, "Ошибка", "Ошибка при поптыке печати.", "В системе не найден принтер!\nЧтобы напечатать что либо, вы должны сначала подключить принтер.");
        if (printerJob == null) return; // нет принтера

        if (!printerJob.showPrintDialog(null)) return;

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A2, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        printerJob.getJobSettings().setPageLayout(pageLayout);

        printerJob.printPage(allocationTable);
    }

    /**
     * Сохраняет содержимое Таблицы Распредления в excel файл
     *
     * @param actionEvent
     */
    @FXML
    private void allocationExcelButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        String allocationYear = allocationTableIssueYear.validate() ? allocationTableIssueYear.getText() : String.valueOf(2016);
        fileChooser.setInitialFileName("Учет распределения (выпуск " + allocationYear + " года).xls");

        fileChooser.setTitle("Сохранить таблицу");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                allocationService.exportTableToExcel(file, allocationTable);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    /**
     * Дергает и делает все, что вкладка Распределение была успешна инициализированна и заполнена
     */
    private void initAllocationTab() {
        allocationSearchTextField.setValidators(new NumberValidator());
        allocationIssueYear.setValidators(new NumberValidator());
        allocationReallocationId.setValidators(new NumberValidator());
        allocationOrderRank.setValidators(new NumberValidator());

        // Загружаем данные в выпадающие списки
        allocationCompensationType.getItems()
                .setAll(Stream.of(values()).map(CompensationType::getType).collect(toList()));

        allocationStage.getItems()
                .setAll(Stream.of(Stage.values()).map(Stage::getAcronym).collect(toList()));

        // Устанавливаем форматы дат
        allocationOrderDate.setDateTimeFormatter(ALLOCATION_TABLE_FORMATTER);
        allocationCompensationDate.setDateTimeFormatter(ALLOCATION_TABLE_FORMATTER);
        allocationCompensationConfirmationDate.setDateTimeFormatter(ALLOCATION_TABLE_FORMATTER);

        // Убираем поля из видимости
        allocationCompensationConfirmationDate.setVisible(false);

        // Устанавливаем в выпадающие списки значения по умолчанию
        allocationStage.setValue(allocationStage.getItems().get(1));
        allocationCompensationType.setValue(allocationCompensationType.getItems().get(0));

        // Активируем вписывание в поле перераспределения идентификатора
        allocationReallocation.setOnAction(e -> allocationReallocationId.setDisable(!allocationReallocation.isSelected()));

        // По выбору ступени меняется количество флажков подтверждения
        allocationStage.setOnAction(e -> Stream.of(Stage.values()).forEach(f -> {
            if (f.getAcronym().equals(String.valueOf(allocationStage.getValue()))) {
                if (f == Stage.FIRST) {
                    allocationConfirmation_6.setVisible(false);
                    allocationConfirmation_7.setVisible(false);
                    allocationConfirmation_8.setVisible(false);
                    allocationConfirmation_9.setVisible(false);
                    allocationConfirmation_10.setVisible(false);
                } else {
                    allocationConfirmation_6.setVisible(true);
                    allocationConfirmation_7.setVisible(true);
                    allocationConfirmation_8.setVisible(true);
                    allocationConfirmation_9.setVisible(true);
                    allocationConfirmation_10.setVisible(true);
                }
            }
        }));

        // Играем с полями Компенсации
        allocationCompensationType.setOnAction(e -> Stream.of(values()).forEach(t -> {
            if (t.getType().equals(String.valueOf(allocationCompensationType.getValue()))) {
                if (t == NONE) {
                    allocationCompensationNumber.setDisable(true);
                    allocationCompensationDate.setDisable(true);
                    allocationCompensationConfirmationDateLabel.setVisible(false);
                    allocationCompensationConfirmationDate.setVisible(false);
                } else if (t == VOLUNTARY) {
                    allocationCompensationNumber.setDisable(false);
                    allocationCompensationDate.setDisable(false);
                    allocationCompensationConfirmationDateLabel.setVisible(true);
                    allocationCompensationConfirmationDate.setVisible(true);
                } else if (t == CORT) {
                    allocationCompensationNumber.setDisable(false);
                    allocationCompensationDate.setDisable(false);
                    allocationCompensationConfirmationDateLabel.setVisible(false);
                    allocationCompensationConfirmationDate.setVisible(false);
                }
            }
        }));

        // В зависимости от года выпуска, выставляем годы подтверждения
        allocationIssueYear.focusedProperty().addListener((a, o, n) -> {
            if (!allocationIssueYear.validate() || n) return;

            final int year = Integer.valueOf(allocationIssueYear.getText());
            final int nextYear = year + 1;
            final int nextNextYear = nextYear + 1;

            allocationConfirmation_1.setText(CONFIRMATION_PERIOD_1 + " " + year);
            allocationConfirmation_2.setText(CONFIRMATION_PERIOD_2 + " " + year);
            allocationConfirmation_3.setText(CONFIRMATION_PERIOD_3 + " " + nextYear);
            allocationConfirmation_4.setText(CONFIRMATION_PERIOD_4 + " " + nextYear);
            allocationConfirmation_5.setText(CONFIRMATION_PERIOD_5 + " " + nextYear);

            allocationConfirmation_6.setText(CONFIRMATION_PERIOD_1 + " " + nextYear);
            allocationConfirmation_7.setText(CONFIRMATION_PERIOD_2 + " " + nextYear);
            allocationConfirmation_8.setText(CONFIRMATION_PERIOD_3 + " " + nextNextYear);
            allocationConfirmation_9.setText(CONFIRMATION_PERIOD_4 + " " + nextNextYear);
            allocationConfirmation_10.setText(CONFIRMATION_PERIOD_5 + " " + nextNextYear);
        });

        // Они одинаковы по смыслу, поэтому по нажатию на одно - должно отмечаться и другое
        allocationConfirmation_5.setOnAction(e -> allocationConfirmation_6.setSelected(allocationConfirmation_5.isSelected()));
        allocationConfirmation_6.setOnAction(e -> allocationConfirmation_5.setSelected(allocationConfirmation_6.isSelected()));

        // Если указано, что выпускник имеет свободное распределение - то обязываем указать причину
        allocationFreeAllocation.setOnAction(e -> allocationFreeAllocationReason.setDisable(!allocationFreeAllocation.isSelected()));

        allocationReallocationId.setDisable(true);
        allocationCompensationType.setValue(allocationCompensationType.getItems().get(1));

        allocationCleanButton.fire();
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
        notificationsTab.setText("Уведомления");
        notificationsTable.setItems(FXCollections.observableArrayList());
    }

    /**
     * Нажатие на вкладку "Уведомлений"
     *
     * @param event
     */
    @FXML
    private void notificationsTabChoose(Event event) {
        notificationsTableUpdate();
    }

    @FXML
    private void allocationEditButtonClick(ActionEvent actionEvent) {

        if (allocationSearchTextField.validate()) {
            final long id = Long.parseLong(allocationSearchTextField.getText());


            IAllocationDTO allocation = allocationService.findOne(id);

            if (allocation == null) {
                raiseMessageBox(INFORMATION, "Не найдено", "Поиск не дал результатов", "Запись о распределении с данным номером не найдена.");
                return;
            }
            allocationId.setText(String.valueOf(id));

            allocationCompensationType.getItems()
                    .setAll(Stream.of(values()).map(CompensationType::getType).collect(toList()));

            IStudentsDTO student = allocation.getStudent();
            IGroupsDTO group = student.getGroup();
            IOrganisationsDTO organisation = allocation.getOrganisation();
            IOrdersDTO order = allocation.getOrder();

            // TODO разложить данные по полям
            allocationStudentsList.setValue(student.toPrettyString());
            allocationOrganisationsList.setValue(organisation.toPrettyString());
            allocationGroupsList.setValue(group.toPrettyString());

            allocationArchive.setSelected(allocation.isArchive());
            allocationArmy.setSelected(allocation.isArmy());

            if (allocation.getParentId() != null) {
                allocationReallocation.setSelected(true);
                allocationReallocationId.setText(String.valueOf(allocation.getParentId()));
            }

            if (allocation.isFreeAllocation()) {
                allocationFreeAllocation.setSelected(true);
                allocationFreeAllocationReason.setText(allocation.getFreeAllocationReason());
            }

            allocationOrderNumber.setText(order.getNumber());
            allocationOrderDate.setLocalDate(LocalDate.from(order.getStarts()));
            allocationOrderProfession.setText(order.getProfession());
            allocationOrderRank.setText(String.valueOf(order.getRank()));
            allocationOrderPayload.setText(order.getPayload());
            allocationIssueYear.setText(String.valueOf(group.getIssueYear()));
            allocationIssueMonth.setText(String.valueOf(group.getIssueMonth()));

            String[] c = allocation.getConfirmations();

            allocationConfirmation_1.setSelected(c[0] != null);
            allocationConfirmation_2.setSelected(c[1] != null);
            allocationConfirmation_3.setSelected(c[2] != null);
            allocationConfirmation_4.setSelected(c[3] != null);
            allocationConfirmation_5.setSelected(c[4] != null);

            if (group.getStage() == Stage.SECOND) {
                allocationConfirmation_6.setSelected(c[5] != null);
                allocationConfirmation_7.setSelected(c[6] != null);
                allocationConfirmation_8.setSelected(c[7] != null);
                allocationConfirmation_9.setSelected(c[8] != null);
                allocationConfirmation_10.setSelected(c[9] != null);
            }

            CompensationType compensationType = allocation.getCompensationType();

            if (compensationType != NONE) {
                allocationCompensationType.setValue(compensationType.getType());
                allocationCompensationNumber.setText(allocation.getCompensationOrderNumber());
                allocationCompensationDate.setLocalDate(LocalDate.from(allocation.getCompensationOrderDate()));

                if (compensationType == VOLUNTARY) {
                    allocationCompensationConfirmationDate.setLocalDate(LocalDate.from(allocation.getVoluntaryCompensationConfirmationDate()));
                }
            }

            allocationStage.setValue(group.getStage().getAcronym());
        }
    }


    @FXML
    private void allocationSaveButtonClick(ActionEvent actionEvent) {
        IAllocationDTO row = new AllocationDTOImpl();

        IStudentsDTO student = studentsService.findOne(getIdFromComboBox(String.valueOf(allocationStudentsList.getValue())));
        IOrganisationsDTO organisation = organisationsService.findOne(getIdFromComboBox(String.valueOf(allocationOrganisationsList.getValue())));
        IOrdersDTO order = groupsService.findOne(getIdFromComboBox(String.valueOf(allocationOrdersList.getValue())));

//        IOrdersDTO order = new OrdersDTOImpl();
//        order.setUuid(UUID.randomUUID());
//        order.setDtUpdate(new Date());
//        order.setNumber(allocationOrderNumber.getText());
//        order.setStarts(convertLocalDateToLocalDateTime(allocationOrderDate.getLocalDate()));
//        order.setProfession(allocationOrderProfession.getText());
//        order.setRank(allocationOrderRank.validate() ? Integer.valueOf(allocationOrderRank.getText()) : null);
//        order.setPayload(allocationOrderPayload.getText());

        row.setStudent(student);
        row.setOrganisation(organisation);
        row.setOrder(order);

        row.setId(Long.valueOf(allocationId.getText()));

        row.setArchive(allocationArchive.isSelected());
        row.setArmy(allocationArmy.isSelected());

        if (allocationReallocation.isSelected() && allocationReallocationId.validate()) {
            row.setParentId(Long.valueOf(allocationReallocationId.getText()));
        }

        row.setFreeAllocation(allocationFreeAllocation.isSelected());
        row.setFreeAllocationReason(allocationFreeAllocation.isSelected() ? allocationFreeAllocationReason.getText() : null);

        final String[] confirmations = new String[10];

        confirmations[0] = allocationConfirmation_1.isSelected() ? CONFIRMED : null;
        confirmations[1] = allocationConfirmation_2.isSelected() ? CONFIRMED : null;
        confirmations[2] = allocationConfirmation_3.isSelected() ? CONFIRMED : null;
        confirmations[3] = allocationConfirmation_4.isSelected() ? CONFIRMED : null;
        confirmations[4] = allocationConfirmation_5.isSelected() ? CONFIRMED : null;

        if (student.getGroup().getStage() == Stage.SECOND) {
            confirmations[5] = allocationConfirmation_6.isSelected() ? CONFIRMED : null;
            confirmations[6] = allocationConfirmation_7.isSelected() ? CONFIRMED : null;
            confirmations[7] = allocationConfirmation_8.isSelected() ? CONFIRMED : null;
            confirmations[8] = allocationConfirmation_9.isSelected() ? CONFIRMED : null;
            confirmations[9] = allocationConfirmation_10.isSelected() ? CONFIRMED : null;
        }
        row.setConfirmations(confirmations);

        Stream.of(CompensationType.values()).forEach(c -> {
            if (c.getType().equals(allocationCompensationType.getValue())) row.setCompensationType(c);
        });
        row.setCompensationOrderNumber(allocationCompensationNumber.getText());
        row.setCompensationOrderDate(convertLocalDateToLocalDateTime(allocationCompensationDate.getLocalDate()));
        row.setVoluntaryCompensationConfirmationDate(convertLocalDateToLocalDateTime(allocationCompensationConfirmationDate.getLocalDate()));

        allocationService.save(row);

        allocationCleanForm();
    }

    @FXML
    private void allocationCleanButtonClick(ActionEvent actionEvent) {
        allocationCleanForm();
    }

    /**
     * Очищает форму сохранения записей по распределению
     */
    private void allocationCleanForm() {
        allocationId.setText("0");

        allocationStudentsList.setValue(null);
        allocationOrganisationsList.setValue(null);
        allocationGroupsList.setValue(null);

        allocationArchive.setSelected(false);
        allocationArmy.setSelected(false);
        allocationReallocation.setSelected(false);

        allocationFreeAllocation.setSelected(false);
        allocationFreeAllocationReason.setText("");

        allocationOrderNumber.setText("");
        allocationOrderDate.setLocalDate(LocalDate.now());
        allocationOrderProfession.setText("");
        allocationOrderRank.setText("");
        allocationOrderPayload.setText("");

        allocationIssueYear.setText("");

        allocationConfirmation_1.setSelected(false);
        allocationConfirmation_2.setSelected(false);
        allocationConfirmation_3.setSelected(false);
        allocationConfirmation_4.setSelected(false);
        allocationConfirmation_5.setSelected(false);
        allocationConfirmation_6.setSelected(false);
        allocationConfirmation_7.setSelected(false);
        allocationConfirmation_8.setSelected(false);
        allocationConfirmation_9.setSelected(false);
        allocationConfirmation_10.setSelected(false);

        allocationCompensationType.setValue(allocationCompensationType.getItems().get(0));
        allocationCompensationNumber.setDisable(true);
        allocationCompensationDate.setDisable(true);
        allocationCompensationConfirmationDateLabel.setVisible(false);
        allocationCompensationConfirmationDate.setVisible(false);

        allocationCompensationNumber.setText("");
        allocationCompensationDate.setLocalDate(LocalDate.now());
        allocationCompensationConfirmationDate.setLocalDate(LocalDate.now());

        allocationStage.setValue(allocationStage.getItems().get(1));
        allocationReallocationId.setText("");
    }


    /**
     * Инициализируем все вкладки Управления Сущностями
     */
    private void initManagementTab() {
        initManagementStudentsTab();
        initManagementGroupsTab();
        initManagementRulersTab();
        initManagementOrganisationsTab();
    }

    /**
     * Инициализируем вкладку управления выпускниками
     */
    private void initManagementStudentsTab() {
        studentsService.fillStudentsList(managementStudentsList);

        studentsService.addContextMenuToStudentsList(managementStudentsList, managementStudentId,
                managementStudentFirstName, managementStudentMiddleName, managementStudentLastName,
                managementStudentTelephone, managementStudentAddress);

        managementStudentCleanForm();
    }

    /**
     * Инициализируем вкладку управления группами
     */
    private void initManagementGroupsTab() {
        groupsService.fillGroupsList(managementGroupsList);

        managementGroupHours.setValidators(new NumberValidator());

        groupsService.addContextMenuToGroupsList(managementGroupsList, managementGroupId, managementGroupTitle,
                managementGroupQualification, managementGroupNumber, managementGroupSpecialization,
                managementGroupDescription, managementGroupHours, managementGroupRulers);

        managementGroupCleanForm();

    }

    /**
     * Инициализируем вкладку управления руководителями
     */
    private void initManagementRulersTab() {
        rulersService.fillRulersList(managementRulersList);

        rulersService.addContextMenuToRulersList(managementRulersList, managementRulerId, managementRulerFirstName,
                managementRulerMiddleName, managementRulerLastName, managementRulerPayload);

        managementRulersCleanForm();
    }

    /**
     * Инициализируем вкладку управления организациями
     */
    private void initManagementOrganisationsTab() {
        organisationsService.fillOrganisationList(managementOrganisationsList);

        organisationsService.addContextMenuToOrganisationsList(managementOrganisationsList, managementOrganisationId, managementOrganisationTitle,
                managementOrganisationAddress, managementOrganisationTelephone, managementOrganisationContacts);

        managementOrganisationsCleanForm();
    }

    private void managementGroupCleanForm() {
        managementGroupId.setText("0");
        managementGroupTitle.setText("");
        managementGroupQualification.setText("");
        managementGroupNumber.setText("");
        managementGroupSpecialization.setText("");
        managementGroupDescription.setText("");
        managementGroupHours.setText("");
    }


    /**
     * Сохраняем текущую сущность
     *
     * @param actionEvent
     */
    @FXML
    private void managementStudentSaveButtonClick(ActionEvent actionEvent) {

        long id = Long.parseLong(managementStudentId.getText());
        String firstName = managementStudentFirstName.getText();
        String middleName = managementStudentMiddleName.getText();
        String lastName = managementStudentLastName.getText();
        String telephone = managementStudentTelephone.getText();
        String address = managementStudentAddress.getText();

        studentsService.save(id, firstName, middleName, lastName, telephone, address);

        managementStudentCleanForm();
        studentsService.fillStudentsList(managementStudentsList);

        refresh();
    }

    /**
     * Обрабатываем нажатие на кнопку очистки формы сохранения студента
     *
     * @param actionEvent
     */
    @FXML
    private void managementStudentCleanButtonClick(ActionEvent actionEvent) {
        managementStudentCleanForm();
    }

    /**
     * Вычищает данные из полей сохранения сущности выпускника
     */
    private void managementStudentCleanForm() {
        managementStudentId.setText("0");
        managementStudentFirstName.setText("");
        managementStudentMiddleName.setText("");
        managementStudentLastName.setText("");
        managementStudentTelephone.setText("");
        managementStudentAddress.setText("");
    }

    /**
     * Нажатие на кнопку очистить форму сохранения группы
     *
     * @param actionEvent
     */
    @FXML
    private void managementGroupCleanButtonClick(ActionEvent actionEvent) {
        managementGroupCleanForm();
    }

    /**
     * Нажатие на кнопку сохранить группу
     *
     * @param actionEvent
     */
    @FXML
    private void managementGroupSaveButtonClick(ActionEvent actionEvent) {

        Long id = Long.valueOf(managementGroupId.getText());
        String title = managementGroupTitle.getText();
        String qualification = managementGroupQualification.getText();
        String number = managementGroupNumber.getText();
        String specialisation = managementGroupSpecialization.getText();
        String description = managementGroupDescription.getText();
        String hours = managementGroupHours.getText();
        String ruler = String.valueOf(managementGroupRulers.getSelectionModel().getSelectedItem());

        groupsService.save(id, title, qualification, number, specialisation, description, hours, ruler);

        managementGroupCleanForm();

        groupsService.fillGroupsList(managementGroupsList);

        refresh();
    }

    /**
     * Нажатие на кнопку очистить форму создания управляющего
     *
     * @param actionEvent
     */
    @FXML
    private void managementRulerCleanButtonClick(ActionEvent actionEvent) {
        managementRulersCleanForm();
    }

    /**
     * Вычищает данные из полей сохранения сущности управляющего
     */
    private void managementRulersCleanForm() {
        managementRulerId.setText("0");
        managementRulerFirstName.setText("");
        managementRulerMiddleName.setText("");
        managementRulerLastName.setText("");
        managementRulerPayload.setText("");
    }

    @FXML
    private void managementRulerSaveButtonClick(ActionEvent actionEvent) {
        Long id = Long.valueOf(managementRulerId.getText());
        String firstName = managementRulerFirstName.getText();
        String middleName = managementRulerMiddleName.getText();
        String lastName = managementRulerLastName.getText();
        String payload = managementRulerPayload.getText();

        rulersService.save(id, firstName, middleName, lastName, payload);

        managementRulersCleanForm();

        rulersService.fillRulersList(managementRulersList);

        refresh();
    }

    @FXML
    private void managementOrganisationCleanButtonClick(ActionEvent actionEvent) {
        managementOrganisationsCleanForm();
    }

    private void managementOrganisationsCleanForm() {
        managementOrganisationId.setText("0");
        managementOrganisationTitle.setText("");
        managementOrganisationAddress.setText("");
        managementOrganisationTelephone.setText("");
        managementOrganisationContacts.setText("");
    }


    @FXML
    private void managementOrganisationSaveButtonClick(ActionEvent actionEvent) {
        Long id = Long.valueOf(managementOrganisationId.getText());
        String title = managementOrganisationTitle.getText();
        String address = managementOrganisationAddress.getText();
        String telephone = managementOrganisationTelephone.getText();
        String contacts = managementOrganisationContacts.getText();

        organisationsService.save(id, title, address, telephone, contacts);

        managementOrganisationsCleanForm();

        organisationsService.fillOrganisationList(managementOrganisationsList);

        refresh();
    }

    /**
     * Обновляем данные во всех комбобоксах где участвуют изменяемые сущности
     */
    public void refresh() {
        ObservableList mGL = managementGroupRulers.getItems();
        mGL.clear();
        ofNullable(((List<IRulersDTO>) rulersService.findAll()))
                .ifPresent(l -> l.forEach(r -> mGL.add(r.toPrettyString())));

        ObservableList aSL = allocationStudentsList.getItems();
        aSL.clear();
        ofNullable(((List<IStudentsDTO>) studentsService.findAll()))
                .ifPresent(l -> l.forEach(e -> aSL.add(e.toPrettyString())));

        ObservableList aGL = allocationGroupsList.getItems();
        aGL.clear();
        ofNullable(((List<IGroupsDTO>) groupsService.findAll()))
                .ifPresent(l -> l.forEach(e -> aGL.add(e.toPrettyString())));

        ObservableList aOL = allocationOrganisationsList.getItems();
        aOL.clear();
        ofNullable(((List<IOrganisationsDTO>) organisationsService.findAll()))
                .ifPresent(l -> l.forEach(e -> aOL.add(e.toPrettyString())));
    }
}
