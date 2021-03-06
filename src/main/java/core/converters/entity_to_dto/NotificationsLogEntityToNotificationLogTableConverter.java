/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.converters.entity_to_dto;

import core.dto.NotificationLogTableDTOImpl;
import core.dto.api.INotificationLogTableDTO;
import model.entity.NotificationsLogEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.ALLOCATION_TABLE_FORMATTER;
import static core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 24.12.16.
 */
@Component
public class NotificationsLogEntityToNotificationLogTableConverter implements Converter<NotificationsLogEntity, INotificationLogTableDTO> {

    @Override
    public INotificationLogTableDTO convert(NotificationsLogEntity source) {
        if (source == null) return null;

        INotificationLogTableDTO target = new NotificationLogTableDTOImpl();

        target.setId(String.valueOf(source.getId()));
        target.setDate(convertDateToLocalDateTime(source.getNotificationDate()).format(ALLOCATION_TABLE_FORMATTER));
        target.setType(source.getNotificationType().getDescription());
        target.setDescription(source.getPayload());

        return target;
    }
}
