/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.converters.entity_to_dto;

import core.dto.RulersDTOImpl;
import core.dto.api.IRulersDTO;
import model.entity.RulersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 17.12.16.
 */
@Component
public class RulersEntityToDTOConverter implements Converter<RulersEntity, IRulersDTO> {

    @Override
    public IRulersDTO convert(RulersEntity source) {
        if (source == null) return null;

        IRulersDTO target = new RulersDTOImpl();

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
