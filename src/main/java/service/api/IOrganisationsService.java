/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service.api;

import core.dto.api.IOrganisationsDTO;
import model.entity.OrganisationsEntity;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrganisationsService extends IEssenceService<IOrganisationsDTO, Long>, IConversionService<IOrganisationsDTO, OrganisationsEntity> {
}
