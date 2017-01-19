/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

import com.core.api.IEssence;
import com.core.api.IVisible;
import com.core.enums.Stage;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IGroupsDTO extends IEssence, IVisible {

    String getTitle();

    void setTitle(String title);

    String getSpecialization();

    void setSpecialization(String specialization);

    String getQualification();

    void setQualification(String qualification);

    String getDescription();

    void setDescription(String description);

    String getNumber();

    void setNumber(String number);

    Integer getHours();

    void setHours(Integer hours);

    Integer getIssueYear();

    void setIssueYear(Integer issueYear);

    Integer getIssueMonth();

    void setIssueMonth(Integer issueMonth);

    Stage getStage();

    void setStage(Stage stage);

    IRulersDTO getRuler();

    void setRuler(IRulersDTO rulersList);
}
