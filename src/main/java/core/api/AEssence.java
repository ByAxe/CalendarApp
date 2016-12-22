/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.api;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
public abstract class AEssence implements Serializable, IEssence {
    protected Long id;
    protected UUID uuid;
    protected Date dtUpdate;

    public AEssence() {
    }

    public AEssence(IEssence essence) {
        if (essence != null) {
            this.uuid = essence.getUuid();
            this.dtUpdate = essence.getDtUpdate();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
