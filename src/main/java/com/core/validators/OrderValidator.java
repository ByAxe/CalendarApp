/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.validators;

import com.core.commons.Result;
import com.core.dto.api.IOrdersDTO;
import com.core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 26.12.16.
 */
@Component
public class OrderValidator implements IValidator<Result, IOrdersDTO> {
    @Override
    public Result validate(IOrdersDTO order) {
        List<String> errors = new ArrayList<>();

        if (order.getNumber().isEmpty()) {
            errors.add("Номер приказа о приеме на работу отсутствует.");
        }

        if (order.getProfession().isEmpty()) {
            errors.add("Не указан профессия по которой выпускник идет работать в организацию.");
        }

//        if (order.getRank() == null) errors.add("Не указано, по какому разряду будет работать выпускник.");

        return wrapResult(errors);
    }
}
