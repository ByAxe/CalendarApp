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
public interface IStudentsDTO extends IEssence, IVisible {

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

    String getCredentials();

    String toPrettyString();
}
