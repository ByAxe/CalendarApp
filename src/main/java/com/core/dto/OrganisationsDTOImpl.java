/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.api.IEssence;
import com.core.dto.api.IOrganisationsDTO;

/**
 * Created by byaxe on 18.12.16.
 */
public class OrganisationsDTOImpl extends AEssence implements IOrganisationsDTO {

    private String title;
    private String address;
    private String telephone;
    private String contacts;

    public OrganisationsDTOImpl() {
    }

    public OrganisationsDTOImpl(IEssence essence) {
        super(essence);
    }

    public OrganisationsDTOImpl(Long id, String title, String address, String telephone, String contacts) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.telephone = telephone;
        this.contacts = contacts;
    }

    public OrganisationsDTOImpl(IOrganisationsDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();
        this.title = essence.getTitle();
        this.address = essence.getAddress();
        this.telephone = essence.getTelephone();
        this.contacts = essence.getContacts();
    }

    @Override
    public String toPrettyString() {
        return title + "(" + id + ")";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
