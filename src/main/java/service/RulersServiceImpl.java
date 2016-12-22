/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service;

import core.dto.api.IRulersDTO;
import model.entity.RulersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RulersRepository;
import service.api.IRulersService;

import java.util.List;

/**
 * Created by byaxe on 17.12.16.
 */
@Service("rulersService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class RulersServiceImpl implements IRulersService {

    private final RulersRepository rulersRepository;
    private final ConversionService conversionService;

    @Autowired
    public RulersServiceImpl(RulersRepository rulersRepository, ConversionService conversionService) {
        this.rulersRepository = rulersRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public IRulersDTO save(IRulersDTO dto) {
        if (dto == null) throw new IllegalArgumentException();

        RulersEntity sourceEntity = convertDtoToEntity(dto);

        RulersEntity savedEntity = rulersRepository.save(sourceEntity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IRulersDTO> save(Iterable<IRulersDTO> dtoList) {
        if (dtoList == null) throw new IllegalArgumentException();

        List<RulersEntity> sourceEntities = convertListDtoToEntity(dtoList);

        Iterable<RulersEntity> savedEntities = rulersRepository.save(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IRulersDTO> findAll(Sort sort) {
        Iterable<RulersEntity> entities = rulersRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IRulersDTO> findAll(Pageable pageable) {
        Page<RulersEntity> entities = rulersRepository.findAll(pageable);

        List<IRulersDTO> dtoList = convertListEntityToDto(entities);

        return new PageImpl<>(dtoList);
    }

    @Override
    public Iterable<IRulersDTO> findAll() {
        Iterable<RulersEntity> entities = rulersRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IRulersDTO> findAll(Iterable<Long> ids) {
        Iterable<RulersEntity> entities = rulersRepository.findAll(ids);

        return convertListEntityToDto(entities);
    }

    @Override
    public IRulersDTO findOne(Long id) {
        return convertEntityToDto(getActualEntity(id));
    }

    @Override
    public RulersEntity getActualEntity(Long id) {
        return rulersRepository.findOne(id);
    }

    @Override
    public long count() {
        return rulersRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return rulersRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        rulersRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IRulersDTO dto) {
        RulersEntity entity = convertDtoToEntity(dto);

        rulersRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IRulersDTO> dtoList) {
        List<RulersEntity> entities = convertListDtoToEntity((Iterable<IRulersDTO>) dtoList);

        rulersRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RulersEntity convertDtoToEntity(IRulersDTO dto) {
        return conversionService.convert(dto, RulersEntity.class);
    }

    @Override
    public IRulersDTO convertEntityToDto(RulersEntity entity) {
        return conversionService.convert(entity, IRulersDTO.class);
    }
}
