/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package model.entity;

import core.enums.Stage;
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
        @Index(name = "allocation_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME),
        @Index(name = "allocation_issue_year_idx", unique = true, columnList = "issue_year")
})
@SequenceGenerator(name = "allocation_id_seq", sequenceName = "allocation_id_seq", allocationSize = 1)
public class AllocationEntity extends APersistentEntity {

    private Long id;
    private Long parentId;

    private boolean army;
    private boolean freeAllocation;
    private boolean voluntaryCompensation;
    private boolean archive;

    private Stage stage;
    private String cortOrderNumber;
    private String voluntaryCompensationOrderNumber;
    private Date voluntaryCompensationOrderDate;
    private Date voluntaryCompensationConfirmationDate;
    private Date cortOrderDate;
    private String[] confirmations;
    private String freeAllocationReason;
    private Integer issueYear;

    private GroupsEntity group;
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

    @Column(name = "parent_id", nullable = false)
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

    @Column(name = "voluntary_compensation")
    public boolean isVoluntaryCompensation() {
        return voluntaryCompensation;
    }

    public void setVoluntaryCompensation(boolean voluntaryCompensation) {
        this.voluntaryCompensation = voluntaryCompensation;
    }

    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Column(name = "stage", nullable = false)
    @Enumerated(EnumType.STRING)
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Column(name = "cort_order_number", length = 50)
    public String getCortOrderNumber() {
        return cortOrderNumber;
    }

    public void setCortOrderNumber(String cortOrderNumber) {
        this.cortOrderNumber = cortOrderNumber;
    }

    @Column(name = "voluntary_compensation_order_number", length = 50)
    public String getVoluntaryCompensationOrderNumber() {
        return voluntaryCompensationOrderNumber;
    }

    public void setVoluntaryCompensationOrderNumber(String voluntaryCompensationOrderNumber) {
        this.voluntaryCompensationOrderNumber = voluntaryCompensationOrderNumber;
    }

    @Column(name = "voluntary_compensation_order_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getVoluntaryCompensationOrderDate() {
        return voluntaryCompensationOrderDate;
    }

    public void setVoluntaryCompensationOrderDate(Date voluntaryCompensationOrderDate) {
        this.voluntaryCompensationOrderDate = voluntaryCompensationOrderDate;
    }

    @Column(name = "voluntary_compensation_confirmation_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getVoluntaryCompensationConfirmationDate() {
        return voluntaryCompensationConfirmationDate;
    }

    public void setVoluntaryCompensationConfirmationDate(Date voluntaryCompensationConfirmationDate) {
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
    }

    @Column(name = "cort_order_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getCortOrderDate() {
        return cortOrderDate;
    }

    public void setCortOrderDate(Date cortOrderDate) {
        this.cortOrderDate = cortOrderDate;
    }

    @Type(type = "stringArray")
    @Column(name = "confirmations")
    public String[] getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String[] confirmations) {
        this.confirmations = confirmations;
    }

    @ManyToOne
    public GroupsEntity getGroup() {
        return group;
    }

    public void setGroup(GroupsEntity group) {
        this.group = group;
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

    @ManyToOne
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

    @Column(name = "issue_year", nullable = false)
    public Integer getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(Integer issueYear) {
        this.issueYear = issueYear;
    }
}
