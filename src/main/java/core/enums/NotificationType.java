/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.enums;

/**
 * Created by byaxe on 19.12.16.
 * <p>
 * Тип уведомления в разделе Отработки (Allocation)
 */
public enum NotificationType {
    /**
     * Подтверждение об отработке
     */
    CONFIRMATION("Подтверждение об отработке"),

    /**
     * Окончание срока отработки
     */
    ALLOCATION_END("Окончание срока отработки"),

    /**
     * Перемещение в архив
     */
    ARCHIVE("Перемещение в архив");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
