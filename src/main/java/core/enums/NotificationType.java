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
    CONFIRMATION,

    /**
     * Окончание срока отработки
     */
    ALLOCATION_END,

    /**
     * Перемещение в архив
     */
    ARCHIVE
}
