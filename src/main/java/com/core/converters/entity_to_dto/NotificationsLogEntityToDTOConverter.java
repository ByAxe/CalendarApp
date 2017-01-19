/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.NotificationsLogDTOImpl;
import com.core.dto.api.INotificationsLogDTO;
import com.model.entity.NotificationsLogEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 20.12.16.
 */
@Component
public class NotificationsLogEntityToDTOConverter implements Converter<NotificationsLogEntity, INotificationsLogDTO> {

    @Override
    public INotificationsLogDTO convert(NotificationsLogEntity source) {
        if (source == null) return null;

        INotificationsLogDTO target = new NotificationsLogDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setViewed(source.isViewed());
        target.setPayload(source.getPayload());
        target.setNotificationDate(convertDateToLocalDateTime(source.getNotificationDate()));
        target.setNotificationType(source.getNotificationType());

        return target;
    }
}
