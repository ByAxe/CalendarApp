/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.enums;

/**
 * Created by byaxe on 26.11.16.
 */
public enum Frequency {
    ONCE("Не повторяется"),
    DAILY("Ежедневно"),
    WEEKLY("Еженедельно"),
    MONTHLY("Ежемесячно");

    private String name;

    Frequency(String name) {
        this.name = name;
    }

    public static Frequency getDefault() {
        return ONCE;
    }

    public String getName() {
        return name;
    }

}
