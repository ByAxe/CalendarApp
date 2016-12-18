package core.dto.api;

import core.api.IEssence;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrganisationsDTO extends IEssence {

    String getTitle();

    void setTitle(String title);

    String getAddress();

    void setAddress(String address);

    String getTelephone();

    void setTelephone(String telephone);

    String getContacts();

    void setContacts(String contacts);
}
