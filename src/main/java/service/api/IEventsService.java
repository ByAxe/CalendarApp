package service.api;

import core.dto.api.IEventsDTO;

import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEventsService extends IEssenceService<IEventsDTO, Long> {
    List<IEventsDTO> findUpcomingEvents();
}
