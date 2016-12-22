/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service.api;

import core.dto.api.IAllocationDTO;
import model.entity.AllocationEntity;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IAllocationService extends IEssenceService<IAllocationDTO, Long>,
        IConversionService<IAllocationDTO, AllocationEntity> {

    List<IAllocationDTO> findByArchiveTrue();

    List<IAllocationDTO> findByArchiveFalse();

    void moveToArchive(Long id);
}
