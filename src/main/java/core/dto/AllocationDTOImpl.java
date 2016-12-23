/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.*;
import core.enums.Stage;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
public class AllocationDTOImpl extends AEssence implements IAllocationDTO {
    private UUID parentUuid;

    private boolean army;
    private boolean freeAllocation;
    private boolean voluntaryCompensation;
    private boolean archive;

    private Stage stage;
    private String cortOrderNumber;
    private String voluntaryCompensationOrderNumber;
    private LocalDateTime voluntaryCompensationOrderDate;
    private LocalDateTime voluntaryCompensationConfirmationDate;
    private LocalDateTime cortOrderDate;
    private String[] confirmations;

    private IGroupsDTO group;
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

        this.parentUuid = essence.getParentUuid();

        this.army = essence.isArmy();
        this.freeAllocation = essence.isFreeAllocation();
        this.voluntaryCompensation = essence.isVoluntaryCompensation();
        this.archive = essence.isArchive();

        this.stage = essence.getStage();
        this.cortOrderNumber = essence.getCortOrderNumber();
        this.voluntaryCompensationOrderNumber = essence.getVoluntaryCompensationOrderNumber();
        this.voluntaryCompensationOrderDate = essence.getVoluntaryCompensationOrderDate();
        this.voluntaryCompensationConfirmationDate = essence.getVoluntaryCompensationConfirmationDate();
        this.cortOrderDate = essence.getCortOrderDate();
        this.confirmations = essence.getConfirmations();

        this.group = essence.getGroup();
        this.organisation = essence.getOrganisation();
        this.student = essence.getStudent();
        this.order = essence.getOrder();
    }

    public AllocationDTOImpl(Long id, UUID parentUuid, boolean army, boolean freeAllocation, boolean voluntaryCompensation,
                             boolean archive, Stage stage, String cortOrderNumber, String voluntaryCompensationOrderNumber,
                             LocalDateTime voluntaryCompensationOrderDate, LocalDateTime voluntaryCompensationConfirmationDate,
                             LocalDateTime cortOrderDate, String[] confirmations, IGroupsDTO group, IOrganisationsDTO organisation,
                             IStudentsDTO student, IOrdersDTO order) {
        this.id = id;
        this.parentUuid = parentUuid;
        this.army = army;
        this.freeAllocation = freeAllocation;
        this.voluntaryCompensation = voluntaryCompensation;
        this.archive = archive;
        this.stage = stage;
        this.cortOrderNumber = cortOrderNumber;
        this.voluntaryCompensationOrderNumber = voluntaryCompensationOrderNumber;
        this.voluntaryCompensationOrderDate = voluntaryCompensationOrderDate;
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
        this.cortOrderDate = cortOrderDate;
        this.confirmations = confirmations;
        this.group = group;
        this.organisation = organisation;
        this.student = student;
        this.order = order;
    }

    public UUID getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(UUID parentUuid) {
        this.parentUuid = parentUuid;
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

    public boolean isVoluntaryCompensation() {
        return voluntaryCompensation;
    }

    public void setVoluntaryCompensation(boolean voluntaryCompensation) {
        this.voluntaryCompensation = voluntaryCompensation;
    }

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getCortOrderNumber() {
        return cortOrderNumber;
    }

    public void setCortOrderNumber(String cortOrderNumber) {
        this.cortOrderNumber = cortOrderNumber;
    }

    public String getVoluntaryCompensationOrderNumber() {
        return voluntaryCompensationOrderNumber;
    }

    public void setVoluntaryCompensationOrderNumber(String voluntaryCompensationOrderNumber) {
        this.voluntaryCompensationOrderNumber = voluntaryCompensationOrderNumber;
    }

    public LocalDateTime getVoluntaryCompensationOrderDate() {
        return voluntaryCompensationOrderDate;
    }

    public void setVoluntaryCompensationOrderDate(LocalDateTime voluntaryCompensationOrderDate) {
        this.voluntaryCompensationOrderDate = voluntaryCompensationOrderDate;
    }

    public LocalDateTime getVoluntaryCompensationConfirmationDate() {
        return voluntaryCompensationConfirmationDate;
    }

    public void setVoluntaryCompensationConfirmationDate(LocalDateTime voluntaryCompensationConfirmationDate) {
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
    }

    public LocalDateTime getCortOrderDate() {
        return cortOrderDate;
    }

    public void setCortOrderDate(LocalDateTime cortOrderDate) {
        this.cortOrderDate = cortOrderDate;
    }

    public String[] getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String[] confirmations) {
        this.confirmations = confirmations;
    }

    public IGroupsDTO getGroup() {
        return group;
    }

    public void setGroup(IGroupsDTO group) {
        this.group = group;
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
}
