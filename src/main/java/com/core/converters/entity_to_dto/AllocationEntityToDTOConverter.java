/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.AllocationDTOImpl;
import com.core.dto.api.IAllocationDTO;
import com.model.entity.AllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class AllocationEntityToDTOConverter implements Converter<AllocationEntity, IAllocationDTO> {

    private final OrganisationsEntityToDTOConverter organisationsEntityToDTOConverter;
    private final StudentsEntityToDTOConverter studentsEntityToDTOConverter;
    private final OrdersEntityToDTOConverter ordersEntityToDTOConverter;

    @Autowired
    public AllocationEntityToDTOConverter(OrganisationsEntityToDTOConverter organisationsEntityToDTOConverter,
                                          StudentsEntityToDTOConverter studentsEntityToDTOConverter, OrdersEntityToDTOConverter ordersEntityToDTOConverter) {
        this.organisationsEntityToDTOConverter = organisationsEntityToDTOConverter;
        this.studentsEntityToDTOConverter = studentsEntityToDTOConverter;
        this.ordersEntityToDTOConverter = ordersEntityToDTOConverter;
    }

    @Override
    public IAllocationDTO convert(AllocationEntity source) {
        if (source == null) return null;

        IAllocationDTO target = new AllocationDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setParentId(source.getParentId());

        target.setArmy(source.isArmy());
        target.setFreeAllocation(source.isFreeAllocation());
        target.setArchive(source.isArchive());

        target.setCompensationType(source.getCompensationType());
        target.setCompensationOrderNumber(source.getCompensationOrderNumber());

        target.setCompensationOrderDate(convertDateToLocalDateTime(source.getCompensationOrderDate()));
        target.setVoluntaryCompensationConfirmationDate(convertDateToLocalDateTime(source.getVoluntaryCompensationConfirmationDate()));
        target.setFreeAllocationReason(source.getFreeAllocationReason());

        target.setConfirmations(source.getConfirmations());

        target.setOrganisation(organisationsEntityToDTOConverter.convert(source.getOrganisation()));
        target.setStudent(studentsEntityToDTOConverter.convert(source.getStudent()));
        target.setOrder(ordersEntityToDTOConverter.convert(source.getOrder()));

        return target;
    }
}
