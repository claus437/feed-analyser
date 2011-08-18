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



    @Test
    public void testSetSchedule() {
        JobPlan plan;

        plan = new JobPlan();

        Assert.assertEquals(null, subject.getPlan());

        subject.setPlan(plan);
        Assert.assertEquals(plan, subject.getPlan());
    }

    @Test
    public void testStart() throws Exception {
        subject.start();
        subject.kill();

        while (executed == 0) {
            Thread.sleep(100);
        }

        IOUtil.wait(subject);

        Assert.assertEquals(1, executed);
    }


    @Override
    public void execute() {
        executed++;
    }

    @Override
    public void terminate() {

    }

    @Override
    public String getName() {
        return null;
    }
}
