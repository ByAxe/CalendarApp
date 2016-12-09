package service;

import core.dto.api.IGroupsDTO;
import model.entity.GroupsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GroupsRepository;
import service.api.IGroupsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 */
@Service("groupsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class GroupsServiceImpl implements IGroupsService {

    private final GroupsRepository groupsRepository;

    private final ConversionService conversionService;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository, ConversionService conversionService) {
        this.groupsRepository = groupsRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public IGroupsDTO save(IGroupsDTO sourceDto) {
        GroupsEntity sourceEntity = convertDtoToEntity(sourceDto);

        GroupsEntity savedEntity = groupsRepository.save(sourceEntity);

        IGroupsDTO savedDto = convertEntityToDto(savedEntity);

        return savedDto;
    }

    @Override
    @Transactional
    public Iterable<IGroupsDTO> save(Iterable<IGroupsDTO> sourceDtoList) {
        List<GroupsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        Iterable<GroupsEntity> savedEntities = groupsRepository.save(sourceEntities);

        List<IGroupsDTO> savedDtoList = convertListEntityToDto(savedEntities);

        return savedDtoList;
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Sort sort) {
        Iterable<GroupsEntity> entities = groupsRepository.findAll(sort);

        List<IGroupsDTO> dtoList = convertListEntityToDto(entities);

        return dtoList;
    }

    @Override
    public Page<IGroupsDTO> findAll(Pageable pageable) {
        Page<GroupsEntity> entities = groupsRepository.findAll(pageable);

        List<IGroupsDTO> dtoList = convertListEntityToDto(entities);

        return new PageImpl<>(dtoList);
    }

    @Override
    public Iterable<IGroupsDTO> findAll() {
        Iterable<GroupsEntity> entities = groupsRepository.findAll();

        List<IGroupsDTO> dtoList = convertListEntityToDto(entities);

        return dtoList;
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Iterable<Long> ids) {
        Iterable<GroupsEntity> entities = groupsRepository.findAll(ids);

        List<IGroupsDTO> dtoList = convertListEntityToDto(entities);

        return dtoList;
    }

    @Override
    public IGroupsDTO findOne(Long id) {
        GroupsEntity entity = groupsRepository.findOne(id);

        IGroupsDTO groupsDTO = convertEntityToDto(entity);

        return groupsDTO;
    }

    @Override
    public long count() {
        return groupsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return groupsRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        groupsRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IGroupsDTO dto) {
        GroupsEntity entity = convertDtoToEntity(dto);

        groupsRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IGroupsDTO> dtoList) {
        List<GroupsEntity> entities = convertListDtoToEntity((Iterable<IGroupsDTO>) dtoList);

        groupsRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }


    /**
     * Конвертируем DTO в Сущность
     *
     * @param eventsDTO Исходный DTO
     * @return Сущность
     */
    private GroupsEntity convertDtoToEntity(IGroupsDTO eventsDTO) {
        return conversionService.convert(eventsDTO, GroupsEntity.class);
    }

    /**
     * Конвертируем Сущность в DTO
     *
     * @param entity Исходная сущность
     * @return DTO
     */
    private IGroupsDTO convertEntityToDto(GroupsEntity entity) {
        return conversionService.convert(entity, IGroupsDTO.class);
    }

    /**
     * Конвертация списка DTO в список Сущностей
     *
     * @param sourceDtoList Список DTO
     * @return Список Сущсностей
     */
    private List<GroupsEntity> convertListDtoToEntity(Iterable<IGroupsDTO> sourceDtoList) {
        final List<GroupsEntity> sourceEntities = new ArrayList<>();

        sourceDtoList.forEach(d -> sourceEntities.add(convertDtoToEntity(d)));

        return sourceEntities;
    }

    /**
     * Конвертация списка Сущностей в список DTO
     *
     * @param sourceEntities Список Сущностей
     * @return Список DTO
     */
    private List<IGroupsDTO> convertListEntityToDto(Iterable<GroupsEntity> sourceEntities) {

        final List<IGroupsDTO> savedDtoList = new ArrayList<>();

        sourceEntities.forEach(e -> savedDtoList.add(convertEntityToDto(e)));

        return savedDtoList;
    }
}
