/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto.api;

import core.api.IEssence;
import core.api.IVisible;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrganisationsDTO extends IEssence, IVisible {

    String getTitle();

    void setTitle(String title);

    String getAddress();

    void setAddress(String address);

    String getTelephone();

    void setTelephone(String telephone);

    String getContacts();

    void setContacts(String contacts);
}
