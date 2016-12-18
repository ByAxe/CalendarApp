package core.dto.api;

import core.api.IEssence;

import java.util.Set;

/**
 * Created by byaxe on 17.12.16.
 */
public interface IRulersDTO extends IEssence {

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    String getLastName();

    void setLastName(String lastName);

    String getPayload();

    void setPayload(String payload);

    Set<IGroupsDTO> getGroups();

    void setGroups(Set<IGroupsDTO> groups);
}
