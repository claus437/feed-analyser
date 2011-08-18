package org.wooddog.job;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 17-08-11
 * Time: 08:51
 * To change this template use File | Settings | File Templates.
 */
public class JobThread extends Thread {
    private Job job;
    private JobPlan plan;
    private boolean signal;
    private Date lastRun;
    private Date nextRun;

    public JobThread() {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobPlan getPlan() {
        return plan;
    }

    public void setPlan(JobPlan plan) {
        this.plan = plan;
    }

    public void run() {
        lastRun = new Date();
        nextRun = plan.getNextRun(lastRun);

        while (!signal) {
            if (System.currentTimeMillis() > nextRun.getTime()) {
                execute();
            } else {
                sleep();
            }
        }
    }

    public void execute() {
        job.execute();

        lastRun = new Date();
        nextRun = plan.getNextRun(lastRun);
    }

    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException x) {
            signal = true;
        }
    }

    public void kill() {
        signal = true;
    }
}
