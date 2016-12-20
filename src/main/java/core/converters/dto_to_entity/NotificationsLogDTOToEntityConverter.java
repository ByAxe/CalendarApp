package core.converters.dto_to_entity;

import core.dto.api.INotificationsLogDTO;
import model.entity.NotificationsLogEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.convertLocalDateTimeToDate;

/**
 * Created by byaxe on 20.12.16.
 */
@Component
public class NotificationsLogDTOToEntityConverter implements Converter<INotificationsLogDTO, NotificationsLogEntity> {

    @Override
    public NotificationsLogEntity convert(INotificationsLogDTO source) {
        if (source == null) return null;

        NotificationsLogEntity target = new NotificationsLogEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setViewed(source.isViewed());
        target.setPayload(source.getPayload());
        target.setNotificationDate(convertLocalDateTimeToDate(source.getNotificationDate()));
        target.setNotificationType(source.getNotificationType());

        return target;
    }
}
