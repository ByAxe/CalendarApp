/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package model.entity;

import model.api.APersistentEntity;

import javax.persistence.*;
import java.util.Date;

import static core.api.IEssence.ID_COLUMN_NAME;
import static core.api.IEssence.UUID_COLUMN_NAME;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by byaxe on 18.12.16.
 */
@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "orders_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "orders_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
public class OrdersEntity extends APersistentEntity {

    private Long id;
    private String profession;
    private Integer rank;
    private Date starts;
    private String number;
    private String payload;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "orders_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "profession", nullable = false, length = 100)
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Column(name = "rank", nullable = false)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Column(name = "starts", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    @Column(name = "number", nullable = false, length = 50)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "payload", columnDefinition = "TEXT", length = 1000)
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
