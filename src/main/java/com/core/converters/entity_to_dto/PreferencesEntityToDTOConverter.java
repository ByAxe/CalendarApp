/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.PreferencesDTOImpl;
import com.core.dto.api.IPreferencesDTO;
import com.model.entity.PreferencesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 20.12.16.
 */
@Component
public class PreferencesEntityToDTOConverter implements Converter<PreferencesEntity, IPreferencesDTO> {

    @Override
    public IPreferencesDTO convert(PreferencesEntity source) {
        if (source == null) return null;

        IPreferencesDTO target = new PreferencesDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setArchiveTerm(source.getArchiveTerm());
        target.setConfirmationNoticeTerm(source.getConfirmationNoticeTerm());
        target.setNotificationsEnabled(source.isNotificationsEnabled());
        target.setArchiveEnabled(source.isArchiveEnabled());
        target.setAllocationEndNoticeTerm(source.getAllocationEndNoticeTerm());

        return target;
    }
}
