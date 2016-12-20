package core.dto.api;

import core.api.IEssence;
import core.enums.NotificationType;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 19.12.16.
 */
public interface INotificationsLogDTO extends IEssence {

    boolean isViewed();

    void setViewed(boolean viewed);

    LocalDateTime getNotificationDate();

    void setNotificationDate(LocalDateTime notificationDate);

    NotificationType getNotificationType();

    void setNotificationType(NotificationType notificationType);

    String getPayload();

    void setPayload(String payload);

}
