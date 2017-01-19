/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.OrganisationsDTOImpl;
import com.core.dto.api.IOrganisationsDTO;
import com.model.entity.OrganisationsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class OrganisationsEntityToDTOConverter implements Converter<OrganisationsEntity, IOrganisationsDTO> {

    @Override
    public IOrganisationsDTO convert(OrganisationsEntity source) {
        if (source == null) return null;

        IOrganisationsDTO target = new OrganisationsDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setTitle(source.getTitle());
        target.setAddress(source.getAddress());
        target.setTelephone(source.getTelephone());
        target.setContacts(source.getContacts());

        return target;
    }
}
