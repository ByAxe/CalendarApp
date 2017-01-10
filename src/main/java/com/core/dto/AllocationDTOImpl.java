/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.api.IEssence;
import com.core.dto.api.IAllocationDTO;
import com.core.dto.api.IOrdersDTO;
import com.core.dto.api.IOrganisationsDTO;
import com.core.dto.api.IStudentsDTO;
import com.core.enums.CompensationType;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
public class AllocationDTOImpl extends AEssence implements IAllocationDTO {
    private Long parentId;

    private boolean army;
    private boolean freeAllocation;
    private boolean archive;

    private CompensationType compensationType;
    private String compensationOrderNumber;
    private LocalDateTime compensationOrderDate;
    private LocalDateTime voluntaryCompensationConfirmationDate;
    private String[] confirmations;
    private String freeAllocationReason;

    private IOrganisationsDTO organisation;
    private IStudentsDTO student;
    private IOrdersDTO order;

    public AllocationDTOImpl() {
    }

    public AllocationDTOImpl(IEssence essence) {
        super(essence);
    }

    public AllocationDTOImpl(IAllocationDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();

        this.parentId = essence.getParentId();

        this.army = essence.isArmy();
        this.freeAllocation = essence.isFreeAllocation();
        this.archive = essence.isArchive();

        this.compensationOrderNumber = essence.getCompensationOrderNumber();
        this.compensationOrderDate = essence.getCompensationOrderDate();
        this.voluntaryCompensationConfirmationDate = essence.getVoluntaryCompensationConfirmationDate();
        this.confirmations = essence.getConfirmations();

        this.organisation = essence.getOrganisation();
        this.student = essence.getStudent();
        this.order = essence.getOrder();
    }

    public AllocationDTOImpl(Long id, Long parentUuid, boolean army, boolean freeAllocation,
                             boolean archive, String compensationOrderNumber,
                             LocalDateTime compensationOrderDate, LocalDateTime voluntaryCompensationConfirmationDate,
                             String[] confirmations, IOrganisationsDTO organisation,
                             IStudentsDTO student, IOrdersDTO order) {
        this.id = id;
        this.parentId = parentUuid;
        this.army = army;
        this.freeAllocation = freeAllocation;
        this.archive = archive;
        this.compensationOrderNumber = compensationOrderNumber;
        this.compensationOrderDate = compensationOrderDate;
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
        this.confirmations = confirmations;
        this.organisation = organisation;
        this.student = student;
        this.order = order;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isArmy() {
        return army;
    }

    public void setArmy(boolean army) {
        this.army = army;
    }

    public boolean isFreeAllocation() {
        return freeAllocation;
    }

    public void setFreeAllocation(boolean freeAllocation) {
        this.freeAllocation = freeAllocation;
    }

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getCompensationOrderNumber() {
        return compensationOrderNumber;
    }

    public void setCompensationOrderNumber(String compensationOrderNumber) {
        this.compensationOrderNumber = compensationOrderNumber;
    }

    public LocalDateTime getCompensationOrderDate() {
        return compensationOrderDate;
    }

    public void setCompensationOrderDate(LocalDateTime compensationOrderDate) {
        this.compensationOrderDate = compensationOrderDate;
    }

    public LocalDateTime getVoluntaryCompensationConfirmationDate() {
        return voluntaryCompensationConfirmationDate;
    }

    public void setVoluntaryCompensationConfirmationDate(LocalDateTime voluntaryCompensationConfirmationDate) {
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
    }

    public String[] getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String[] confirmations) {
        this.confirmations = confirmations;
    }

    public IOrganisationsDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(IOrganisationsDTO organisation) {
        this.organisation = organisation;
    }

    public IStudentsDTO getStudent() {
        return student;
    }

    public void setStudent(IStudentsDTO student) {
        this.student = student;
    }

    public IOrdersDTO getOrder() {
        return order;
    }

    public void setOrder(IOrdersDTO order) {
        this.order = order;
    }

    public String getFreeAllocationReason() {
        return freeAllocationReason;
    }

    public void setFreeAllocationReason(String freeAllocationReason) {
        this.freeAllocationReason = freeAllocationReason;
    }

    public CompensationType getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(CompensationType compensationType) {
        this.compensationType = compensationType;
    }
}
