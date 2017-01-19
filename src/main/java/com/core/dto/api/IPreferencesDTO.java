/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.dto.api;

import com.core.api.IEssence;

/**
 * Created by byaxe on 20.12.16.
 */
public interface IPreferencesDTO extends IEssence {

    boolean isNotificationsEnabled();

    void setNotificationsEnabled(boolean notificationsEnabled);

    boolean isArchiveEnabled();

    void setArchiveEnabled(boolean archiveEnabled);

    Integer getArchiveTerm();

    void setArchiveTerm(Integer archiveTerm);

    Integer getConfirmationNoticeTerm();

    void setConfirmationNoticeTerm(Integer confirmationNoticeTerm);

    Integer getAllocationEndNoticeTerm();

    void setAllocationEndNoticeTerm(Integer allocationEndNoticeTerm);
}
