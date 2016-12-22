/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.converters.dto_to_entity;

import core.dto.api.IEventsDTO;
import model.entity.EventsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.convertLocalDateTimeToDate;

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
