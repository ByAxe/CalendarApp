package core.commons;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static core.enums.ResultEnum.ERROR;

/**
 * Created by byaxe on 26.11.16.
 */
public class Utils {

    public static final String TODAY = "СЕГОДНЯ";
    public static final String TOMORROW = "ЗАВТРА";

    public static final DateTimeFormatter PRETTY_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM HH:mm");

    /**
     * Создает сообщение с помощью {@link Alert}
     *
     * @param type   Тип сообщения
     * @param title  Надпись сверху
     * @param header Заголовок
     * @param body   Основной текст сообщения
     */
    public static void raiseMessageBox(AlertType type, String title, String header, String body) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

    /**
     * Конвертирует {@link Date} в {@link LocalDateTime} с применением текущей локали
     * <p>
     *
     * @param source Исходная дата в {@link Date}
     * @return Результативная дата в {@link LocalDateTime}
     */
    public static LocalDateTime covertDateToLocalDateTime(Date source) {
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
        return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Оборачивает {@link List<String>} ошибок в {@link Result}
     * И форматирует {@link List<String>} ошибок для подготовки к выводу для пользователя
     * <p>
     *
     * @param errors {@link List<String>} ошибок
     * @return {@link Result} специальная обертка
     */
    public static Result wrapErrorsList(List<String> errors) {
        return new Result(ERROR, errors);
    }

}
