/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.converters.dto_to_entity;

import core.dto.api.IStudentsDTO;
import model.entity.StudentsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class StudentsDTOToEntityConverter implements Converter<IStudentsDTO, StudentsEntity> {

    @Override
    public StudentsEntity convert(IStudentsDTO source) {
        if (source == null) return null;

        StudentsEntity target = new StudentsEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setFirstName(source.getFirstName());
        target.setMiddleName(source.getMiddleName());
        target.setLastName(source.getLastName());
        target.setAddress(source.getAddress());
        target.setTelephone(source.getTelephone());

        return target;
    }
}
