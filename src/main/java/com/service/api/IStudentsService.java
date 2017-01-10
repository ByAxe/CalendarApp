/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.api;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.core.dto.api.IStudentsDTO;
import javafx.scene.control.Label;
import com.model.entity.StudentsEntity;

import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IStudentsService extends IEssenceService<IStudentsDTO, Long>, IConversionService<IStudentsDTO, StudentsEntity> {
    void save(long id, String firstName, String middleName, String lastName, String telephone, String address, String group);

    void fillStudentsList(JFXListView<Label> allocationStudentsList);

    void addContextMenuToStudentsList(JFXListView<Label> managementStudentsList, JFXTextField managementStudentId,
                                      JFXTextField managementStudentFirstName, JFXTextField managementStudentMiddleName,
                                      JFXTextField managementStudentLastName, JFXTextField managementStudentTelephone,
                                      JFXTextField managementStudentAddress, JFXComboBox groups);

    IStudentsDTO findByUuid(UUID uuid);
}
