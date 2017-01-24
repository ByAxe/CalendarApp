/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.dto.api;

import core.enums.Stage;

/**
 * Created by byaxe on 24.12.16.
 */
public interface IAllocationTableDTO {

    String getId();

    void setId(String id);

    String getStudentFirstName();

    void setStudentFirstName(String studentFirstName);

    String getStudentLastName();

    void setStudentLastName(String studentLastName);

    String getStudentMiddleName();

    void setStudentMiddleName(String studentMiddleName);

    String getStudentAddress();

    void setStudentAddress(String studentAddress);

    String getStudentTelephone();

    void setStudentTelephone(String studentTelephone);

    String getGroupNumber();

    void setGroupNumber(String groupNumber);

    String getOrganisationTitle();

    void setOrganisationTitle(String organisationTitle);

    String getOrganisationAddress();

    void setOrganisationAddress(String organisationAddress);

    String getOrganisationTelephone();

    void setOrganisationTelephone(String organisationTelephone);

    String getOrganisationContacts();

    void setOrganisationContacts(String organisationContacts);

    String getOrderDate();

    void setOrderDate(String orderDate);

    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    String getOrderProfession();

    void setOrderProfession(String orderProfession);

    String getOrderRank();

    void setOrderRank(String orderRank);

    String getConfirmations_1();

    void setConfirmations_1(String confirmations_1);

    String getConfirmations_2();

    void setConfirmations_2(String confirmations_2);

    String getConfirmations_3();

    void setConfirmations_3(String confirmations_3);

    String getConfirmations_4();

    void setConfirmations_4(String confirmations_4);

    String getConfirmations_5();

    void setConfirmations_5(String confirmations_5);

    String getConfirmations_6();

    void setConfirmations_6(String confirmations_6);

    String getConfirmations_7();

    void setConfirmations_7(String confirmations_7);

    String getConfirmations_8();

    void setConfirmations_8(String confirmations_8);

    String getConfirmations_9();

    void setConfirmations_9(String confirmations_9);

    String getConfirmations_10();

    void setConfirmations_10(String confirmations_10);

    String getArmy();

    void setArmy(String army);

    String getFreeAllocationFlag();

    void setFreeAllocationFlag(String freeAllocationFlag);

    String getFreeAllocationReason();

    void setFreeAllocationReason(String freeAllocationReason);

    String getReallocationFlag();

    void setReallocationFlag(String reallocationFlag);

    String getReallocationId();

    void setReallocationId(String reallocationId);

    String getCompensationOrderDate();

    void setCompensationOrderDate(String compensationOrderDate);

    String getCompensationOrderNumber();

    void setCompensationOrderNumber(String compensationOrderNumber);

    String getVoluntaryCompensationConfirmationDate();

    void setVoluntaryCompensationConfirmationDate(String voluntaryCompensationConfirmationDate);

    Stage getStage();

    void setStage(Stage stage);

    String getIssueDate();

    void setIssueDate(String issueDate);

    String getCompensationType();

    void setCompensationType(String compensationType);
}
