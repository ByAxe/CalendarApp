/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service.api;

import core.dto.api.IEventsDTO;
import model.entity.EventsEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEventsService extends IEssenceService<IEventsDTO, Long>, IConversionService<IEventsDTO, EventsEntity> {
    List<IEventsDTO> findUpcomingEvents();

    List<IEventsDTO> findUpcomingEventsForPeriod(Date starts, Date ends);
}
