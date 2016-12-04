package service.impl;

import core.dto.api.IEventsDTO;
import model.entity.EventsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.EventsRepository;
import service.api.IEventsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 * <p>
 * Бизнес-логика событий
 */
@Service("eventsService")
public class EventsServiceImpl implements IEventsService {

    private final EventsRepository eventsRepository;

    private final ConversionService conversionService;

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
    public IEventsDTO save(IEventsDTO sourceDto) {
        EventsEntity sourceEntity = convertDtoToEntity(sourceDto);

        EventsEntity savedEntity = eventsRepository.save(sourceEntity);

        IEventsDTO savedDto = convertEntityToDto(savedEntity);

        return savedDto;
    }

    /**
     * Сохранение списка сущностей
     *
     * @param sourceDtoList сохраняемый список сущностей в виде DTO
     * @return сохраненный список сущностей в виде DTO
     */
    @Override
    public Iterable<IEventsDTO> save(Iterable<IEventsDTO> sourceDtoList) {
        List<EventsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        Iterable<EventsEntity> savedEntities = eventsRepository.save(sourceEntities);

        List<IEventsDTO> savedDtoList = convertListEntityToDto(savedEntities);

        return savedDtoList;
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

        List<IEventsDTO> dtoList = convertListEntityToDto(entities);

        return dtoList;
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

        List<IEventsDTO> dtoList = convertListEntityToDto(entities);

        return new PageImpl<>(dtoList);
    }

    /**
     * Находим все события
     *
     * @return Список событий как DTO
     */
    @Override
    public Iterable<IEventsDTO> findAll() {
        Iterable<EventsEntity> eventsEntities = eventsRepository.findAll();

        List<IEventsDTO> dtoList = convertListEntityToDto(eventsEntities);

        return dtoList;
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

        List<IEventsDTO> dtoList = convertListEntityToDto(eventsEntities);

        return dtoList;
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

        IEventsDTO dto = convertEntityToDto(entity);

        return dto;
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
    public void delete(Long id) {
        eventsRepository.delete(id);
    }

    /**
     * Удаление события по сущности
     *
     * @param dto Сущность
     */
    @Override
    public void delete(IEventsDTO dto) {
        EventsEntity entity = convertDtoToEntity(dto);

        eventsRepository.delete(entity);
    }

    /**
     * Удаление списка событий
     *
     * @param dtoList список сущностей
     */
    @Override
    public void delete(Iterable<? extends IEventsDTO> dtoList) {
        List<EventsEntity> entities = convertListDtoToEntity((Iterable<IEventsDTO>) dtoList);

        eventsRepository.delete(entities);
    }

    /**
     * Удаление всех событий
     */
    @Override
    public void deleteAll() {
        eventsRepository.deleteAll();
    }


    /**
     * Конвертируем DTO в Сущность
     *
     * @param eventsDTO Исходный DTO
     * @return Сущность
     */
    private EventsEntity convertDtoToEntity(IEventsDTO eventsDTO) {
        return conversionService.convert(eventsDTO, EventsEntity.class);
    }

    /**
     * Конвертируем Сущность в DTO
     *
     * @param entity Исходная сущность
     * @return DTO
     */
    private IEventsDTO convertEntityToDto(EventsEntity entity) {
        return conversionService.convert(entity, IEventsDTO.class);
    }

    /**
     * Конвертация списка DTO в список Сущностей
     *
     * @param sourceDtoList Список DTO
     * @return Список Сущсностей
     */
    private List<EventsEntity> convertListDtoToEntity(Iterable<IEventsDTO> sourceDtoList) {
        final List<EventsEntity> sourceEntities = new ArrayList<>();

        sourceDtoList.forEach(dto -> sourceEntities.add(conversionService.convert(dto, EventsEntity.class)));

        return sourceEntities;
    }

    /**
     * Конвертация списка Сущностей в список DTO
     *
     * @param sourceEntities Список Сущностей
     * @return Список DTO
     */
    private List<IEventsDTO> convertListEntityToDto(Iterable<EventsEntity> sourceEntities) {

        final List<IEventsDTO> savedDtoList = new ArrayList<>();

        sourceEntities.forEach(entity -> savedDtoList.add(conversionService.convert(entity, IEventsDTO.class)));

        return savedDtoList;
    }

}
