/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.api;

import java.util.Date;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEssence {
    String ID_COLUMN_NAME = "id";
    String UUID_COLUMN_NAME = "uuid";
    String DATE_UPDATE_COLUMN_NAME = "dt_update";

    Long getId();

    void setId(Long id);

    UUID getUuid();

    void setUuid(UUID uuid);

    Date getDtUpdate();

    void setDtUpdate(Date dtUpdate);
}
