/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.dto_to_entity;

import com.core.dto.api.IRulersDTO;
import com.model.entity.RulersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 17.12.16.
 */
@Component
public class RulersDTOToEntityConverter implements Converter<IRulersDTO, RulersEntity> {

    @Override
    public RulersEntity convert(IRulersDTO source) {
        if (source == null) return null;

        RulersEntity target = new RulersEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setFirstName(source.getFirstName());
        target.setMiddleName(source.getMiddleName());
        target.setLastName(source.getLastName());
        target.setPayload(source.getPayload());

        return target;
    }
}
