package core.dto.api;

import core.api.IEssence;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IStudentsDTO extends IEssence {

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    String getLastName();

    void setLastName(String lastName);

    String getTelephone();

    void setTelephone(String telephone);

    String getAddress();

    void setAddress(String address);
}
