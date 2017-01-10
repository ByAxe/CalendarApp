/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

import com.core.api.IEssence;
import com.core.enums.NotificationType;

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
