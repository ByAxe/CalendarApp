/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.validators;

import com.core.commons.Result;
import com.core.dto.api.IOrganisationsDTO;
import com.core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class OrganisationValidator implements IValidator<Result, IOrganisationsDTO> {

    @Override
    public Result validate(IOrganisationsDTO organisation) {
        List<String> errors = new ArrayList<>();

        if (organisation.getTitle().isEmpty()) errors.add("Название организации не может быть пустым.");
        if (organisation.getAddress().isEmpty()) errors.add("Организация должна иметь адрес.");
        if (organisation.getTelephone().isEmpty()) errors.add("Контактный телефон является обязательным полем.");

        return wrapResult(errors);
    }
}
