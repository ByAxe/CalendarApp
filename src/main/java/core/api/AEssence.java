package core.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by byaxe on 26.11.16.
 */
public abstract class AEssence implements Serializable, IEssence {
    protected String uuid;
    protected Date dtUpdate;

    public AEssence() {
    }

    public AEssence(IEssence essence) {
        if (essence != null) {
            this.uuid = essence.getUuid();
            this.dtUpdate = essence.getDtUpdate();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
