/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.api.IEssence;
import com.core.dto.api.INotificationsLogDTO;
import com.core.enums.NotificationType;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 19.12.16.
 */
public class NotificationsLogDTOImpl extends AEssence implements INotificationsLogDTO {

    private boolean viewed;
    private LocalDateTime notificationDate;
    private NotificationType notificationType;
    private String payload;

    public NotificationsLogDTOImpl() {
    }

    public NotificationsLogDTOImpl(IEssence essence) {
        super(essence);
    }

    public NotificationsLogDTOImpl(INotificationsLogDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.viewed = essence.isViewed();
        this.notificationDate = essence.getNotificationDate();
        this.notificationType = essence.getNotificationType();
        this.payload = essence.getPayload();
    }

    public NotificationsLogDTOImpl(Long id, boolean viewed, LocalDateTime notificationDate,
                                   NotificationType notificationType, String payload) {
        this.viewed = viewed;
        this.notificationDate = notificationDate;
        this.notificationType = notificationType;
        this.payload = payload;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
