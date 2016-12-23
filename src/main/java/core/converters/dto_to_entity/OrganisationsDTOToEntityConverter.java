/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.converters.dto_to_entity;

import core.dto.api.IOrganisationsDTO;
import model.entity.OrganisationsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class OrganisationsDTOToEntityConverter implements Converter<IOrganisationsDTO, OrganisationsEntity> {

    @Override
    public OrganisationsEntity convert(IOrganisationsDTO source) {
        if (source == null) return null;

        OrganisationsEntity target = new OrganisationsEntity();

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
