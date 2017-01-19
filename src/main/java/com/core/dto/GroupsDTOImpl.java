/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto;

import com.core.api.AEssence;
import com.core.dto.api.IGroupsDTO;
import com.core.dto.api.IRulersDTO;
import com.core.enums.Stage;

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

    private Integer issueYear;
    private Integer issueMonth;
    private Stage stage;

    private IRulersDTO ruler;

    public GroupsDTOImpl() {
    }

    @Override
    public String toPrettyString() {
        return number + " " + title + " (" + id + ")";
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

    public Integer getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(Integer issueYear) {
        this.issueYear = issueYear;
    }

    public Integer getIssueMonth() {
        return issueMonth;
    }

    public void setIssueMonth(Integer issueMonth) {
        this.issueMonth = issueMonth;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
