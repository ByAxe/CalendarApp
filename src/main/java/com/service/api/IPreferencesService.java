/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.api;

import com.core.dto.api.IPreferencesDTO;
import com.model.entity.PreferencesEntity;

/**
 * Created by byaxe on 20.12.16.
 */
public interface IPreferencesService extends IEssenceService<IPreferencesDTO, Long>,
        IConversionService<IPreferencesDTO, PreferencesEntity> {

    IPreferencesDTO get();
}
