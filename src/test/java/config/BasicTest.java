package config;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import java.util.TimeZone;

/**
 * Created by byaxe on 04.12.16.
 */
@ContextConfiguration(locations = {"classpath:test-application-context.xml"})
public abstract class BasicTest extends AbstractTestNGSpringContextTests {

    @BeforeSuite(alwaysRun = true)
    protected void setupSpringAutowiring() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        super.springTestContextBeforeTestClass();
        super.springTestContextPrepareTestInstance();
    }

}
