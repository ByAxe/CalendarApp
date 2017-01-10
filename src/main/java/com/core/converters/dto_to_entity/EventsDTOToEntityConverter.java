/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.dto_to_entity;

import com.core.dto.api.IEventsDTO;
import com.model.entity.EventsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertLocalDateTimeToDate;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class EventsDTOToEntityConverter implements Converter<IEventsDTO, EventsEntity> {

    @Override
    public EventsEntity convert(IEventsDTO source) {
        if (source == null) return null;

        EventsEntity target = new EventsEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setStarts(convertLocalDateTimeToDate(source.getStarts()));
        target.setEnds(convertLocalDateTimeToDate(source.getEnds()));
        target.setTitle(source.getTitle());
        target.setPriority(source.getPriority());
        target.setNoticePeriod(source.getNoticePeriod());
        target.setFrequency(source.getFrequency());

        return target;
    }
}
