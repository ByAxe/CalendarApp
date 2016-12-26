/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.enums;

/**
 * Created by byaxe on 25.12.16.
 */
public enum CompensationType {

    NONE("Не возмещает"),
    VOLUNTARY("Добровольное"),
    CORT("Судебное");

    private final String type;

    CompensationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
