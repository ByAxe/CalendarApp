package service.api;

import core.dto.api.IAllocationDTO;
import model.entity.AllocationEntity;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IAllocationService extends IEssenceService<IAllocationDTO, Long>,
        IConversionService<IAllocationDTO, AllocationEntity> {
}
