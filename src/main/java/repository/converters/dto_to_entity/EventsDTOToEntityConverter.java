package repository.converters.dto_to_entity;

import core.dto.api.IEventsDTO;
import model.entity.EventsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class EventsDTOToEntityConverter implements Converter<IEventsDTO, EventsEntity> {

    @Override
    public EventsEntity convert(IEventsDTO source) {
        if (source == null) return null;

        EventsEntity target = new EventsEntity();

        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setStarts(Date.from(source.getStarts().atZone(ZoneId.systemDefault()).toInstant()));
        target.setEnds(Date.from(source.getEnds().atZone(ZoneId.systemDefault()).toInstant()));
        target.setTitle(source.getTitle());
        target.setPriority(source.getPriority());
        target.setNoticePeriod(source.getNoticePeriod());
        target.setFrequency(source.getFrequency());

        return target;
    }
}
