package core.enums;

/**
 * Created by byaxe on 17.12.16.
 */
public enum Stage {
    FIRST("Первая ступень", "ПТО", "Профессиональное техническое образование"),
    SECOND("Вторая ступень", "ССО", "Среднее специальное образование");

    private String title;
    private String acronym;
    private String description;

    Stage(String title, String acronym, String description) {
        this.title = title;
        this.acronym = acronym;
        this.description = description;
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
}
