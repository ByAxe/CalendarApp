package core.dto.api;

import core.api.IEssence;

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
