package core.converters.dto_to_entity;

import core.dto.api.IGroupsDTO;
import model.entity.GroupsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class GroupsDTOToEntityConverter implements Converter<IGroupsDTO, GroupsEntity> {

    private final RulersDTOToEntityConverter rulersDTOToEntityConverter;

    @Autowired
    public GroupsDTOToEntityConverter(RulersDTOToEntityConverter rulersDTOToEntityConverter) {
        this.rulersDTOToEntityConverter = rulersDTOToEntityConverter;
    }

    @Override
    public GroupsEntity convert(IGroupsDTO source) {
        if (source == null) return null;

        GroupsEntity target = new GroupsEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setTitle(source.getTitle());
        target.setHours(source.getHours());
        target.setDescription(source.getDescription());
        target.setNumber(source.getNumber());
        target.setSpecialization(source.getSpecialization());
        target.setQualification(source.getQualification());
        target.setRuler(rulersDTOToEntityConverter.convert(source.getRuler()));

        return target;
    }
}
