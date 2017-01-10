/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.validators;

import com.core.commons.Result;
import com.core.dto.api.IEventsDTO;
import com.core.validators.api.IValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.core.commons.Utils.wrapResult;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by A.Litvinau on 12/10/2016.
 */
@Component
public class EventValidator implements IValidator<Result, IEventsDTO> {

    @Value("${minimal.event.longing.minutes}")
    private int MINIMAL_EVENT_LONGING_MINUTES;

    public Result validate(IEventsDTO event) {
        List<String> errors = new ArrayList<>();

        if (event.getStarts().isAfter(event.getEnds())) {
            errors.add("Начальная дата события не может быть больше конечной.");
        }

        if (MINUTES.between(event.getStarts(), event.getEnds()) < MINIMAL_EVENT_LONGING_MINUTES) {
            errors.add("Событие не может длиться менее " + MINIMAL_EVENT_LONGING_MINUTES + "  минут.");
        }

        if (event.getTitle().isEmpty()) {
            errors.add("Описание события не может быть пустым.");
        }

        return wrapResult(errors);
    }
}
