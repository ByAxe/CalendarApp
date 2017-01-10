/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.model.entity;

import com.model.api.APersistentEntity;

import javax.persistence.*;

import static com.core.api.IEssence.ID_COLUMN_NAME;
import static com.core.api.IEssence.UUID_COLUMN_NAME;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by byaxe on 19.12.16.
 */
@Entity
@Table(name = "preferences", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "preferences_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "preferences_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "preferences_id_seq", sequenceName = "preferences_id_seq", allocationSize = 1)
public class PreferencesEntity extends APersistentEntity {

    private Long id;
    private boolean notificationsEnabled;
    private boolean archiveEnabled;
    private Integer archiveTerm;
    private Integer confirmationNoticeTerm;
    private Integer allocationEndNoticeTerm;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "preferences_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "notifications_enabled")
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    @Column(name = "archive_enabled")
    public boolean isArchiveEnabled() {
        return archiveEnabled;
    }

    public void setArchiveEnabled(boolean archiveEnabled) {
        this.archiveEnabled = archiveEnabled;
    }

    @Column(name = "archive_term", nullable = false)
    public Integer getArchiveTerm() {
        return archiveTerm;
    }

    public void setArchiveTerm(Integer archiveTerm) {
        this.archiveTerm = archiveTerm;
    }

    @Column(name = "confirmation_notice_term", nullable = false)
    public Integer getConfirmationNoticeTerm() {
        return confirmationNoticeTerm;
    }

    public void setConfirmationNoticeTerm(Integer confirmationNoticeTerm) {
        this.confirmationNoticeTerm = confirmationNoticeTerm;
    }

    @Column(name = "allocation_end_notice_term", nullable = false)
    public Integer getAllocationEndNoticeTerm() {
        return allocationEndNoticeTerm;
    }

    public void setAllocationEndNoticeTerm(Integer allocationEndNoticeTerm) {
        this.allocationEndNoticeTerm = allocationEndNoticeTerm;
    }
}
