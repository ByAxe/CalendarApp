/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.converters.entity_to_dto;

import core.dto.AllocationDTOImpl;
import core.dto.api.IAllocationDTO;
import model.entity.AllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class AllocationEntityToDTOConverter implements Converter<AllocationEntity, IAllocationDTO> {

    private final GroupsEntityToDTOConverter groupsEntityToDTOConverter;
    private final OrganisationsEntityToDTOConverter organisationsEntityToDTOConverter;
    private final StudentsEntityToDTOConverter studentsEntityToDTOConverter;
    private final OrdersEntityToDTOConverter ordersEntityToDTOConverter;

    @Autowired
    public AllocationEntityToDTOConverter(GroupsEntityToDTOConverter groupsEntityToDTOConverter, OrganisationsEntityToDTOConverter organisationsEntityToDTOConverter,
                                          StudentsEntityToDTOConverter studentsEntityToDTOConverter, OrdersEntityToDTOConverter ordersEntityToDTOConverter) {
        this.groupsEntityToDTOConverter = groupsEntityToDTOConverter;
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

        target.setParentUuid(source.getParentUuid());

        target.setArmy(source.isArmy());
        target.setFreeAllocation(source.isFreeAllocation());
        target.setVoluntaryCompensation(source.isVoluntaryCompensation());
        target.setArchive(source.isArchive());

        target.setStage(source.getStage());
        target.setCortOrderNumber(source.getCortOrderNumber());
        target.setVoluntaryCompensationOrderNumber(source.getVoluntaryCompensationOrderNumber());

        target.setVoluntaryCompensationOrderDate(convertDateToLocalDateTime(source.getVoluntaryCompensationOrderDate()));
        target.setVoluntaryCompensationConfirmationDate(convertDateToLocalDateTime(source.getVoluntaryCompensationConfirmationDate()));
        target.setCortOrderDate(convertDateToLocalDateTime(source.getCortOrderDate()));

        target.setConfirmations(source.getConfirmations());

        target.setGroup(groupsEntityToDTOConverter.convert(source.getGroup()));
        target.setOrganisation(organisationsEntityToDTOConverter.convert(source.getOrganisation()));
        target.setStudent(studentsEntityToDTOConverter.convert(source.getStudent()));
        target.setOrder(ordersEntityToDTOConverter.convert(source.getOrder()));

        return target;
    }
}
