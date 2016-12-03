package core.api;

import java.util.Date;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEssence {
    String ID_COLUMN_NAME = "id";
    String UUID_COLUMN_NAME = "uuid";
    String DATE_UPDATE_COLUMN_NAME = "dt_update";

    String getUuid();

    void setUuid(String uuid);

    Date getDtUpdate();

    void setDtUpdate(Date dtUpdate);
}
