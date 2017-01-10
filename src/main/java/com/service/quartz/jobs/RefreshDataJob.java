/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service.quartz.jobs;

import com.controller.ApplicationController;
import javafx.application.Platform;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by byaxe on 26.12.16.
 * <p>
 * Обновляет все данные по всем комбобоксам
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class RefreshDataJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(RefreshDataJob.class);

    @Autowired
    private ApplicationController applicationController;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Platform.runLater(() -> applicationController.refresh());
    }
}
