package core.enums;

/**
 * Created by byaxe on 17.12.16.
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
