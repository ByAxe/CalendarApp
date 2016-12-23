/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import core.dto.api.IOrganisationsDTO;
import model.entity.OrganisationsEntity;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrganisationsService extends IEssenceService<IOrganisationsDTO, Long>, IConversionService<IOrganisationsDTO, OrganisationsEntity> {
}
