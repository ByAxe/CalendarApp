/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import com.jfoenix.controls.JFXComboBox;
import core.dto.api.IAllocationDTO;
import core.enums.Stage;
import model.entity.AllocationEntity;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IAllocationService extends IEssenceService<IAllocationDTO, Long>,
        IConversionService<IAllocationDTO, AllocationEntity> {

    <T> List<T> findByArchiveTrue();

    <T> List<T> findByArchiveFalse();

    <T> List<T> find(Stage stage, Boolean archive, Integer issueYear);

    void moveToArchive(Long id);

    void fillComboBox(JFXComboBox allocationTableStageComboBox);

    void setDefaultValues(JFXComboBox allocationTableStageComboBox);
}
