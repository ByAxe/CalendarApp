package core.enums;

/**
 * Created by byaxe on 26.11.16.
 */
public enum Frequency {
    DAILY("Ежедневно"),
    WEEKLY("Еженедельно"),
    MONTHLY("Ежемесячно"),
    ANNUALLY("Ежегодно"),
    ONCE("Единожды");

    private String name;

    Frequency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
