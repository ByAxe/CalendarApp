package core.converters.entity_to_dto;

import core.dto.RulersDTOImpl;
import core.dto.api.IGroupsDTO;
import core.dto.api.IRulersDTO;
import model.entity.RulersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by byaxe on 17.12.16.
 */
@Component
public class RulersEntityToDTOConverter implements Converter<RulersEntity, IRulersDTO> {

    @Override
    public IRulersDTO convert(RulersEntity source) {
        if (source == null) return null;

        GroupsEntityToDTOConverter groupsEntityToDTOConverter = new GroupsEntityToDTOConverter(this);

        IRulersDTO target = new RulersDTOImpl();

        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());
        target.setFirstName(source.getFirstName());
        target.setMiddleName(source.getMiddleName());
        target.setLastName(source.getLastName());
        target.setPayload(source.getPayload());

        Set<IGroupsDTO> groups = source.getGroups()
                .stream()
                .map(groupsEntityToDTOConverter::convert)
                .collect(toSet());

        target.setGroups(groups);

        return target;
    }
}
