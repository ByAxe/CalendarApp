/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

import com.core.api.IEssence;
import com.core.api.IVisible;

/**
 * Created by byaxe on 17.12.16.
 */
public interface IRulersDTO extends IEssence, IVisible {

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    String getLastName();

    void setLastName(String lastName);

    String getPayload();

    void setPayload(String payload);
}
