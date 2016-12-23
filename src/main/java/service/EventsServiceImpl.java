/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import core.dto.api.IEventsDTO;
import core.enums.EventType;
import model.entity.EventsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.EventsRepository;
import service.api.IEventsService;
import service.quartz.JobInitializer;

import java.util.Date;
import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 * <p>
 * Бизнес-логика событий
 */
@Service("eventsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class EventsServiceImpl implements IEventsService {

    private final EventsRepository eventsRepository;
    private final ConversionService conversionService;

    @Autowired
    private JobInitializer jobInitializer;

    @Autowired
    public EventsServiceImpl(EventsRepository eventsRepository, ConversionService conversionService) {
        this.eventsRepository = eventsRepository;
        this.conversionService = conversionService;
    }

    /**
     * Сохранение единичной сущности
     *
     * @param sourceDto сохраняемая сущность в виде DTO
     * @return сохраненная сущность в виде DTO
     */
    @Override
    @Transactional
    public IEventsDTO save(IEventsDTO sourceDto) {
        EventsEntity sourceEntity = convertDtoToEntity(sourceDto);

        EventsEntity savedEntity = eventsRepository.save(sourceEntity);

        IEventsDTO newEvent = convertEntityToDto(savedEntity);

        jobInitializer.synchronizeJobWithCalendarEvents(newEvent, EventType.SAVE);

        return newEvent;
    }

    /**
     * Сохранение списка сущностей
     *
     * @param sourceDtoList сохраняемый список сущностей в виде DTO
     * @return сохраненный список сущностей в виде DTO
     */
    @Override
    @Transactional
    public Iterable<IEventsDTO> save(Iterable<IEventsDTO> sourceDtoList) {
        List<EventsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        Iterable<EventsEntity> savedEntities = eventsRepository.save(sourceEntities);

        List<IEventsDTO> newEvents = convertListEntityToDto(savedEntities);

        newEvents.forEach(e -> jobInitializer.synchronizeJobWithCalendarEvents(e, EventType.SAVE));

        return newEvents;
    }

    /**
     * Возвращает сортированный список событий
     *
     * @param sort схема сортировки
     * @return сортированный список
     */
    @Override
    public Iterable<IEventsDTO> findAll(Sort sort) {
        Iterable<EventsEntity> entities = eventsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    /**
     * Возвращает страницу событий
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<IEventsDTO> findAll(Pageable pageable) {
        Page<EventsEntity> entities = eventsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    /**
     * Находим все события
     *
     * @return Список событий как DTO
     */
    @Override
    public Iterable<IEventsDTO> findAll() {
        Iterable<EventsEntity> eventsEntities = eventsRepository.findAll();

        return convertListEntityToDto(eventsEntities);
    }

    /**
     * Получение списка событий с определенными идентификаторами
     *
     * @param idList Список индефикаторов записей, которые нужно выбрать
     * @return Список DTO с указанными идентификаторами
     */
    @Override
    public Iterable<IEventsDTO> findAll(Iterable<Long> idList) {
        Iterable<EventsEntity> eventsEntities = eventsRepository.findAll(idList);

        return convertListEntityToDto(eventsEntities);
    }

    /**
     * Получение события по идентификатору
     *
     * @param id Идентификатор
     * @return Событие как DTO
     */
    @Override
    public IEventsDTO findOne(Long id) {
        EventsEntity entity = eventsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    /**
     * Получение количества записей
     *
     * @return Количество записей
     */
    @Override
    public long count() {
        return eventsRepository.count();
    }

    /**
     * Проверка на существование сущности с данным идентификатором
     *
     * @param id Идентификатор
     * @return true/false
     */
    @Override
    public boolean exists(Long id) {
        return eventsRepository.exists(id);
    }

    /**
     * Удаление события по идентификатору
     *
     * @param id Идентификатор
     */
    @Override
    @Transactional
    public void delete(Long id) {
        jobInitializer.synchronizeJobWithCalendarEvents(findOne(id), EventType.DELETE);

        eventsRepository.delete(id);
    }

    /**
     * Удаление события по сущности
     *
     * @param dto Сущность
     */
    @Override
    @Transactional
    public void delete(IEventsDTO dto) {
        jobInitializer.synchronizeJobWithCalendarEvents(dto, EventType.DELETE);

        EventsEntity entity = convertDtoToEntity(dto);

        eventsRepository.delete(entity);
    }

    /**
     * Удаление списка событий
     *
     * @param dtoList список сущностей
     */
    @Override
    @Transactional
    public void delete(Iterable<? extends IEventsDTO> dtoList) {
        dtoList.forEach(e -> jobInitializer.synchronizeJobWithCalendarEvents(e, EventType.DELETE));

        List<EventsEntity> entities = convertListDtoToEntity((Iterable<IEventsDTO>) dtoList);

        eventsRepository.delete(entities);
    }

    /**
     * Удаление всех событий
     */
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Найти все предстоящие события
     *
     * @return список предстоящих событий
     */
    @Override
    public List<IEventsDTO> findUpcomingEvents() {
        List<EventsEntity> entities = eventsRepository.findUpcomingEvents();

        return convertListEntityToDto(entities);
    }


    @Override
    public List<IEventsDTO> findUpcomingEventsForPeriod(Date starts, Date ends) {
        List<EventsEntity> entities = eventsRepository.findUpcomingEventsForPeriod(starts, ends);

        return convertListEntityToDto(entities);
    }

    public EventsEntity convertDtoToEntity(IEventsDTO dto) {
        return conversionService.convert(dto, EventsEntity.class);
    }

    public IEventsDTO convertEntityToDto(EventsEntity entity) {
        return conversionService.convert(entity, IEventsDTO.class);
    }

}
