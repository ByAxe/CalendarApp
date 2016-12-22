/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
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
@Table(name = "organisations", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "organisations_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "organisations_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "organisations_id_seq", sequenceName = "organisations_id_seq", allocationSize = 1)
public class OrganisationsEntity extends APersistentEntity {

    private Long id;
    private String title;
    private String address;
    private String telephone;
    private String contacts;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "organisations_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "address", nullable = false, length = 150)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telephone", nullable = false, length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "contacts", columnDefinition = "TEXT", length = 1000)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
