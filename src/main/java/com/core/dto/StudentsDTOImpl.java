/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.api.IEssence;
import com.core.dto.api.IGroupsDTO;
import com.core.dto.api.IStudentsDTO;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Created by byaxe on 18.12.16.
 */
public class StudentsDTOImpl extends AEssence implements IStudentsDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String telephone;
    private String address;
    private IGroupsDTO group;

    public StudentsDTOImpl() {
    }

    public StudentsDTOImpl(IEssence essence) {
        super(essence);
    }

    public StudentsDTOImpl(Long id, String firstName, String middleName, String lastName, String telephone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
    }

    public StudentsDTOImpl(IStudentsDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();

        this.firstName = essence.getFirstName();
        this.middleName = essence.getMiddleName();
        this.lastName = essence.getLastName();
        this.telephone = essence.getTelephone();
        this.address = essence.getAddress();
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getCredentials() {
        return Stream.of(firstName, middleName, lastName).collect(joining(" "));
    }

    public IGroupsDTO getGroup() {
        return group;
    }

    public void setGroup(IGroupsDTO group) {
        this.group = group;
    }
}
