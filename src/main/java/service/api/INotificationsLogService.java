package service.api;

import core.dto.api.INotificationsLogDTO;
import core.enums.NotificationType;
import model.entity.NotificationsLogEntity;

import java.util.List;

/**
 * Created by byaxe on 20.12.16.
 */
public interface INotificationsLogService extends IEssenceService<INotificationsLogDTO, Long>,
        IConversionService<INotificationsLogDTO, NotificationsLogEntity> {

    List<INotificationsLogDTO> findByViewedTrue();

    List<INotificationsLogDTO> findByViewedFalse();

    long countFindByViewedFalse();

    void addNotification(NotificationType notificationType, String payload);
}
