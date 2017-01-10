/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

/**
 * Created by byaxe on 24.12.16.
 */
public interface INotificationLogTableDTO {

    String getId();

    void setId(String id);

    String getDate();

    void setDate(String date);

    String getType();

    void setType(String type);

    String getDescription();

    void setDescription(String description);
}
