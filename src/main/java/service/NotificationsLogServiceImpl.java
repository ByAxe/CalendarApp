package service;

import core.dto.NotificationsLogDTOImpl;
import core.dto.api.INotificationsLogDTO;
import core.enums.NotificationType;
import model.entity.NotificationsLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.NotificationsLogRepository;
import service.api.INotificationsLogService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by byaxe on 20.12.16.
 */
@Service("notificationsLogService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class NotificationsLogServiceImpl implements INotificationsLogService {

    private final ConversionService conversionService;
    private final NotificationsLogRepository notificationsLogRepository;

    @Autowired
    public NotificationsLogServiceImpl(ConversionService conversionService, NotificationsLogRepository notificationsLogRepository) {
        this.conversionService = conversionService;
        this.notificationsLogRepository = notificationsLogRepository;
    }

    @Override
    @Transactional
    public INotificationsLogDTO save(INotificationsLogDTO dto) {
        NotificationsLogEntity entity = convertDtoToEntity(dto);

        NotificationsLogEntity savedEntity = notificationsLogRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<INotificationsLogDTO> save(Iterable<INotificationsLogDTO> dtoList) {
        List<NotificationsLogEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<NotificationsLogEntity> savedEntities = notificationsLogRepository.save(entities);

        return convertListEntityToDto(savedEntities);
    }

    /**
     * Добавление записи в лог уведомлений
     *
     * @param notificationType Тип уведомления {@link NotificationType}
     * @param payload          суть/тело уведомления
     */
    @Override
    @Transactional
    public void addNotification(NotificationType notificationType, String payload) {
        INotificationsLogDTO notificationLogRow = new NotificationsLogDTOImpl();

        notificationLogRow.setUuid(UUID.randomUUID());
        notificationLogRow.setDtUpdate(new Date());

        notificationLogRow.setNotificationType(notificationType);
        notificationLogRow.setNotificationDate(LocalDateTime.now(ZoneId.systemDefault()));
        notificationLogRow.setPayload(payload);

        save(notificationLogRow);
    }

    @Override
    public Iterable<INotificationsLogDTO> findAll(Sort sort) {
        Iterable<NotificationsLogEntity> entities = notificationsLogRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<INotificationsLogDTO> findAll(Pageable pageable) {
        Page<NotificationsLogEntity> entities = notificationsLogRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<INotificationsLogDTO> findAll() {
        Iterable<NotificationsLogEntity> entities = notificationsLogRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<INotificationsLogDTO> findAll(Iterable<Long> idList) {
        Iterable<NotificationsLogEntity> entities = notificationsLogRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public List<INotificationsLogDTO> findByViewedTrue() {
        List<NotificationsLogEntity> entities = notificationsLogRepository.findByViewedTrue();

        return convertListEntityToDto(entities);
    }

    @Override
    public List<INotificationsLogDTO> findByViewedFalse() {
        List<NotificationsLogEntity> entities = notificationsLogRepository.findByViewedFalse();

        return convertListEntityToDto(entities);
    }

    @Override
    public INotificationsLogDTO findOne(Long id) {
        NotificationsLogEntity entity = notificationsLogRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return notificationsLogRepository.count();
    }

    @Override
    public long countFindByViewedFalse() {
        return notificationsLogRepository.countFindByViewedFalse();
    }

    @Override
    public boolean exists(Long id) {
        return notificationsLogRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        notificationsLogRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(INotificationsLogDTO dto) {
        NotificationsLogEntity entity = convertDtoToEntity(dto);

        notificationsLogRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends INotificationsLogDTO> dtoList) {
        List<NotificationsLogEntity> entities = convertListDtoToEntity((Iterable<INotificationsLogDTO>) dtoList);

        notificationsLogRepository.delete(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        notificationsLogRepository.deleteAll();
    }

    @Override
    public NotificationsLogEntity convertDtoToEntity(INotificationsLogDTO dto) {
        return conversionService.convert(dto, NotificationsLogEntity.class);
    }

    @Override
    public INotificationsLogDTO convertEntityToDto(NotificationsLogEntity entity) {
        return conversionService.convert(entity, INotificationsLogDTO.class);
    }
}
