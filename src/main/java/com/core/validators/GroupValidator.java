/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.validators;

import com.core.commons.Result;
import com.core.dto.api.IGroupsDTO;
import com.core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class GroupValidator implements IValidator<Result, IGroupsDTO> {

    @Override
    public Result validate(IGroupsDTO group) {
        List<String> errors = new ArrayList<>();

        if (group.getTitle().isEmpty()) errors.add("Краткое название должно быть заполнено.");
        if (group.getQualification().isEmpty()) errors.add("Поле квалификация должно быть заполнено.");
        if (group.getNumber().isEmpty()) errors.add("Номер группы не может отсутствовать.");
        if (group.getSpecialization().isEmpty()) errors.add("Специализация обязательна к указанию.");
        if (group.getRuler() == null) errors.add("Группа не может быть без руководителя (мастера, или куратора).");
        if (group.getIssueYear() == null || group.getIssueMonth() == null) {
            errors.add("Вы не указали дату выпуска группы.");
        }
        if (group.getStage() == null) errors.add("Не указана ступень.");

        return wrapResult(errors);
    }
}
