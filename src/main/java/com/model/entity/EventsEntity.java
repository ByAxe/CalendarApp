/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.model.entity;

import com.core.enums.Frequency;
import com.core.enums.NoticePeriod;
import com.core.enums.Priority;
import com.model.api.APersistentEntity;

import javax.persistence.*;
import java.util.Date;

import static com.core.api.IEssence.ID_COLUMN_NAME;
import static com.core.api.IEssence.UUID_COLUMN_NAME;

/**
 * Created by byaxe on 26.11.16.
 */
@Entity
@Table(name = "events", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "events_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "events_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "events_id_seq", sequenceName = "events_id_seq", allocationSize = 1)
public class EventsEntity extends APersistentEntity {

    private Long id;
    private Date starts;
    private Date ends;
    private String title;
    private NoticePeriod noticePeriod;
    private Priority priority;
    private Frequency frequency;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_id_seq")
    @Basic(optional = false)
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "starts", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    @Column(name = "ends", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    @Column(name = "title", nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "notice_period", nullable = false)
    @Enumerated(EnumType.STRING)
    public NoticePeriod getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(NoticePeriod noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Column(name = "frequency", nullable = false)
    @Enumerated(EnumType.STRING)
    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
}
