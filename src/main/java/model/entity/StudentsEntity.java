/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package model.entity;

import model.api.APersistentEntity;

import javax.persistence.*;

import static core.api.IEssence.ID_COLUMN_NAME;
import static core.api.IEssence.UUID_COLUMN_NAME;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by byaxe on 18.12.16.
 */
@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "students_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "students_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "students_id_seq", sequenceName = "students_id_seq", allocationSize = 1)
public class StudentsEntity extends APersistentEntity {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String telephone;
    private String address;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "students_id_seq")
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

    @Column(name = "telephone", length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "address", nullable = false, length = 150)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
