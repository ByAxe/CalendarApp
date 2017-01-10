/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.converters.entity_to_dto;

import com.core.dto.OrdersDTOImpl;
import com.core.dto.api.IOrdersDTO;
import com.model.entity.OrdersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.core.commons.Utils.convertDateToLocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class OrdersEntityToDTOConverter implements Converter<OrdersEntity, IOrdersDTO> {

    @Override
    public IOrdersDTO convert(OrdersEntity source) {
        if (source == null) return null;

        IOrdersDTO target = new OrdersDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setProfession(source.getProfession());
        target.setPayload(source.getPayload());
        target.setNumber(source.getNumber());
        target.setRank(source.getRank());
        target.setStarts(convertDateToLocalDateTime(source.getStarts()));

        return target;
    }
}
