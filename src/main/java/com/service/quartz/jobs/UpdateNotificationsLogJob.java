/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.quartz.jobs;

import com.controller.ApplicationController;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.api.INotificationsLogService;

/**
 * Created by byaxe on 24.12.16.
 * <p>
 * Если появлись новые уведомления - отмечаем цифрой на вкладке их количество
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class UpdateNotificationsLogJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(UpdateNotificationsLogJob.class);

    @Autowired
    private ApplicationController applicationController;

    @Autowired
    private INotificationsLogService notificationsLogService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final long count = notificationsLogService.count();

        if (count == 0) return;

        Tab notificationsTab = applicationController.notificationsTab;
        Platform.runLater(() -> notificationsTab.setText("Уведомления (" + count + ")"));
    }
}
