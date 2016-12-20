package service.quartz.jobs;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import org.quartz.*;

import static core.commons.Utils.raiseMessageBox;

/**
 * Created by byaxe on 19.12.16.
 * <p>
 * Выдает сообщение пользователю о предстоящем событии (в ежедневнике)
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CalendarNotificationJob implements Job {

    private String notificationType;
    private String notificationTitle;
    private String notificationHeader;
    private String notificationBody;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Platform.runLater(() -> raiseMessageBox(AlertType.valueOf(notificationType), notificationTitle, notificationHeader, notificationBody));
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationHeader() {
        return notificationHeader;
    }

    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }
}
