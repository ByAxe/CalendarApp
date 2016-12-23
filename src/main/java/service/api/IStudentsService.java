/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import core.dto.api.IStudentsDTO;
import model.entity.StudentsEntity;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IStudentsService extends IEssenceService<IStudentsDTO, Long>, IConversionService<IStudentsDTO, StudentsEntity> {
}
