/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service;

import core.dto.api.IStudentsDTO;
import model.entity.StudentsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.StudentsRepository;
import service.api.IStudentsService;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("studentsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class StudentsServiceImpl implements IStudentsService {

    private final StudentsRepository studentsRepository;
    private final ConversionService conversionService;

    @Autowired
    public StudentsServiceImpl(StudentsRepository studentsRepository, ConversionService conversionService) {
        this.studentsRepository = studentsRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public IStudentsDTO save(IStudentsDTO dto) {
        StudentsEntity entity = convertDtoToEntity(dto);

        StudentsEntity savedEntity = studentsRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IStudentsDTO> save(Iterable<IStudentsDTO> dtoList) {
        List<StudentsEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<StudentsEntity> savedEntities = studentsRepository.save(entities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IStudentsDTO> findAll(Sort sort) {
        Iterable<StudentsEntity> entities = studentsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IStudentsDTO> findAll(Pageable pageable) {
        Page<StudentsEntity> entities = studentsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IStudentsDTO> findAll() {
        Iterable<StudentsEntity> entities = studentsRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IStudentsDTO> findAll(Iterable<Long> idList) {
        Iterable<StudentsEntity> entities = studentsRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IStudentsDTO findOne(Long id) {
        StudentsEntity entity = studentsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return studentsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return studentsRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentsRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IStudentsDTO dto) {
        StudentsEntity entity = convertDtoToEntity(dto);

        studentsRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IStudentsDTO> dtoList) {
        List<StudentsEntity> entities = convertListDtoToEntity((Iterable<IStudentsDTO>) dtoList);

        studentsRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StudentsEntity convertDtoToEntity(IStudentsDTO dto) {
        return conversionService.convert(dto, StudentsEntity.class);
    }

    @Override
    public IStudentsDTO convertEntityToDto(StudentsEntity entity) {
        return conversionService.convert(entity, IStudentsDTO.class);
    }
}
