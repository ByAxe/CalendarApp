/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators;

import core.commons.Result;
import core.dto.api.IAllocationDTO;
import core.enums.CompensationType;
import core.validators.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static core.commons.Utils.wrapResult;

/**
 * Created by byaxe on 25.12.16.
 */
@Component
public class AllocationValidator implements IValidator<Result, IAllocationDTO> {

    @Override
    public Result validate(IAllocationDTO row) {
        List<String> errors = new ArrayList<>();

        if (row.getStudent() == null) errors.add("Вы не выбрали выпускника.");
        if (row.getOrganisation() == null) errors.add("Организация не указана.");

        if (row.isFreeAllocation() && row.getFreeAllocationReason().isEmpty()) {
            errors.add("Вы не указали причину свободного распределения.");
        }

        if (row.getCompensationType() != CompensationType.NONE) {
            if (row.getCompensationOrderNumber().isEmpty()) {
                errors.add("Номер приказа о возмещении не указан.");
            }
            if (row.getCompensationOrderDate() == null) {
                errors.add("Дата приказа о возмещении не указана.");
            }
        }

        return wrapResult(errors);
    }
}
