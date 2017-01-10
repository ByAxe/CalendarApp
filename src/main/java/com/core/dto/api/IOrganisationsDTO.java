/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

import com.core.api.IEssence;
import com.core.api.IVisible;

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
