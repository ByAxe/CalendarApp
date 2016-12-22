/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package model.api;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by byaxe on 26.11.16.
 */
@MappedSuperclass
public abstract class APersistent implements Serializable {

//    @Id
//    @Column(insertable = false, updatable = false)
//    public abstract Long getId();
//
//    public abstract void setId(Long id);
}
