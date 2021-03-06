/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.commons;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static core.enums.ResultEnum.ERROR;
import static core.enums.ResultEnum.SUCCESS;
import static javafx.scene.control.ButtonType.OK;

/**
 * Created by byaxe on 26.11.16.
 */
public class Utils {
    public static final String TODAY = "СЕГОДНЯ";
    public static final String TOMORROW = "ЗАВТРА";
    public static final String CONFIRMATION_PERIOD_1 = "Сентябрь";
    public static final String CONFIRMATION_PERIOD_2 = "Декабрь";
    public static final String CONFIRMATION_PERIOD_3 = "Март";
    public static final String CONFIRMATION_PERIOD_4 = "Июнь";
    public static final String CONFIRMATION_PERIOD_5 = CONFIRMATION_PERIOD_1;
    public static final DateTimeFormatter PRETTY_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM HH:mm");
    public static final DateTimeFormatter CALENDAR_ON_DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM");
    public static final DateTimeFormatter CALENDAR_DATE_PICKER_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter ALLOCATION_TABLE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter ISSUE_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final String LOCALE = "ru";

    /**
     * Создает сообщение с помощью {@link Alert}
     *
     * @param type   Тип сообщения
     * @param title  Надпись сверху
     * @param header Заголовок
     * @param body   Основной текст сообщения
     * @return Отдано подтверждение, или отказ (для {@link AlertType.CONFIRMATION})
     */
    public static boolean raiseMessageBox(AlertType type, String title, String header, String body) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.setHeight(300);
        alert.setWidth(200);

        alert.showAndWait();

        return alert.getResult() == OK;
    }

    /**
     * Создает сообщение с помощью {@link Alert}
     *
     * @param type   Тип сообщения
     * @param title  Надпись сверху
     * @param header Заголовок
     * @param body   Основной текст сообщения
     * @param height Высота окна
     * @param width  Ширина окна
     * @return Отдано подтверждение, или отказ (для {@link AlertType.CONFIRMATION})
     */
    public static boolean raiseMessageBoxWithCustomSize(AlertType type, String title, String header, String body, int height, int width) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.setHeight(height);
        alert.setWidth(width);

        alert.showAndWait();

        return alert.getResult() == OK;
    }

    /**
     * Конвертирует {@link Date} в {@link LocalDateTime} с применением текущей локали
     * <p>
     *
     * @param source Исходная дата в {@link Date}
     * @return Результативная дата в {@link LocalDateTime}
     */
    public static LocalDateTime convertDateToLocalDateTime(Date source) {
        if (source == null) return null;
        return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Конвертирует {@link LocalDateTime} в {@link Date} с применением текущей локали
     * <p>
     *
     * @param source Исходная дата в {@link LocalDateTime}
     * @return Результативная дата в {@link Date}
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime source) {
        if (source == null) return null;
        return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param source
     * @return
     */
    public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate source) {
        if (source == null) return null;
        return Timestamp.valueOf(source.atStartOfDay()).toLocalDateTime();
    }

    /**
     * Оборачивает {@link List<String>} ошибок в {@link Result}
     * <p>
     * В случае Успеха (пустой errors) возвращает {@link Result} где лежит {@link core.enums.ResultEnum.SUCCESS},
     * payload {@link List<String>} == null
     * </p>
     * <p>
     * В случае Ошибок (не пустой errors) возвращает {@link Result} где лежит {@link core.enums.ResultEnum.ERROR},
     * payload {@link List<String>} с ошибками
     * </p>
     *
     * @param errors {@link List<String>} ошибок
     * @return {@link Result} специальная обертка
     */
    public static Result wrapResult(final List<String> errors) {
        return errors.isEmpty() ? new Result(SUCCESS) : new Result(ERROR, errors);
    }


    /**
     * Дата в нормальном виде, для заголовка календаря
     *
     * @param date настоящая дата
     * @return форматированная дата
     */
    public static String formatDateForCalendarTitle(Date date) {
        return new SimpleDateFormat("MMMM").format(date) + " " + new SimpleDateFormat("yyyy").format(date);
    }

    /**
     * Возвращает начало дня переданной даты в {@link Date}
     *
     * @param date Дата
     * @return Та же дата, но время установлено на начало дня
     */
    public static Date getStartOfDay(LocalDateTime date) {
        long epochDay = getEpochDays(date);
        return Date.from((LocalDate.ofEpochDay(epochDay).atStartOfDay().atZone(ZoneId.systemDefault())).toInstant());
    }

    /**
     * Возвращает конец ддня переданной даты в {@link Date}
     *
     * @param date Дата
     * @return Та же дата, но время установлено на конец дня 23.59.00
     */
    public static Date getEndOfDay(LocalDateTime date) {
        long epochDay = getEpochDays(date);
        return Date.from((LocalDate.ofEpochDay(epochDay).atTime(23, 59).atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * Возвращает количество дней с начала эпохи по определенной дате
     *
     * @param date Дата
     * @return Дата в днях с начала эпохи
     */
    public static long getEpochDays(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toLocalDate().toEpochDay();
    }


    /**
     * Конвертируем дату {@link LocalDateTime} в {@link SimpleStringProperty}
     *
     * @param source Дата в {@link LocalDateTime}
     * @return Дата в {@link SimpleStringProperty}
     */
    public static SimpleStringProperty dateToStringProperty(LocalDateTime source) {
        return new SimpleStringProperty(source.format(ALLOCATION_TABLE_FORMATTER));
    }

    /**
     * Добавляем год в название колонки
     *
     * @param column Колонка
     * @param year   Какой год добавить
     */
    public static void addYearToColumnTitle(TableColumn column, int year) {
        column.setText(column.getText() + " " + year);
    }

    /**
     * Создает колонку с определенным текстом
     *
     * @param text Текст
     * @return Новая колонка
     */
    public static TableColumn createTableColumn(String text) {
        TableColumn column = new TableColumn(text);
        column.setPrefWidth(120);
        return column;
    }

    /**
     * Получаем id записи из строки в комбобоксе
     * id попадает в строку, благодаря {@link core.api.IVisible} методу
     *
     * @param source
     * @return
     */
    public static Long getIdFromComboBox(String source) {
        if (source == null || source.equalsIgnoreCase("null") || source.isEmpty()) return 0L;

        try {
            return Long.valueOf(source.split("[()]")[1]);
        } catch (NumberFormatException e) {
            logger.error("Ошибка разбора строки \"" + source + "\", для получения id записи", e);
            return 0L;
        }
    }


    /**
     * Локализуем название месяца
     *
     * @param month месяц
     * @return русское название
     */
    public static String localizeNameOfMonth(Month month) {
        return month.getDisplayName(TextStyle.FULL, new Locale(LOCALE));
    }
}
