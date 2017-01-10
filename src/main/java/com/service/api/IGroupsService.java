/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.api;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.core.dto.api.IGroupsDTO;
import javafx.scene.control.Label;
import jfxtras.scene.control.LocalDateTextField;
import com.model.entity.GroupsEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IGroupsService extends IEssenceService<IGroupsDTO, Long>, IConversionService<IGroupsDTO, GroupsEntity> {
    void fillGroupsList(JFXListView<Label> managementGroupsList);

    void addContextMenuToGroupsList(JFXListView<Label> managementGroupsList, JFXTextField managementGroupId,
                                    JFXTextField managementGroupTitle, JFXTextField managementGroupQualification,
                                    JFXTextField managementGroupNumber, JFXTextField managementGroupSpecialization,
                                    JFXTextField managementGroupDescription, JFXTextField hours,
                                    JFXComboBox managementGroupRuler, LocalDateTextField managementGroupIssueDate, JFXComboBox managementGroupStage);

    IGroupsDTO findByUuid(UUID uuid);

    void save(Long id, String title, String qualification, String number, String specialisation, String description,
              String hours, LocalDate issueDate, String stage, String ruler);
}
