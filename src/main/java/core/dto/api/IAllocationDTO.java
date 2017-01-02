/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto.api;

import core.api.IEssence;
import core.enums.CompensationType;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IAllocationDTO extends IEssence {

    Long getParentId();

    void setParentId(Long parentId);

    boolean isArmy();

    void setArmy(boolean army);

    boolean isFreeAllocation();

    void setFreeAllocation(boolean freeAllocation);

    boolean isArchive();

    void setArchive(boolean archive);

    String getCompensationOrderNumber();

    void setCompensationOrderNumber(String compensationOrderNumber);

    LocalDateTime getCompensationOrderDate();

    void setCompensationOrderDate(LocalDateTime compensationOrderDate);

    LocalDateTime getVoluntaryCompensationConfirmationDate();

    void setVoluntaryCompensationConfirmationDate(LocalDateTime voluntaryCompensationConfirmationDate);

    String[] getConfirmations();

    void setConfirmations(String[] confirmations);

    IOrganisationsDTO getOrganisation();

    void setOrganisation(IOrganisationsDTO organisation);

    IStudentsDTO getStudent();

    void setStudent(IStudentsDTO student);

    IOrdersDTO getOrder();

    void setOrder(IOrdersDTO order);

    String getFreeAllocationReason();

    void setFreeAllocationReason(String freeAllocationReason);

    CompensationType getCompensationType();

    void setCompensationType(CompensationType compensationType);
}
