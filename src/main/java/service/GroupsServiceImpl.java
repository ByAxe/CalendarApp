/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service;

import core.dto.api.IGroupsDTO;
import model.entity.GroupsEntity;
import model.entity.RulersEntity;
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
import service.api.IRulersService;

import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 */
@Service("groupsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class GroupsServiceImpl implements IGroupsService {

    private final GroupsRepository groupsRepository;
    private final IRulersService rulersService;
    private final ConversionService conversionService;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository, IRulersService rulersService, ConversionService conversionService) {
        this.groupsRepository = groupsRepository;
        this.rulersService = rulersService;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public IGroupsDTO save(IGroupsDTO sourceDto) {
        if (sourceDto == null) throw new IllegalArgumentException();

        GroupsEntity newEntity = convertDtoToEntity(sourceDto);

        RulersEntity ruler = rulersService.getActualEntity(newEntity.getRuler().getId());

        newEntity.setRuler(ruler);

        GroupsEntity savedEntity = groupsRepository.save(newEntity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IGroupsDTO> save(Iterable<IGroupsDTO> sourceDtoList) {
        if (sourceDtoList == null) throw new IllegalArgumentException();

        List<GroupsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        sourceEntities.forEach(newEntity -> {
            RulersEntity ruler = rulersService.getActualEntity(newEntity.getRuler().getId());
            newEntity.setRuler(ruler);
        });

        Iterable<GroupsEntity> savedEntities = groupsRepository.save(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Sort sort) {
        Iterable<GroupsEntity> entities = groupsRepository.findAll(sort);

        return convertListEntityToDto(entities);
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

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Iterable<Long> ids) {
        Iterable<GroupsEntity> entities = groupsRepository.findAll(ids);

        return convertListEntityToDto(entities);
    }

    @Override
    public IGroupsDTO findOne(Long id) {
        GroupsEntity entity = groupsRepository.findOne(id);

        return convertEntityToDto(entity);
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


    public GroupsEntity convertDtoToEntity(IGroupsDTO dto) {
        return conversionService.convert(dto, GroupsEntity.class);
    }

    public IGroupsDTO convertEntityToDto(GroupsEntity entity) {
        return conversionService.convert(entity, IGroupsDTO.class);
    }
}
