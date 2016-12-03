package model.api;

import core.api.IEssence;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by byaxe on 26.11.16.
 */
@MappedSuperclass
public abstract class APersistentEntity extends APersistent implements IEssence {
    protected String uuid;
    protected Date dtUpdate;

    public APersistentEntity() {
    }

    public APersistentEntity(IEssence essence) {
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();
    }

    @Override
    @Column(name = UUID_COLUMN_NAME, length = 128, nullable = false)
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = DATE_UPDATE_COLUMN_NAME, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public Date getDtUpdate() {
        return dtUpdate;
    }

    @Override
    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
