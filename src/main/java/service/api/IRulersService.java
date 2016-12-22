/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service.api;

import core.dto.api.IRulersDTO;
import model.entity.RulersEntity;

/**
 * Created by byaxe on 17.12.16.
 */
public interface IRulersService extends IEssenceService<IRulersDTO, Long>, IConversionService<IRulersDTO, RulersEntity> {

    RulersEntity getActualEntity(Long id);
}
