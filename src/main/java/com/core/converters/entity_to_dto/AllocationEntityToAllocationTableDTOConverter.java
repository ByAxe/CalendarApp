/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.AllocationTableDTOImpl;
import com.core.dto.api.IAllocationTableDTO;
import com.core.enums.Stage;
import com.model.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.core.commons.Utils.*;

/**
 * Created by byaxe on 24.12.16.
 */
@Component
public class AllocationEntityToAllocationTableDTOConverter implements Converter<AllocationEntity, IAllocationTableDTO> {

    @Value("${army.true.message}")
    private String ARMY_TRUE;

    @Override
    public IAllocationTableDTO convert(AllocationEntity source) {
        if (source == null) return null;

        StudentsEntity student = source.getStudent();
        GroupsEntity group = student.getGroup();
        OrganisationsEntity organisation = source.getOrganisation();
        OrdersEntity order = source.getOrder();

        String[] c = source.getConfirmations();

        IAllocationTableDTO target = new AllocationTableDTOImpl();

        // Устанавливаем Идентификатор записи
        target.setId(String.valueOf(source.getId()));

        // Заполняем колонки по Выпускнику
        {
            target.setStudentFirstName(student.getFirstName());
            target.setStudentMiddleName(student.getMiddleName());
            target.setStudentLastName(student.getLastName());
            target.setStudentAddress(student.getAddress());
            target.setStudentTelephone(student.getTelephone());
            target.setGroupNumber(group.getNumber());
        }

        // Заполняем колонки по Организации
        {
            target.setOrganisationTitle(organisation.getTitle());
            target.setOrganisationAddress(organisation.getAddress());
            target.setOrganisationTelephone(organisation.getTelephone());
            target.setOrganisationContacts(organisation.getContacts());
        }

        // Заполняем колонки по Приказу о приеме на работу
        {
            target.setOrderDate(convertDateToLocalDateTime(order.getStarts()).format(ALLOCATION_TABLE_FORMATTER));
            target.setOrderNumber(order.getNumber());
            target.setOrderProfession(order.getProfession());
            target.setOrderRank(String.valueOf(order.getRank()));
        }

        // Заполняем подтверждения. Выгядит как ужасный костыль...
        {
            target.setConfirmations_1(c[0]);
            target.setConfirmations_2(c[1]);
            target.setConfirmations_3(c[2]);
            target.setConfirmations_4(c[3]);
            target.setConfirmations_5(c[4]);

            if (group.getStage() == Stage.SECOND) {
                target.setConfirmations_6(c[5]);
                target.setConfirmations_7(c[6]);
                target.setConfirmations_8(c[7]);
                target.setConfirmations_9(c[8]);
                target.setConfirmations_10(c[9]);
            }
        }

        target.setReallocationId(String.valueOf(source.getParentId()));

        // Остальные поля
        {
            target.setArmy(source.isArmy() ? ARMY_TRUE : null);
            target.setFreeAllocationFlag(source.isFreeAllocation() ? "Да" : "Нет");
            target.setFreeAllocationReason(source.getFreeAllocationReason());

            target.setCompensationType(source.getCompensationType().name());

            Optional.ofNullable(source.getCompensationOrderDate()).ifPresent(vCOD ->
                    target.setCompensationOrderDate(convertDateToLocalDateTime(vCOD)
                            .format(ALLOCATION_TABLE_FORMATTER)));

            target.setCompensationOrderNumber(source.getCompensationOrderNumber());

            Optional.ofNullable(source.getVoluntaryCompensationConfirmationDate()).ifPresent(e ->
                    target.setVoluntaryCompensationConfirmationDate(convertDateToLocalDateTime(e)
                            .format(ALLOCATION_TABLE_FORMATTER)));

            target.setIssueDate(LocalDateTime.of(group.getIssueYear(), group.getIssueMonth(), 1, 0, 0).format(ISSUE_DATE_FORMATTER));
            target.setStage(group.getStage());
        }

        return target;
    }
}
