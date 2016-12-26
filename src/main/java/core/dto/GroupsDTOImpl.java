/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IGroupsDTO;
import core.dto.api.IRulersDTO;

/**
 * Created by byaxe on 26.11.16.
 */
public class GroupsDTOImpl extends AEssence implements IGroupsDTO {

    private String title;
    private String specialization;
    private String qualification;
    private String description;
    private String number;
    private Integer hours;
    private IRulersDTO ruler;

    public GroupsDTOImpl() {
    }

    public GroupsDTOImpl(IEssence essence) {
        super(essence);
    }

    public GroupsDTOImpl(IGroupsDTO essence) {
        if (essence != null) {
            this.id = essence.getId();
            this.title = essence.getTitle();
            this.specialization = essence.getSpecialization();
            this.qualification = essence.getQualification();
            this.description = essence.getDescription();
            this.number = essence.getNumber();
            this.hours = essence.getHours();
            this.ruler = essence.getRuler();
        }
    }

    public GroupsDTOImpl(Long id, String title, String specialization, String qualification,
                         String description, String number, Integer hours, IRulersDTO ruler) {
        this.id = id;
        this.title = title;
        this.specialization = specialization;
        this.qualification = qualification;
        this.description = description;
        this.number = number;
        this.hours = hours;
        this.ruler = ruler;
    }

    @Override
    public String toPrettyString() {
        return number + " " + title + " (" + id + ") " + "часов: " + hours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Override
    public IRulersDTO getRuler() {
        return ruler;
    }

    @Override
    public void setRuler(IRulersDTO ruler) {
        this.ruler = ruler;
    }
}
