/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.dto_to_entity;

import com.core.dto.api.INotificationsLogDTO;
import com.model.entity.NotificationsLogEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertLocalDateTimeToDate;

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
