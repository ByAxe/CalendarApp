package core.enums;

/**
 * Created by byaxe on 26.11.16.
 * <p>
 * Напомнить за определенное время до начала события
 */
public enum NoticePeriod {
    NONE("Никогда"),
    FIVE_MINUTES("За 5 минут"),
    TEN_MINUTES("За 10 минут"),
    FIFTEEN_MINUTES("За 15 минут"),
    THIRTY_MINUTES("За 30 минут"),
    ONE_HOUR("За 1 час"),
    TWO_HOURS("За 2 часа"),
    FIVE_HOURS("За 5 часов"),
    DAY("За 1 день"),
    TWO_DAYS("За 2 дня"),
    THREE_DAYS("За 3 дня"),
    FIVE_DAYS("За 5 дней"),
    WEEK("За 1 неделю");

    private String name;

    NoticePeriod(String name) {
        this.name = name;
    }

    public static NoticePeriod getDefault() {
        return FIFTEEN_MINUTES;
    }

    public String getName() {
        return name;
    }
}
