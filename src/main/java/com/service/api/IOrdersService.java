/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.api;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.core.dto.api.IOrdersDTO;
import javafx.scene.control.Label;
import jfxtras.scene.control.LocalDateTextField;
import com.model.entity.OrdersEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrdersService extends IEssenceService<IOrdersDTO, Long>, IConversionService<IOrdersDTO, OrdersEntity> {
    void fillOrdersList(JFXListView<Label> managementOrdersList);


    void addContextMenuToStudentsList(JFXListView<Label> ordersList, JFXTextField id,
                                      LocalDateTextField starts, JFXTextField number,
                                      JFXTextField profession, JFXTextArea payload);

    IOrdersDTO findByUuid(UUID uuid);

    void save(Long id, LocalDate starts, String profession, String number, String payload);
}
