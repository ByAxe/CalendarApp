/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IPreferencesDTO;

/**
 * Created by byaxe on 20.12.16.
 */
public class PreferencesDTOImpl extends AEssence implements IPreferencesDTO {

    private boolean notificationsEnabled;
    private boolean archiveEnabled;
    private Integer archiveTerm;
    private Integer confirmationNoticeTerm;
    private Integer allocationEndNoticeTerm;

    public PreferencesDTOImpl() {
    }

    public PreferencesDTOImpl(IEssence essence) {
        super(essence);
    }

    public PreferencesDTOImpl(IPreferencesDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();
        this.notificationsEnabled = essence.isNotificationsEnabled();
        this.archiveTerm = essence.getArchiveTerm();
        this.confirmationNoticeTerm = essence.getConfirmationNoticeTerm();
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean isArchiveEnabled() {
        return archiveEnabled;
    }

    public void setArchiveEnabled(boolean archiveEnabled) {
        this.archiveEnabled = archiveEnabled;
    }

    public Integer getArchiveTerm() {
        return archiveTerm;
    }

    public void setArchiveTerm(Integer archiveTerm) {
        this.archiveTerm = archiveTerm;
    }

    public Integer getConfirmationNoticeTerm() {
        return confirmationNoticeTerm;
    }

    public void setConfirmationNoticeTerm(Integer confirmationNoticeTerm) {
        this.confirmationNoticeTerm = confirmationNoticeTerm;
    }

    @Override
    public Integer getAllocationEndNoticeTerm() {
        return allocationEndNoticeTerm;
    }

    @Override
    public void setAllocationEndNoticeTerm(Integer allocationEndNoticeTerm) {
        this.allocationEndNoticeTerm = allocationEndNoticeTerm;
    }
}
