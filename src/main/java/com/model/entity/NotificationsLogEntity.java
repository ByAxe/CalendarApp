/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.model.entity;

import com.core.enums.NotificationType;
import com.model.api.APersistentEntity;

import javax.persistence.*;
import java.util.Date;

import static com.core.api.IEssence.ID_COLUMN_NAME;
import static com.core.api.IEssence.UUID_COLUMN_NAME;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by byaxe on 19.12.16.
 */
@Entity
@Table(name = "notifications_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "notifications_log_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "notifications_log_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "notifications_log_id_seq", sequenceName = "notifications_log_id_seq", allocationSize = 1)
public class NotificationsLogEntity extends APersistentEntity {

    private Long id;
    private boolean viewed;
    private Date notificationDate;
    private NotificationType notificationType;
    private String payload;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "notifications_log_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "viewed")
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Column(name = "notification_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Enumerated(value = STRING)
    @Column(name = "notification_type", nullable = false)
    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
