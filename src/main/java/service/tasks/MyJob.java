package service.tasks;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by byaxe on 13.12.16.
 */
@DisallowConcurrentExecution
public class MyJob implements Job {

    private String someParam;
    private String someParam2;

    public void setSomeParam(String someParam) {
        this.someParam = someParam;
    }

    public void setSomeParam2(String someParam2) {
        this.someParam2 = someParam2;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("My job is running with " + someParam + ' ' + someParam2);
    }
}
