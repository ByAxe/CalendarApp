/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.dto.api;

import core.api.IEssence;
import core.enums.Stage;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IAllocationDTO extends IEssence {

    UUID getParentUuid();

    void setParentUuid(UUID parentUuid);

    boolean isArmy();

    void setArmy(boolean army);

    boolean isFreeAllocation();

    void setFreeAllocation(boolean freeAllocation);

    boolean isVoluntaryCompensation();

    void setVoluntaryCompensation(boolean voluntaryCompensation);

    boolean isArchive();

    void setArchive(boolean archive);

    Stage getStage();

    void setStage(Stage stage);

    String getCortOrderNumber();

    void setCortOrderNumber(String cortOrderNumber);

    String getVoluntaryCompensationOrderNumber();

    void setVoluntaryCompensationOrderNumber(String voluntaryCompensationOrderNumber);

    LocalDateTime getVoluntaryCompensationOrderDate();

    void setVoluntaryCompensationOrderDate(LocalDateTime voluntaryCompensationOrderDate);

    LocalDateTime getVoluntaryCompensationConfirmationDate();

    void setVoluntaryCompensationConfirmationDate(LocalDateTime voluntaryCompensationConfirmationDate);

    LocalDateTime getCortOrderDate();

    void setCortOrderDate(LocalDateTime cortOrderDate);

    String[] getConfirmations();

    void setConfirmations(String[] confirmations);

    IGroupsDTO getGroup();

    void setGroup(IGroupsDTO group);

    IOrganisationsDTO getOrganisation();

    void setOrganisation(IOrganisationsDTO organisation);

    IStudentsDTO getStudent();

    void setStudent(IStudentsDTO student);

    IOrdersDTO getOrder();

    void setOrder(IOrdersDTO order);
}
