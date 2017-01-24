/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.dto.api.IRulersDTO;
import javafx.scene.control.Label;
import model.entity.RulersEntity;

import java.util.UUID;

/**
 * Created by byaxe on 17.12.16.
 */
public interface IRulersService extends IEssenceService<IRulersDTO, Long>, IConversionService<IRulersDTO, RulersEntity> {

    RulersEntity getActualEntity(Long id);

    void addContextMenuToRulersList(JFXListView<Label> managementRulersList, JFXTextField managementRulerId, JFXTextField managementRulerFirstName, JFXTextField managementRulerMiddleName, JFXTextField managementRulerLastName, JFXTextField managementRulerPayload);

    void fillRulersList(JFXListView<Label> managementRulersList);

    void save(Long id, String firstName, String middleName, String lastName, String payload);

    IRulersDTO findByUuid(UUID uuid);
}
