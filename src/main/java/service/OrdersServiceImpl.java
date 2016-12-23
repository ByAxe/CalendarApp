/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import core.dto.api.IOrdersDTO;
import model.entity.OrdersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.OrdersRepository;
import service.api.IOrdersService;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("ordersService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class OrdersServiceImpl implements IOrdersService {

    private final OrdersRepository ordersRepository;
    private final ConversionService conversionService;

    @Autowired
    public OrdersServiceImpl(ConversionService conversionService, OrdersRepository ordersRepository) {
        this.conversionService = conversionService;
        this.ordersRepository = ordersRepository;
    }

    @Override
    @Transactional
    public IOrdersDTO save(IOrdersDTO dto) {
        OrdersEntity entity = convertDtoToEntity(dto);

        OrdersEntity savedEntity = ordersRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IOrdersDTO> save(Iterable<IOrdersDTO> dtoList) {
        List<OrdersEntity> sourceEntities = convertListDtoToEntity(dtoList);

        Iterable<OrdersEntity> savedEntities = ordersRepository.save(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IOrdersDTO> findAll(Sort sort) {
        Iterable<OrdersEntity> entities = ordersRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IOrdersDTO> findAll(Pageable pageable) {
        Page<OrdersEntity> entities = ordersRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IOrdersDTO> findAll() {
        Iterable<OrdersEntity> entities = ordersRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IOrdersDTO> findAll(Iterable<Long> idList) {
        Iterable<OrdersEntity> entities = ordersRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IOrdersDTO findOne(Long id) {
        OrdersEntity entity = ordersRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return ordersRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return ordersRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ordersRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IOrdersDTO dto) {
        ordersRepository.delete(convertDtoToEntity(dto));
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IOrdersDTO> dtoList) {
        List<OrdersEntity> entities = convertListDtoToEntity((Iterable<IOrdersDTO>) dtoList);

        ordersRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrdersEntity convertDtoToEntity(IOrdersDTO dto) {
        return conversionService.convert(dto, OrdersEntity.class);
    }

    @Override
    public IOrdersDTO convertEntityToDto(OrdersEntity entity) {
        return conversionService.convert(entity, IOrdersDTO.class);
    }
}
