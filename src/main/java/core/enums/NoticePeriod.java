/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.enums;

/**
 * Created by byaxe on 26.11.16.
 * <p>
 * Напомнить за определенное время до начала события
 */
public enum NoticePeriod {
    NONE("Никогда", 0),
    FIVE_MINUTES("За 5 минут", 300_000),
    TEN_MINUTES("За 10 минут", 600_000),
    FIFTEEN_MINUTES("За 15 минут", 900_000),
    THIRTY_MINUTES("За 30 минут", 1_800_000),
    ONE_HOUR("За 1 час", 3_600_000),
    TWO_HOURS("За 2 часа", 7_200_000),
    FIVE_HOURS("За 5 часов", 18_000_000),
    DAY("За 1 день", 86_400_000),
    TWO_DAYS("За 2 дня", 172_800_000),
    THREE_DAYS("За 3 дня", 259_200_000),
    FIVE_DAYS("За 5 дней", 432_000_000),
    WEEK("За 1 неделю", 604_800_000);

    private String name;
    private long milliseconds;

    NoticePeriod(String name, long milliseconds) {
        this.name = name;
        this.milliseconds = milliseconds;
    }

    public static NoticePeriod getDefault() {
        return FIFTEEN_MINUTES;
    }

    public String getName() {
        return name;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
