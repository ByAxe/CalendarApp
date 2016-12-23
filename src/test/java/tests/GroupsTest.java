/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package tests;

import config.BasicDBTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import service.api.IEventsService;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * Created by byaxe on 04.12.16.
 */
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {
                "sql/groups_data.sql"
        }),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {
                "sql/ddl/clear.sql"
        }),
})
public class GroupsTest extends BasicDBTest {
    private final IEventsService eventsService;

    @Autowired
    public GroupsTest(IEventsService eventsService) {
        this.eventsService = eventsService;
    }
}
