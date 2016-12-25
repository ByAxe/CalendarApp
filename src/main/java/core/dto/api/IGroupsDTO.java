/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto.api;

import core.api.IEssence;
import core.api.IVisible;

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

    IRulersDTO getRuler();

    void setRuler(IRulersDTO rulersList);
}
