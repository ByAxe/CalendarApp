/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto.api;

import core.api.IEssence;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrdersDTO extends IEssence {

    String getProfession();

    void setProfession(String profession);

    Integer getRank();

    void setRank(Integer rank);

    LocalDateTime getStarts();

    void setStarts(LocalDateTime starts);

    String getNumber();

    void setNumber(String number);

    String getPayload();

    void setPayload(String payload);
}
