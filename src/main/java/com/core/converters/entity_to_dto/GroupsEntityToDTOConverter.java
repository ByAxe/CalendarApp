/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.GroupsDTOImpl;
import com.core.dto.api.IGroupsDTO;
import com.model.entity.GroupsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 26.11.16.
 */
@Component
public class GroupsEntityToDTOConverter implements Converter<GroupsEntity, IGroupsDTO> {

    private final RulersEntityToDTOConverter rulersEntityToDTOConverter;

    @Autowired
    public GroupsEntityToDTOConverter(RulersEntityToDTOConverter rulersEntityToDTOConverter) {
        this.rulersEntityToDTOConverter = rulersEntityToDTOConverter;
    }

    @Override
    public IGroupsDTO convert(GroupsEntity source) {
        if (source == null) return null;

        IGroupsDTO target = new GroupsDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setTitle(source.getTitle());
        target.setHours(source.getHours());
        target.setSpecialization(source.getSpecialization());
        target.setNumber(source.getNumber());
        target.setDescription(source.getDescription());
        target.setQualification(source.getQualification());

        target.setIssueYear(source.getIssueYear());
        target.setIssueMonth(source.getIssueMonth());
        target.setStage(source.getStage());

        target.setRuler(rulersEntityToDTOConverter.convert(source.getRuler()));

        return target;
    }
}
