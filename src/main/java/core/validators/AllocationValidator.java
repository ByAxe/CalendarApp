/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IAllocationDTO;
import core.dto.api.IOrdersDTO;
import core.validators.api.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class AllocationValidator implements IValidator<Result, IAllocationDTO> {

    private final IValidator<Result, IOrdersDTO> validator;

    @Autowired
    public AllocationValidator(IValidator<Result, IOrdersDTO> validator) {
        this.validator = validator;
    }

    @Override
    public Result validate(IAllocationDTO row) {
        List<String> errors = new ArrayList<>();

        if (row.getStudent() == null) errors.add("Вы не выбрали выпускника.");
        if (row.getGroup() == null) errors.add("Группа не указана.");
        if (row.getOrganisation() == null) errors.add("Организация не указана.");

        errors.addAll(validator.validate(row.getOrder()).getPayload());

        if (row.isFreeAllocation() && row.getFreeAllocationReason().isEmpty()) {
            errors.add("Вы не указали причину свободного распределения.");
        }

        if (row.isVoluntaryCompensation()) {
            if (row.getVoluntaryCompensationOrderNumber().isEmpty()) {
                errors.add("Номер приказа о добровольном возмещении не указан.");
            }
        } else if (row.getCortOrderDate() != null) {
            if (row.getCortOrderNumber().isEmpty()) {
                errors.add("Номер приказа о возмещении через суд не указан.");
            }
        }

        return wrapResult(errors);
    }
}
