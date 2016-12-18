package core.enums;

/**
 * Created by byaxe on 26.11.16.
 */
public enum Frequency {
    ONCE("Не повторяется"),
    DAILY("Ежедневно"),
    WEEKLY("Еженедельно"),
    MONTHLY("Ежемесячно"),
    ANNUALLY("Ежегодно");

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
