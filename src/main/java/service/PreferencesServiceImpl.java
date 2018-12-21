/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import core.dto.api.IPreferencesDTO;
import model.entity.PreferencesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PreferencesRepository;
import service.api.IPreferencesService;

/**
 * Created by byaxe on 20.12.16.
 */
@Service("prefencesService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class PreferencesServiceImpl implements IPreferencesService {

    private final ConversionService conversionService;
    private final PreferencesRepository preferencesRepository;

    @Autowired
    public PreferencesServiceImpl(ConversionService conversionService, PreferencesRepository preferencesRepository) {
        this.conversionService = conversionService;
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    @Transactional
    public IPreferencesDTO save(IPreferencesDTO dto) {
        PreferencesEntity entity = convertDtoToEntity(dto);

        PreferencesEntity savedEntity = preferencesRepository.save(entity);

        if (savedEntity.getId() != 1) throw new UnsupportedOperationException();

        IPreferencesDTO preferences = convertEntityToDto(savedEntity);

        return preferences;
    }

    @Override
    public Iterable<IPreferencesDTO> save(Iterable<IPreferencesDTO> dtoList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<IPreferencesDTO> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<IPreferencesDTO> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<IPreferencesDTO> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<IPreferencesDTO> findAll(Iterable<Long> idList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPreferencesDTO findOne(Long id) {
        PreferencesEntity entity = findOneEntity(id);

        return convertEntityToDto(entity);
    }

    private PreferencesEntity findOneEntity(Long id) {
        return preferencesRepository.findById(id).orElse(null);
    }

    @Override
    public IPreferencesDTO get() {
        return findOne(1L);
    }

    @Override
    public long count() {
        return preferencesRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return preferencesRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(IPreferencesDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Iterable<? extends IPreferencesDTO> dtoList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreferencesEntity convertDtoToEntity(IPreferencesDTO dto) {
        return conversionService.convert(dto, PreferencesEntity.class);
    }

    @Override
    public IPreferencesDTO convertEntityToDto(PreferencesEntity entity) {
        return conversionService.convert(entity, IPreferencesDTO.class);
    }
}
