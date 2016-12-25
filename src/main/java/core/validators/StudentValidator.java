/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IStudentsDTO;
import core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class StudentValidator implements IValidator<Result, IStudentsDTO> {
    @Override
    public Result validate(IStudentsDTO student) {
        List<String> errors = new ArrayList<>();

        if (student.getFirstName().isEmpty()) errors.add("Имя не может быть пустым.");
        if (student.getMiddleName().isEmpty()) errors.add("Отчество не может быть пустым.");
        if (student.getLastName().isEmpty()) errors.add("Фамилия не может быть пустой.");
        if (student.getAddress().isEmpty()) errors.add("Поле адрес должно быть заполнено.");

        return wrapResult(errors);
    }
}
