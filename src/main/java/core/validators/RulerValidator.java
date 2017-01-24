/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IRulersDTO;
import core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 26.12.16.
 */
@Component
public class RulerValidator implements IValidator<Result, IRulersDTO> {

    @Override
    public Result validate(IRulersDTO ruler) {
        List<String> errors = new ArrayList<>();

        if (ruler.getFirstName().isEmpty()) {
            errors.add("Имя должно быть заполнено.");
        }

        if (ruler.getMiddleName().isEmpty()) {
            errors.add("Отчество должно быть заполнено.");
        }

        if (ruler.getLastName().isEmpty()) {
            errors.add("Фамилия должна быть заполнена.");
        }

        return wrapResult(errors);
    }
}
