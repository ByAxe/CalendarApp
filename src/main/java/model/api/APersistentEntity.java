package model.api;

import core.api.IEssence;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
@MappedSuperclass
public abstract class APersistentEntity extends APersistent implements IEssence {
    protected UUID uuid;
    protected Date dtUpdate;

    public APersistentEntity() {
    }

    public APersistentEntity(IEssence essence) {
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();
    }

    @Override
    @Column(name = UUID_COLUMN_NAME, nullable = false)
    @Type(type = "pg-uuid")
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
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
