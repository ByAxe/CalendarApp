package config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;
import java.lang.reflect.Method;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * Created by byaxe on 04.12.16.
 */
@TestExecutionListeners(value = BasicDBTest.DBTestExecutionListener.class)
public abstract class BasicDBTest extends BasicTest {

    public static class DBTestExecutionListener extends AbstractTestExecutionListener {

        /**
         * Метод, выполняющийся перед запуском тестового метода: предустановка
         */
        public void beforeTestMethod(TestContext testContext) throws Exception {
            Method method = testContext.getTestMethod();
            executeCommand(testContext, method.getAnnotation(SqlGroup.class), BEFORE_TEST_METHOD);
        }

        public void beforeTestClass(TestContext testContext) throws Exception {
            Class clazz = testContext.getTestClass();
            executeCommand(testContext, (SqlGroup) clazz.getAnnotation(SqlGroup.class), BEFORE_TEST_METHOD);
        }

        /**
         * Метод, выполняющийся после запуском тестового метода: очистка
         */
        public void afterTestMethod(TestContext testContext) throws Exception {
            Method method = testContext.getTestMethod();
            executeCommand(testContext, method.getAnnotation(SqlGroup.class), AFTER_TEST_METHOD);
        }

        public void afterTestClass(TestContext testContext) throws Exception {
            Class clazz = testContext.getTestClass();
            executeCommand(testContext, (SqlGroup) clazz.getAnnotation(SqlGroup.class), AFTER_TEST_METHOD);
        }

        private void executeCommand(TestContext testContext, SqlGroup annotation, Sql.ExecutionPhase phase) {
            if (annotation == null) return;

            DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
//            JdbcTemplate custom = new JdbcTemplate(dataSource);
            if (annotation.value() != null && annotation.value().length > 0) {
                for (Sql sqlScript : annotation.value()) {
                    if (sqlScript.executionPhase() == phase) {
                        for (String sqlFilePath : sqlScript.scripts()) {
//                            custom.execute(readFile(sqlFilePath, Charset.defaultCharset()));
                            Resource resource = new ClassPathResource(sqlFilePath);
                            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
                            databasePopulator.execute(dataSource);
                        }
                    }
                }
            }
        }
    }
}
