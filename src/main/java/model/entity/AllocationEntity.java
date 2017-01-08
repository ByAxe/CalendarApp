/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package model.entity;

import core.enums.CompensationType;
import model.api.APersistentEntity;
import model.api.types.StringArray;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

import static core.api.IEssence.ID_COLUMN_NAME;
import static core.api.IEssence.UUID_COLUMN_NAME;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by byaxe on 18.12.16.
 */
@Entity
@TypeDef(name = "stringArray", typeClass = StringArray.class)
@Table(name = "allocation", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "allocation_pkey", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "allocation_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "allocation_id_seq", sequenceName = "allocation_id_seq", allocationSize = 1)
public class AllocationEntity extends APersistentEntity {

    private Long id;
    private Long parentId;

    private boolean army;
    private boolean freeAllocation;
    private boolean archive;

    private CompensationType compensationType;
    private String compensationOrderNumber;
    private Date compensationOrderDate;
    private Date voluntaryCompensationConfirmationDate;
    private String[] confirmations;
    private String freeAllocationReason;

    private OrganisationsEntity organisation;
    private StudentsEntity student;
    private OrdersEntity order;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "allocation_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "army")
    public boolean isArmy() {
        return army;
    }

    public void setArmy(boolean army) {
        this.army = army;
    }

    @Column(name = "free_allocation")
    public boolean isFreeAllocation() {
        return freeAllocation;
    }

    public void setFreeAllocation(boolean freeAllocation) {
        this.freeAllocation = freeAllocation;
    }

    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Column(name = "compensation_order_number", length = 50)
    public String getCompensationOrderNumber() {
        return compensationOrderNumber;
    }

    public void setCompensationOrderNumber(String compensationOrderNumber) {
        this.compensationOrderNumber = compensationOrderNumber;
    }

    @Column(name = "compensation_order_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getCompensationOrderDate() {
        return compensationOrderDate;
    }

    public void setCompensationOrderDate(Date compensationOrderDate) {
        this.compensationOrderDate = compensationOrderDate;
    }

    @Column(name = "voluntary_compensation_confirmation_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getVoluntaryCompensationConfirmationDate() {
        return voluntaryCompensationConfirmationDate;
    }

    public void setVoluntaryCompensationConfirmationDate(Date voluntaryCompensationConfirmationDate) {
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
    }

    @Type(type = "stringArray")
    @Column(name = "confirmations")
    public String[] getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String[] confirmations) {
        this.confirmations = confirmations;
    }

    @Column(name = "compensation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public CompensationType getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(CompensationType compensationType) {
        this.compensationType = compensationType;
    }

    @ManyToOne
    public OrganisationsEntity getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationsEntity organisation) {
        this.organisation = organisation;
    }

    @ManyToOne
    public StudentsEntity getStudent() {
        return student;
    }

    public void setStudent(StudentsEntity student) {
        this.student = student;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    @Column(name = "free_allocation_reason", columnDefinition = "TEXT")
    public String getFreeAllocationReason() {
        return freeAllocationReason;
    }

    public void setFreeAllocationReason(String freeAllocationReason) {
        this.freeAllocationReason = freeAllocationReason;
    }
}
