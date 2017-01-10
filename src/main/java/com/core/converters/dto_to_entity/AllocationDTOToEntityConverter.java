/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.dto_to_entity;

import com.core.dto.api.IAllocationDTO;
import com.model.entity.AllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertLocalDateTimeToDate;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class AllocationDTOToEntityConverter implements Converter<IAllocationDTO, AllocationEntity> {

    private final OrganisationsDTOToEntityConverter organisationsDTOToEntityConverter;
    private final StudentsDTOToEntityConverter studentsDTOToEntityConverter;
    private final OrdersDTOToEntityConverter ordersDTOToEntityConverter;

    @Autowired
    public AllocationDTOToEntityConverter(OrganisationsDTOToEntityConverter organisationsDTOToEntityConverter,
                                          StudentsDTOToEntityConverter studentsDTOToEntityConverter, OrdersDTOToEntityConverter ordersDTOToEntityConverter) {
        this.organisationsDTOToEntityConverter = organisationsDTOToEntityConverter;
        this.studentsDTOToEntityConverter = studentsDTOToEntityConverter;
        this.ordersDTOToEntityConverter = ordersDTOToEntityConverter;
    }

    @Override
    public AllocationEntity convert(IAllocationDTO source) {
        if (source == null) return null;

        AllocationEntity target = new AllocationEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setParentId(source.getParentId());

        target.setArmy(source.isArmy());
        target.setFreeAllocation(source.isFreeAllocation());
        target.setArchive(source.isArchive());

        target.setCompensationOrderNumber(source.getCompensationOrderNumber());
        target.setFreeAllocationReason(source.getFreeAllocationReason());

        target.setCompensationType(source.getCompensationType());
        target.setCompensationOrderDate(convertLocalDateTimeToDate(source.getCompensationOrderDate()));
        target.setVoluntaryCompensationConfirmationDate(convertLocalDateTimeToDate(source.getVoluntaryCompensationConfirmationDate()));

        target.setConfirmations(source.getConfirmations());

        target.setOrganisation(organisationsDTOToEntityConverter.convert(source.getOrganisation()));
        target.setStudent(studentsDTOToEntityConverter.convert(source.getStudent()));
        target.setOrder(ordersDTOToEntityConverter.convert(source.getOrder()));

        return target;
    }
}
