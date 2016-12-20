package service.quartz.jobs;

import core.enums.NotificationType;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import service.api.INotificationsLogService;

/**
 * Created by byaxe on 19.12.16.
 * <p>
 * Сохранияет уведомление по Истечению срока отработки
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AllocationNotificationJob implements Job {

    @Autowired
    private INotificationsLogService notificationsLogService;

    private String notificationAllocationType;
    private String notificationAllocationPayload;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        notificationsLogService.addNotification(NotificationType.valueOf(notificationAllocationType), notificationAllocationPayload);
    }

    public INotificationsLogService getNotificationsLogService() {
        return notificationsLogService;
    }

    public void setNotificationsLogService(INotificationsLogService notificationsLogService) {
        this.notificationsLogService = notificationsLogService;
    }

    public String getNotificationAllocationType() {
        return notificationAllocationType;
    }

    public void setNotificationAllocationType(String notificationAllocationType) {
        this.notificationAllocationType = notificationAllocationType;
    }

    public String getNotificationAllocationPayload() {
        return notificationAllocationPayload;
    }

    public void setNotificationAllocationPayload(String notificationAllocationPayload) {
        this.notificationAllocationPayload = notificationAllocationPayload;
    }
}
