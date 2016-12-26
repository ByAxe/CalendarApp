/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.dto.api.IOrganisationsDTO;
import javafx.scene.control.Label;
import model.entity.OrganisationsEntity;

import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrganisationsService extends IEssenceService<IOrganisationsDTO, Long>, IConversionService<IOrganisationsDTO, OrganisationsEntity> {
    void fillOrganisationList(JFXListView<Label> managementOrganisationsList);

    void addContextMenuToOrganisationsList(JFXListView<Label> managementOrganisationsList, JFXTextField managementOrganisationId, JFXTextField managementOrganisationTitle, JFXTextField managementOrganisationAddress, JFXTextField managementOrganisationTelephone, JFXTextField managementOrganisationContacts);

    IOrganisationsDTO findByUuid(UUID uuid);

    void save(Long id, String title, String address, String telephone, String contacts);
}
