/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.enums;

/**
 * Created by byaxe on 17.12.16.
 * <p>
 * Ступень образования
 */
public enum Stage {
    FIRST("Первая ступень", "ПТО", "Профессиональное техническое образование", 1),
    SECOND("Вторая ступень", "ССО", "Среднее специальное образование", 2);

    private String title;
    private String acronym;
    private String description;
    private int amountYearsOfMining;

    Stage(String title, String acronym, String description, int amountYearsOfMining) {
        this.title = title;
        this.acronym = acronym;
        this.description = description;
        this.amountYearsOfMining = amountYearsOfMining;
    }

    public String getTitle() {
        return title;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getDescription() {
        return description;
    }

    public int getAmountYearsOfMining() {
        return amountYearsOfMining;
    }
}
