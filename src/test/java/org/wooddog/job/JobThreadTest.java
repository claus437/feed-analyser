package org.wooddog.job;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.IOUtil;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 17-08-11
 * Time: 08:54
 * To change this template use File | Settings | File Templates.
 */
public class JobThreadTest implements Job {
    private JobThread subject = new JobThread();
    private int executed;
    private int terminated;

    @Test
    public void testSetSchedule() {
        JobPlan plan;

        plan = new JobPlan();

        Assert.assertEquals(null, subject.getPlan());

        subject.setPlan(plan);
        Assert.assertEquals(plan, subject.getPlan());
    }

    @Test (timeout = 5000)
    public void testExecute() throws Exception {
        JobPlan plan;

        plan = new JobPlan();
        plan.setSecond(JobPlan.Frequency.EVERY, 1);

        subject.setJob(this);
        subject.setPlan(plan);
        subject.start();

        while (executed == 0) {
            Thread.sleep(10);
        }

        subject.kill();
        subject.join();

        Assert.assertEquals(1, executed);
    }

    @Test (timeout = 5000)
    public void testTerminate() throws Exception {
        JobPlan plan;

        plan = new JobPlan();
        plan.setHour(JobPlan.Frequency.EVERY, 1);

        subject.setJob(this);
        subject.setPlan(plan);
        subject.start();
        subject.kill();

        while (terminated == 0) {
            Thread.sleep(10);
        }

        subject.join();

        Assert.assertEquals(1, terminated);
        Assert.assertEquals(0, executed);
    }

    @Override
    public void execute() {
        executed++;
    }

    @Override
    public void terminate() {
        terminated++;
    }

    @Override
    public String getName() {
        return "MyJob";
    }
}
