package service;

import core.dto.api.IAllocationDTO;
import model.entity.AllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AllocationRepository;
import service.api.*;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("allocationService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class AllocationServiceImpl implements IAllocationService {

    private final AllocationRepository allocationRepository;
    private final ConversionService conversionService;
    private final IGroupsService groupsService;
    private final IOrganisationsService organisationsService;
    private final IStudentsService studentsService;
    private final IOrdersService ordersService;

    @Autowired
    public AllocationServiceImpl(AllocationRepository allocationRepository, ConversionService conversionService,
                                 IGroupsService groupsService, IOrganisationsService organisationsService,
                                 IStudentsService studentsService, IOrdersService ordersService) {
        this.allocationRepository = allocationRepository;
        this.conversionService = conversionService;
        this.groupsService = groupsService;
        this.organisationsService = organisationsService;
        this.studentsService = studentsService;
        this.ordersService = ordersService;
    }

    @Override
    @Transactional
    public IAllocationDTO save(IAllocationDTO dto) {
        if (dto == null) throw new IllegalArgumentException();

        AllocationEntity entity = convertDtoToEntity(dto);

        AllocationEntity savedEntity = allocationRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IAllocationDTO> save(Iterable<IAllocationDTO> dtoList) {
        if (dtoList == null) throw new IllegalArgumentException();

        List<AllocationEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<AllocationEntity> savedEntity = allocationRepository.save(entities);

        return convertListEntityToDto(savedEntity);
    }

    @Override
    public Iterable<IAllocationDTO> findAll(Sort sort) {
        Iterable<AllocationEntity> entities = allocationRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IAllocationDTO> findAll(Pageable pageable) {
        Page<AllocationEntity> entities = allocationRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IAllocationDTO> findAll() {
        Iterable<AllocationEntity> entities = allocationRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IAllocationDTO> findAll(Iterable<Long> idList) {
        Iterable<AllocationEntity> entities = allocationRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IAllocationDTO findOne(Long id) {
        AllocationEntity entity = allocationRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return allocationRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return allocationRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        allocationRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IAllocationDTO dto) {
        AllocationEntity entity = convertDtoToEntity(dto);

        allocationRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IAllocationDTO> dtoList) {
        List<AllocationEntity> entities = convertListDtoToEntity((Iterable<IAllocationDTO>) dtoList);

        allocationRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AllocationEntity convertDtoToEntity(IAllocationDTO dto) {
        return conversionService.convert(dto, AllocationEntity.class);
    }

    @Override
    public IAllocationDTO convertEntityToDto(AllocationEntity entity) {
        return conversionService.convert(entity, IAllocationDTO.class);
    }
}
