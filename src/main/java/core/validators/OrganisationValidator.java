/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IOrganisationsDTO;
import core.validators.api.IValidator;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class OrganisationValidator implements IValidator<Result, IOrganisationsDTO> {

    @Override
    public Result validate(IOrganisationsDTO iOrganisationsDTO) {
        return null;
    }
}
