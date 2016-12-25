/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IGroupsDTO;
import core.validators.api.IValidator;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class GroupValidator implements IValidator<Result, IGroupsDTO> {

    @Override
    public Result validate(IGroupsDTO iGroupsDTO) {
        return null;
    }
}
