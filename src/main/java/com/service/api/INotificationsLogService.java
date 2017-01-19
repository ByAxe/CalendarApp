/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.api;

import com.core.dto.api.INotificationsLogDTO;
import com.core.enums.NotificationType;
import com.model.entity.NotificationsLogEntity;

import java.util.List;

/**
 * Created by byaxe on 20.12.16.
 */
public interface INotificationsLogService extends IEssenceService<INotificationsLogDTO, Long>,
        IConversionService<INotificationsLogDTO, NotificationsLogEntity> {

    List<INotificationsLogDTO> findByViewedTrue();

    List<INotificationsLogDTO> findByViewedFalse();

    <T> List<T> findAllForTable();

    long countFindByViewedFalse();

    void addNotification(NotificationType notificationType, String payload);
}
