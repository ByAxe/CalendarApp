/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto;

import core.dto.api.IAllocationTableDTO;
import core.enums.Stage;

/**
 * Created by byaxe on 24.12.16.
 * <p>
 * Сущность для хранения отображаемых в таблице распределения данных
 */
public class AllocationTableDTOImpl implements IAllocationTableDTO {

    private String id;
    private String studentFirstName;
    private String studentMiddleName;
    private String studentLastName;
    private String studentAddress;
    private String studentTelephone;
    private String groupNumber;

    private String organisationTitle;
    private String organisationAddress;
    private String organisationTelephone;
    private String organisationContacts;

    private String orderDate;
    private String orderNumber;
    private String orderProfession;
    private String orderRank;

    private String confirmations_1;
    private String confirmations_2;
    private String confirmations_3;
    private String confirmations_4;
    private String confirmations_5;
    private String confirmations_6;
    private String confirmations_7;
    private String confirmations_8;
    private String confirmations_9;
    private String confirmations_10;

    private String army;

    private String freeAllocationFlag;
    private String freeAllocationReason;

    private String reallocationFlag;
    private String reallocationId;

    private String compensationType;
    private String compensationOrderDate;
    private String compensationOrderNumber;
    private String voluntaryCompensationConfirmationDate;

    private Stage stage;
    private String issueDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentMiddleName() {
        return studentMiddleName;
    }

    public void setStudentMiddleName(String studentMiddleName) {
        this.studentMiddleName = studentMiddleName;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentTelephone() {
        return studentTelephone;
    }

    public void setStudentTelephone(String studentTelephone) {
        this.studentTelephone = studentTelephone;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getOrganisationTitle() {
        return organisationTitle;
    }

    public void setOrganisationTitle(String organisationTitle) {
        this.organisationTitle = organisationTitle;
    }

    public String getOrganisationAddress() {
        return organisationAddress;
    }

    public void setOrganisationAddress(String organisationAddress) {
        this.organisationAddress = organisationAddress;
    }

    public String getOrganisationTelephone() {
        return organisationTelephone;
    }

    public void setOrganisationTelephone(String organisationTelephone) {
        this.organisationTelephone = organisationTelephone;
    }

    public String getOrganisationContacts() {
        return organisationContacts;
    }

    public void setOrganisationContacts(String organisationContacts) {
        this.organisationContacts = organisationContacts;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderProfession() {
        return orderProfession;
    }

    public void setOrderProfession(String orderProfession) {
        this.orderProfession = orderProfession;
    }

    public String getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(String orderRank) {
        this.orderRank = orderRank;
    }

    public String getConfirmations_1() {
        return confirmations_1;
    }

    public void setConfirmations_1(String confirmations_1) {
        this.confirmations_1 = confirmations_1;
    }

    public String getConfirmations_2() {
        return confirmations_2;
    }

    public void setConfirmations_2(String confirmations_2) {
        this.confirmations_2 = confirmations_2;
    }

    public String getConfirmations_3() {
        return confirmations_3;
    }

    public void setConfirmations_3(String confirmations_3) {
        this.confirmations_3 = confirmations_3;
    }

    public String getConfirmations_4() {
        return confirmations_4;
    }

    public void setConfirmations_4(String confirmations_4) {
        this.confirmations_4 = confirmations_4;
    }

    public String getConfirmations_5() {
        return confirmations_5;
    }

    public void setConfirmations_5(String confirmations_5) {
        this.confirmations_5 = confirmations_5;
    }

    public String getConfirmations_6() {
        return confirmations_6;
    }

    public void setConfirmations_6(String confirmations_6) {
        this.confirmations_6 = confirmations_6;
    }

    public String getConfirmations_7() {
        return confirmations_7;
    }

    public void setConfirmations_7(String confirmations_7) {
        this.confirmations_7 = confirmations_7;
    }

    public String getConfirmations_8() {
        return confirmations_8;
    }

    public void setConfirmations_8(String confirmations_8) {
        this.confirmations_8 = confirmations_8;
    }

    public String getConfirmations_9() {
        return confirmations_9;
    }

    public void setConfirmations_9(String confirmations_9) {
        this.confirmations_9 = confirmations_9;
    }

    public String getConfirmations_10() {
        return confirmations_10;
    }

    public void setConfirmations_10(String confirmations_10) {
        this.confirmations_10 = confirmations_10;
    }

    public String getArmy() {
        return army;
    }

    public void setArmy(String army) {
        this.army = army;
    }

    public String getFreeAllocationFlag() {
        return freeAllocationFlag;
    }

    public void setFreeAllocationFlag(String freeAllocationFlag) {
        this.freeAllocationFlag = freeAllocationFlag;
    }

    public String getFreeAllocationReason() {
        return freeAllocationReason;
    }

    public void setFreeAllocationReason(String freeAllocationReason) {
        this.freeAllocationReason = freeAllocationReason;
    }

    public String getReallocationFlag() {
        return reallocationFlag;
    }

    public void setReallocationFlag(String reallocationFlag) {
        this.reallocationFlag = reallocationFlag;
    }

    public String getReallocationId() {
        return reallocationId;
    }

    public void setReallocationId(String reallocationId) {
        this.reallocationId = reallocationId;
    }

    public String getCompensationOrderDate() {
        return compensationOrderDate;
    }

    public void setCompensationOrderDate(String compensationOrderDate) {
        this.compensationOrderDate = compensationOrderDate;
    }

    public String getCompensationOrderNumber() {
        return compensationOrderNumber;
    }

    public void setCompensationOrderNumber(String compensationOrderNumber) {
        this.compensationOrderNumber = compensationOrderNumber;
    }

    public String getVoluntaryCompensationConfirmationDate() {
        return voluntaryCompensationConfirmationDate;
    }

    public void setVoluntaryCompensationConfirmationDate(String voluntaryCompensationConfirmationDate) {
        this.voluntaryCompensationConfirmationDate = voluntaryCompensationConfirmationDate;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(String compensationType) {
        this.compensationType = compensationType;
    }
}
