package core.converters.dto_to_entity;

import core.dto.api.IRulersDTO;
import model.entity.GroupsEntity;
import model.entity.RulersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by byaxe on 17.12.16.
 */
@Component
public class RulersDTOToEntityConverter implements Converter<IRulersDTO, RulersEntity> {

    @Override
    public RulersEntity convert(IRulersDTO source) {
        if (source == null) return null;

        GroupsDTOToEntityConverter groupsDTOToEntityConverter = new GroupsDTOToEntityConverter(this);

        RulersEntity target = new RulersEntity();

        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());
        target.setFirstName(source.getFirstName());
        target.setMiddleName(source.getMiddleName());
        target.setLastName(source.getLastName());
        target.setPayload(source.getPayload());

        Set<GroupsEntity> groupsEntities = source.getGroups()
                .stream()
                .map(groupsDTOToEntityConverter::convert)
                .collect(toSet());

        target.setGroups(groupsEntities);

        return target;
    }
}
