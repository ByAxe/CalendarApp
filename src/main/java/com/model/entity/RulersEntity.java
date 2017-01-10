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
 * Created by byaxe on 17.12.16.
 */
@Entity
@Table(name = "rulers", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "rulers_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "rulers_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "rulers_id_seq", sequenceName = "rulers_id_seq", allocationSize = 1)
public class RulersEntity extends APersistentEntity {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String payload;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "rulers_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false, length = 100)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name", nullable = false, length = 100)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "last_name", nullable = false, length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "payload", length = 1000, columnDefinition = "TEXT")
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
