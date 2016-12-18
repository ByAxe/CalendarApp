package core.enums;

/**
 * Created by byaxe on 26.11.16.
 */
public enum Priority {
    BASIC("Минимальный"),
    LOW("Низкий"),
    NORMAL("Обычный"),
    HIGH("Высокий"),
    TOP("Максимальный");

    private String name;

    Priority(String name) {
        this.name = name;
    }

    public static Priority getDefault() {
        return NORMAL;
    }

    public String getName() {
        return name;
    }
}
