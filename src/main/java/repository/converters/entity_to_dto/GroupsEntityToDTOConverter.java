package repository.converters.entity_to_dto;

import core.dto.GroupsDTOImpl;
import core.dto.api.IGroupsDTO;
import model.entity.GroupsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class GroupsEntityToDTOConverter implements Converter<GroupsEntity, IGroupsDTO> {

    @Override
    public IGroupsDTO convert(GroupsEntity source) {
        if (source == null) return null;

        IGroupsDTO target = new GroupsDTOImpl();

        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());
        target.setTitle(source.getTitle());
        target.setHours(source.getHours());
        target.setSpecialization(source.getSpecialization());
        target.setNumber(source.getNumber());
        target.setDescription(source.getDescription());

        return target;
    }
}
