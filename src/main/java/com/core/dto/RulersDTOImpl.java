/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.api.IEssence;
import com.core.dto.api.IRulersDTO;

/**
 * Created by byaxe on 17.12.16.
 */
public class RulersDTOImpl extends AEssence implements IRulersDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String payload;

    public RulersDTOImpl() {
    }

    public RulersDTOImpl(IEssence essence) {
        super(essence);
    }

    public RulersDTOImpl(Long id, String firstName, String middleName, String lastName, String payload) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.payload = payload;
    }


    public RulersDTOImpl(IRulersDTO rulersDTO) {
        if (rulersDTO != null) {
            this.id = rulersDTO.getId();
            this.firstName = rulersDTO.getFirstName();
            this.middleName = rulersDTO.getMiddleName();
            this.lastName = rulersDTO.getLastName();
            this.payload = rulersDTO.getPayload();
        }
    }

    @Override
    public String toPrettyString() {
        return lastName
                + " "
                + firstName
                + " "
                + middleName
                + " (" + id + ")";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
