package core.dto.api;

import core.api.IEssence;

import java.util.Date;
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

    String getStage();

    void setStage(String stage);

    String getCortOrderNumber();

    void setCortOrderNumber(String cortOrderNumber);

    String getVoluntaryCompensationOrderNumber();

    void setVoluntaryCompensationOrderNumber(String voluntaryCompensationOrderNumber);

    Date getVoluntaryCompensationOrderDate();

    void setVoluntaryCompensationOrderDate(Date voluntaryCompensationOrderDate);

    Date getVoluntaryCompensationConfirmationDate();

    void setVoluntaryCompensationConfirmationDate(Date voluntaryCompensationConfirmationDate);

    Date getCortOrderDate();

    void setCortOrderDate(Date cortOrderDate);

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
