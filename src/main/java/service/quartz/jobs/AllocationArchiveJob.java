/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.quartz.jobs;

import core.enums.NotificationType;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import service.api.IAllocationService;
import service.api.INotificationsLogService;

/**
 * Created by byaxe on 20.12.16.
 * <p>
 * Перемещает записи в архив по истечении архивного периода и создает уведомление об этом пользователю
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AllocationArchiveJob implements Job {

    @Autowired
    private INotificationsLogService notificationsLogService;

    @Autowired
    private IAllocationService allocationService;

    private String notificationAllocationId;
    private String notificationAllocationType;
    private String notificationAllocationPayload;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Перемещаем запись в архив
        allocationService.moveToArchive(Long.valueOf(notificationAllocationId));

        //Создаем уведомление
        notificationsLogService.addNotification(NotificationType.valueOf(notificationAllocationType), notificationAllocationPayload);
    }

    public String getNotificationAllocationId() {
        return notificationAllocationId;
    }

    public void setNotificationAllocationId(String notificationAllocationId) {
        this.notificationAllocationId = notificationAllocationId;
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
