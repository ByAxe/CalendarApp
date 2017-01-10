/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.EventsDTOImpl;
import com.core.dto.api.IEventsDTO;
import com.model.entity.EventsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class EventsEntityToDTOConverter implements Converter<EventsEntity, IEventsDTO> {

    @Override
    public IEventsDTO convert(EventsEntity source) {
        if (source == null) return null;

        IEventsDTO target = new EventsDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setStarts(convertDateToLocalDateTime(source.getStarts()));
        target.setEnds(convertDateToLocalDateTime(source.getEnds()));
        target.setTitle(source.getTitle());
        target.setFrequency(source.getFrequency());
        target.setNoticePeriod(source.getNoticePeriod());
        target.setPriority(source.getPriority());

        return target;
    }
}
