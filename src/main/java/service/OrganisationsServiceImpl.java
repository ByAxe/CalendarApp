/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service;

import core.dto.api.IOrganisationsDTO;
import model.entity.OrganisationsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.OrganisationsRepository;
import service.api.IOrganisationsService;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Service
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class OrganisationsServiceImpl implements IOrganisationsService {

    private final ConversionService conversionService;
    private final OrganisationsRepository organisationsRepository;

    @Autowired
    public OrganisationsServiceImpl(ConversionService conversionService, OrganisationsRepository organisationsRepository) {
        this.conversionService = conversionService;
        this.organisationsRepository = organisationsRepository;
    }

    @Override
    @Transactional
    public IOrganisationsDTO save(IOrganisationsDTO dto) {
        OrganisationsEntity entity = convertDtoToEntity(dto);

        OrganisationsEntity savedEntity = organisationsRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IOrganisationsDTO> save(Iterable<IOrganisationsDTO> dtoList) {
        List<OrganisationsEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<OrganisationsEntity> savedEntities = organisationsRepository.save(entities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll(Sort sort) {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IOrganisationsDTO> findAll(Pageable pageable) {
        Page<OrganisationsEntity> entities = organisationsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll() {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll(Iterable<Long> idList) {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IOrganisationsDTO findOne(Long id) {
        OrganisationsEntity entity = organisationsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return organisationsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return organisationsRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        organisationsRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IOrganisationsDTO dto) {
        organisationsRepository.delete(convertDtoToEntity(dto));
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IOrganisationsDTO> dtoList) {
        organisationsRepository.delete(convertListDtoToEntity((Iterable<IOrganisationsDTO>) dtoList));
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrganisationsEntity convertDtoToEntity(IOrganisationsDTO dto) {
        return conversionService.convert(dto, OrganisationsEntity.class);
    }

    @Override
    public IOrganisationsDTO convertEntityToDto(OrganisationsEntity entity) {
        return conversionService.convert(entity, IOrganisationsDTO.class);
    }
}
