package core.dto.api;

import core.api.IEssence;

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
}
