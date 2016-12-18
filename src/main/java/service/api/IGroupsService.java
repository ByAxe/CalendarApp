package service.api;

import core.dto.api.IGroupsDTO;
import model.entity.GroupsEntity;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IGroupsService extends IEssenceService<IGroupsDTO, Long>, IConversionService<IGroupsDTO, GroupsEntity> {
}
