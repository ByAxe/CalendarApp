package core.commons;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.format.DateTimeFormatter;

/**
 * Created by byaxe on 26.11.16.
 */
public class Utils {

    public static final String TODAY = "СЕГОДНЯ";
    public static final String TOMORROW = "ЗАВТРА";

    public static final DateTimeFormatter PRETTY_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM HH:mm");

    /**
     * Создает сообщение
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

}
